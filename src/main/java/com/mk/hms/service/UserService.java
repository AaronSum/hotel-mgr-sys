package com.mk.hms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.mk.hms.enums.HmsHRoleTypeEnum;
import com.mk.hms.enums.HmsRuleCodeEnum;
import com.mk.hms.enums.HmsVisibleEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.EHotelMapper;
import com.mk.hms.mapper.GroupHotelMapper;
import com.mk.hms.mapper.GroupMapper;
import com.mk.hms.mapper.MRoleUserMapper;
import com.mk.hms.mapper.MUserMapper;
import com.mk.hms.mapper.OperateLogMapper;
import com.mk.hms.mapper.QrcodeMapper;
import com.mk.hms.mapper.RoleMapper;
import com.mk.hms.mapper.THotelMapper;
import com.mk.hms.mapper.UserMapper;
import com.mk.hms.mapper.UserRoleMapper;
import com.mk.hms.model.EHotel;
import com.mk.hms.model.EHotelCriteria;
import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.Group;
import com.mk.hms.model.GroupCriteria;
import com.mk.hms.model.GroupHotel;
import com.mk.hms.model.GroupHotelCriteria;
import com.mk.hms.model.MRoleUser;
import com.mk.hms.model.MRoleUserCriteria;
import com.mk.hms.model.MUser;
import com.mk.hms.model.MUserCriteria;
import com.mk.hms.model.OperateLog;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.Qrcode;
import com.mk.hms.model.QrcodeCriteria;
import com.mk.hms.model.QrcodeCriteria.Criteria;
import com.mk.hms.model.Role;
import com.mk.hms.model.RoleCriteria;
import com.mk.hms.model.THotelWithBLOBs;
import com.mk.hms.model.User;
import com.mk.hms.model.UserCriteria;
import com.mk.hms.model.UserRole;
import com.mk.hms.model.UserRoleCriteria;
import com.mk.hms.model.VerifyPhoneModel;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.DESUtils;
import com.mk.hms.utils.HmsDateUtils;
import com.mk.hms.utils.HmsFileUtils;
import com.mk.hms.utils.HmsRedisCacheUtils;
import com.mk.hms.utils.HmsStringUtils;
import com.mk.hms.utils.LogUtils;
import com.mk.hms.utils.RequestUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.EHotelWithStringDate;
import com.mk.hms.view.HmsPmsToken;
import com.mk.hms.view.Login;
import com.mk.hms.view.LoginUser;
import com.mk.hms.view.QrcodeUser;

/**
 * 用户 service
 * @author hdy
 *
 */
@Service
@Transactional
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserMapper userMapper = null;
	
	@Autowired
	private GroupMapper groupMapper = null;
	
	@Autowired
	private GroupHotelMapper groupHotelMapper = null;
	
	@Autowired
	private EHotelMapper eHotelMapper = null;
	
	@Autowired
	private UserRoleMapper userRoleMapper = null;
	
	@Autowired
	private RoleMapper roleMapper = null;
	
	@Autowired
	private MUserMapper mUserMapper = null;
	
	@Autowired
	private MRoleUserMapper mRoleUserMapper = null;
	
	@Autowired
	private THotelMapper tHotelMapper = null;
	
	@Autowired
	private QrcodeMapper qrcodeMapper = null;
	
	@Autowired
	private OperateLogMapper operateLogMapper = null;
	
	@Autowired
	private I2DimCodesService i2DimCodesService = null;

	@Autowired
	private SmsService smsService = null;
	
	/**
	 * 用户登录
	 * @param login 登录对象
	 * @throws IOException 
	 * @throws SessionTimeOutException 
	 */
	public ModelAndView login(long hotelId) throws IOException {
		LoginUser loginUser = new LoginUser();
		try {
			loginUser = SessionUtils.getSessionLoginUser();
		} catch (SessionTimeOutException e) {
			UserService.logger.debug("session无数据，用户需重新登录");
		}
		ModelAndView view = new ModelAndView();
		view.setViewName("login/login");
		if (null != loginUser) {
			LogUtils.logStep(logger, "获取登录用户成功");
			User user = loginUser.getUser();
			if (null != user) {
				setView(loginUser, view);
				EHotelWithBLOBs thisHotel = null;
				List<EHotelWithBLOBs> hotels = loginUser.getHotels();
				// 不存在酒店列表
				if (null == hotels || hotels.size() <= 0) {
					SessionUtils.setRegUser(loginUser.getUser());
					view.addObject("status", 2);
					view.addObject("context", "您没有注册酒店，请您先注册酒店信息！");
					view.setViewName("login/msg");
					LogUtils.logStep(logger, "没有注册酒店，跳转到注册酒店信息页！");
					return view;
				}
				if (hotelId == 0) {
					EHotelWithBLOBs tokenHotel = SessionUtils.getTokenHotel();
					thisHotel = tokenHotel == null ? loginUser.getHotels().get(0) : tokenHotel;
				} else {
					for (EHotelWithBLOBs hotel : loginUser.getHotels()) {
						if (hotel.getId() == hotelId) {
							thisHotel = hotel;
							break;
						}
					}
				}
				//防止地址栏中输入hotel编号，默认选中第一个
				if (null == thisHotel) {
					thisHotel = loginUser.getHotels().get(0);
				}
				view.addObject("thisHotel", JSONObject.fromObject(
						copyEhotel2EhotelWithStringDate(this.geteHotelMapper().selectByPrimaryKey(thisHotel.getId()))).toString());
				//获取t表的rulecode
				int ruleCode = findRuleCode(thisHotel.getId());
				view.addObject("ruleCode", ruleCode);
				loginUser.setThisHotel(thisHotel);
			}
		}
		SessionUtils.setSessionLoginUser(loginUser);
		return view;
	}

	/**
	 * 是否存在该用户
	 * @param login 登录对象
	 * @return 输出对象
	 */
	public OutModel isExitLoginUser(Login login) {
		// LogUtils.logReq(logger, "进入请求-查看是否存在该用户", params);
		OutModel out = new OutModel();
		if (StringUtils.isNotBlank(login.getLoginname()) && StringUtils.isNotBlank(login.getPassword())) {
			User user = this.findUserByLoginName(login.getLoginname());
			if (null == user) {
				out.setSuccess(false);
				out.setErrorMsg("该用户无效");
			} else {
				// pms登录校验token
				boolean isToken = false;
				String token = login.getToken();
				if (StringUtils.isNoneBlank(token)) {
					HmsPmsToken hpt = DESUtils.decryptTokenByDES(token);
					if (hpt.getLoginname().equals(login.getLoginname())) {
						if (new Date().getTime() - hpt.getTimes() < DESUtils.timeoutTimestamp) {
							isToken = true;
						}
					}
				}
				String myPwd = HmsStringUtils.encrypByMd5(login.getPassword(), 32);
				if (user.getPsw().equals(myPwd) || isToken) {
					if (isToken) {
						SessionUtils.setToken4PmsUser(token);
					}
					out.setSuccess(true);
					//设置用户 session
					this.setSessionLoginUser(user);
					// 去除pms登录用户信息
					SessionUtils.setSessionPmsUser(null);
					SessionUtils.setSessionPmsHotel(null);
					LogUtils.logStep(logger, "设置用户 session 成功");
				} else {
					out.setSuccess(false);
					out.setErrorMsg("用户名或密码无效");
				}
			}
		} else {
			out.setSuccess(false);
			out.setErrorMsg("用户名或密码不能为空");
		}
		LogUtils.logStep(logger, "结束请求");
		return out;
	}
	
	/**
	 * 注册用户
	 * @param user 用户对象
	 * @param group 分组对象
	 * @return 插入结果
	 */
	public User regUser(String phoneNum, String aPsd) {
		// 添加用户
		User user = new User();
		user.setLoginname(phoneNum);
		user.setPsw(HmsStringUtils.encrypByMd5(aPsd, 32));
		user.setName(phoneNum);
		user.setBegintime(new Date());
		user.setVisible(HmsVisibleEnum.T.getValue());
		Group group = new Group();
		group.setName(phoneNum);
		group.setRegphone(phoneNum);
		this.getGroupMapper().insert(group);
		if (group.getId() > 0) {
			user.setGroupid(group.getId());
			this.getUserMapper().insert(user);
		}
		return user;
	}
	
	/**
	 * 根据登录名获取用户对象
	 * @param loginname 登录名
	 * @return 登录名对象
	 */
	public User findUserByLoginName(String loginname) {
		UserCriteria userCriteria = new UserCriteria();
		userCriteria.createCriteria().andLoginnameEqualTo(loginname).andVisibleEqualTo(HmsVisibleEnum.T.getValue());
		List<User> list = this.getUserMapper().selectByExample(userCriteria);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 检查此用户是否已经注册过
	 * @param loginname 登录名
	 * @return 用户对象
	 */
	public User isRegistered(String loginname) {
		UserCriteria userCriteria = new UserCriteria();
		userCriteria.createCriteria().andLoginnameEqualTo(loginname).andVisibleEqualTo(HmsVisibleEnum.F.getValue());
		List<User> list = this.getUserMapper().selectByExample(userCriteria);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 重置酒店前台用户信息
	 * @param register 前台用户信息
	 * @param aPsd 密码
	 * @throws SessionTimeOutException 登录超时异常
	 */
	public void resetHotelUser(User register, String aPsd) throws SessionTimeOutException {
		// 修改用户密码， 状态置为可用，修改分组信息
		register.setPsw(HmsStringUtils.encrypByMd5(aPsd, 32));
		register.setVisible(HmsVisibleEnum.T.getValue());
		register.setGroupid(SessionUtils.getSessionLoginUser().getGroup().getId());
		this.getUserMapper().updateByPrimaryKey(register);
		
		// 解除用户角色关联
		UserRoleCriteria userRole = new UserRoleCriteria();
		userRole.createCriteria().andUseridEqualTo(register.getId());
		this.getUserRoleMapper().deleteByExample(userRole);
	}
	
	/**
	 * 重置用户信息
	 * @param register 用户信息
	 */
	public void resetUser(User register, String aPsd) {
		String loginName = register.getLoginname();
		// 删除酒店分组
		GroupCriteria groupCriteria = new GroupCriteria();
		groupCriteria.createCriteria().andRegphoneEqualTo(loginName);
		List<Group> groupList = this.getGroupMapper().selectByExample(groupCriteria);
		Group g = groupList.size() > 0 ? groupList.get(0) : null;
		// 删除分组酒店
		if (null != g) {
			GroupHotelCriteria groupHotelCriteria = new GroupHotelCriteria();
			groupHotelCriteria.createCriteria().andGroupidEqualTo(g.getId());
			this.getGroupHotelMapper().deleteByExample(groupHotelCriteria);
		}
		// 删除分组
		this.getGroupMapper().deleteByPrimaryKey(g.getId());
		
		// 修改用户密码， 状态置为可用
		register.setPsw(HmsStringUtils.encrypByMd5(aPsd, 32));
		register.setVisible(HmsVisibleEnum.T.getValue());

		// 添加分组
		Group group = new Group();
		group.setName(loginName);
		group.setRegphone(loginName);
		this.getGroupMapper().insert(group);
		if (group.getId() > 0) {
			register.setGroupid(group.getId());
		}
		this.getUserMapper().updateByPrimaryKey(register);
		
		// 解除用户角色关联
		UserRoleCriteria userRole = new UserRoleCriteria();
		userRole.createCriteria().andUseridEqualTo(register.getId());
		this.getUserRoleMapper().deleteByExample(userRole);
	}
	
	/**
	 * 根据登录名，获取销售用户对象
	 * @param loginname
	 * @return
	 */
	public MUser getMUserByLoginname(String loginname) {
		MUserCriteria mUserCriteria = new MUserCriteria();
		mUserCriteria.createCriteria().andLoginnameEqualTo(loginname);
		List<MUser> musers = this.getmUserMapper().selectByExample(mUserCriteria);
		return musers.size() > 0 ? musers.get(0) : null;
	}
	
	/**
	 * 获取销售人员用户角色对象
	 * @param userId 用户id
	 * @return 用户角色对象
	 */
	public MRoleUser getMUserRole(long userId) {
		MRoleUserCriteria MRoleUserCriteria = new MRoleUserCriteria();
		MRoleUserCriteria.createCriteria().andUseridEqualTo(userId);
		List<MRoleUser> mRoleUsers = this.getmRoleUserMapper().selectByExample(MRoleUserCriteria);
		return mRoleUsers.size() > 0 ? mRoleUsers.get(0) : null;
	}
	
	/**
	 * 判断是不是外来人员权限
	 * @param userId 用户id
	 * @return Boolean true-是  false-不是
	 * @throws IOException
	 */
	public boolean getForeignPersonRoleCode(long userId) throws IOException {
		String foreignPersonRoleCode = HmsFileUtils.getSysContentItem(ContentUtils.FOREIGN_PERSON_ROLE_CODE);
		MRoleUser mRoleUser = this.getMUserRole(userId);
		
		if (!NumberUtils.isNumber(foreignPersonRoleCode)) {
			return false;
		}
		
		if(mRoleUser == null){
			return false;
		}
		
		if(mRoleUser.getRoleid() == Integer.parseInt(foreignPersonRoleCode)){
			return true;
		}
		return false;
		
	}
	
	/**
	 * 获取酒店用户二维码信息列表
	 * @return 酒店二维码信息列表
	 * @throws SessionTimeOutException 
	 */
	public List<QrcodeUser> finsHotelQrcodeUserList() throws SessionTimeOutException {
		// 二维码用户存放列表
		List<QrcodeUser> qrcodeUserList = new ArrayList<QrcodeUser>();
		// 如果不是店长，即前台
		if (!SessionUtils.isHotelManager()) {
			this.findHotelUserByWaiter(qrcodeUserList);
			return qrcodeUserList;
		}
		// 合并用户二维码列表
		this.extendHQrcodeUser(qrcodeUserList);
		return qrcodeUserList;
	}
	
	/**
	 * 修改用户密码
	 * @return 修改状态
	 * @throws SessionTimeOutException 
	 */
	public OutModel modifyUserPwd(String oldPwd, String newPwd, String modifyUserLoginName) throws SessionTimeOutException {
		// 初始化返回信息
		OutModel out = new OutModel(false);
		// 1. 店长可以修改自己喝所有员工密码
		User user = SessionUtils.getSessionLoginUser().getUser();
		String funcName = "";
		if (SessionUtils.isHotelManager()) {
			// 1.1 修改自己密码的密码
			if (modifyUserLoginName.equals(user.getLoginname())) {
				this.modifyYourselfPwd(oldPwd, newPwd, out, user);
				funcName = "修改自己密码(" + user.getLoginname() + ")";
			// 修改员工密码
			} else {
				this.modifyWaiterPwd(modifyUserLoginName, out, newPwd);
				funcName = "店长修改员工密码(" + modifyUserLoginName + ")";
			}
		// 员工只能修改自己密码
		} else {
			this.modifyYourselfPwd(oldPwd, newPwd, out, user);
			funcName = "修改自己密码(" + user.getLoginname() + ")";
		}
		this.addOperateLog("h_user", "modifyYourselfPwd", funcName);
		return out;
	}
	
	/**
	 * 发送手机验证码
	 * @return 发送状态
	 * @throws IOException 
	 */
	public OutModel sendVerifyCodeOfPhoneNo(String phoneNo) throws IOException {
		OutModel out = new OutModel(false);
		// 查看验证码是否过期
		if (HmsRedisCacheUtils.verifyCodeIsExpired(phoneNo)) {
			out.setErrorMsg("验证码未过期，请继续使用");
			return out;
		}
		VerifyPhoneModel newVerifyPhone = new VerifyPhoneModel();
		newVerifyPhone.setPhnoeNo(phoneNo);
		newVerifyPhone.setVerifyCode(HmsStringUtils.getRandomNum(6));
		// 调用发送验证码接口
		String message = HmsFileUtils.getSysContentItem(ContentUtils.SMS_MSG_PREFIX) + newVerifyPhone.getVerifyCode();
		// 发送短信
		out = this.getSmsService().sendSmsMsg(newVerifyPhone, message);
		if (!out.isSuccess()) {
			return out;
		}
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * 添加酒店用户信息
	 * 逻辑：1. 该用户是否已注册 
	 * 		2. 判断是否是店长，只有店长才可以添加用户
	 * 		3. 验证验证码
	 * 		4. 添加用户
	 * 		5. 添加角色
	 * 		6. 绑定二维码
	 * @return 添加返回状态
	 * @throws IOException 
	 * @throws SessionTimeOutException 
	 */
	public OutModel addHotelUser(String phoneNo, int verifyCode, String password, String name, String qrcodeTag) throws IOException, SessionTimeOutException {
		User user = this.findHmsUserByLoginName(phoneNo);
		// 返回结果对象
		OutModel out = new OutModel(false);
		// 1. 该用户是否已注册
		// 已注册
		if (null != user) {
			out.setErrorMsg("该用户已注册");
			return out;
		}
		// 2. 当前用户是否是店长
		// 不是店长
		if (!SessionUtils.isHotelManager()) {
			out.setErrorMsg("您没有添加用户权限");
			return out;
		}
		// 3. 验证手机
		this.verifyPhoneCode(phoneNo, verifyCode, out);
		if (!out.isSuccess()) {
			return out;
		}
		// 该用户是否已注册过，但未启用
		User addUser = this.isRegistered(phoneNo);
		if (null != addUser) {
			this.resetHotelUser(addUser, password);
		// 4. 添加用户
		} else {
			addUser = this.addHotelUser(phoneNo, password, name, out);
			if (!out.isSuccess()) {
				return out;
			}
		}
		this.addOperateLog("h_user", "addHotelUser", "添加前台(" + addUser.getLoginname() + ")");	
		// 5. 添加角色
		this.addHotelUserRole(addUser, out);
		if (!out.isSuccess()) {
			return out;
		}
		// 6. 绑定二维码
		this.bindUserQrcode(qrcodeTag, addUser, out);
		if (!out.isSuccess()) {
			return out;
		}
		this.addOperateLog("h_user", "addHotelUser", "添加用户并绑定了二维码(" + addUser.getLoginname() + "," + qrcodeTag + ")");	
		return out;
	}
	
	/**
	 * 获取可用二维码列表
	 * @return 可用二维码列表
	 * @throws SessionTimeOutException 
	 */
	public List<Qrcode> getAvailableQrcodeByUserLoginName(String userLoginName) throws SessionTimeOutException {
		User user = this.findHmsUserByLoginName(userLoginName);
		EHotel thisHotel = SessionUtils.getSessionLoginUser().getThisHotel();
		long hotelId = thisHotel.getId();
		QrcodeCriteria example = new QrcodeCriteria();
		example.setOrderByClause("tag asc");
		Criteria criteriaUser = example.createCriteria();
		criteriaUser.andHotelidEqualTo(hotelId);
		criteriaUser.andUseridEqualTo(user.getId());
		
		Criteria criteriaUserIsNull = example.createCriteria();
		criteriaUserIsNull.andHotelidEqualTo(hotelId);
		criteriaUserIsNull.andUseridIsNull();
		
		example.or(criteriaUserIsNull);
		return this.getQrcodeMapper().selectByExample(example);
	}
	
	/**
	 * 获取可用二维码列表
	 * @return 可用二维码列表
	 * @throws SessionTimeOutException 
	 */
	public List<Qrcode> findAvailableQrcodesByHotelId() throws SessionTimeOutException {
		EHotel thisHotel = SessionUtils.getSessionLoginUser().getThisHotel();
		long hotelId = thisHotel.getId();
		QrcodeCriteria example = new QrcodeCriteria();
		example.setOrderByClause("tag asc");
		Criteria criteriaUserIsNull = example.createCriteria();
		criteriaUserIsNull.andHotelidEqualTo(hotelId);
		criteriaUserIsNull.andUseridIsNull();
		return this.getQrcodeMapper().selectByExample(example);
	}
	
	/**
	 * 修改用户信息
	 * @return 修改状态
	 * @throws SessionTimeOutException 
	 */
	public OutModel modifyUser(String name, boolean isUnbindFlag, String loginName, String qrcode) throws SessionTimeOutException {
		User user = this.findHmsUserByLoginName(loginName);
		OutModel out = new OutModel(false);
		if (null == user) {
			out.setErrorMsg("用户不存在");
			return out;
		}
		String userName = user.getName();
		if (HmsStringUtils.isBlank(userName)) {
			userName = "";
		}
		if (!userName.equals(name)) {
			user.setName(name);
			this.modifyUserName(name, loginName);
		}
		long hotelId = SessionUtils.getThisHotelId();
		int ruleCode = findRuleCode(hotelId);
		if(ruleCode == HmsRuleCodeEnum.A_RULE.getRuleCode()){
			if (isUnbindFlag) {
				EHotel hotle = SessionUtils.getThisHotel();
				this.unbindQrcode(hotle.getId(), qrcode, user.getId());
				this.addOperateLog("h_qrcode", "modifyUser", "解绑二维码(" + user.getLoginname() + "," + qrcode + ")");
			} else {
				this.bindUserQrcode(qrcode, user, out);
				this.addOperateLog("h_qrcode", "modifyUser", "绑定二维码(" + user.getLoginname() + "," + qrcode + ")");
				if (!out.isSuccess()) {
					return out;
				}
			}
		}
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * 根据用户名，获取二维码对象
	 * @return 二维码对象
	 * @throws SessionTimeOutException 
	 */
	public Qrcode findQrCodeByThisUserId() throws SessionTimeOutException {
		Qrcode qrcode = this.findQrcodeByThisUserId();
		if (null == qrcode) {
			qrcode = new Qrcode();
		}
		return qrcode;
	}
	
	/**
	 * 解绑酒店前台手机号
	 * @return 解绑状态
	 * @throws SessionTimeOutException 
	 */
	public OutModel unbindMobilePhone(String phoneNo, String pwd, String tag) throws SessionTimeOutException {
		OutModel out = new OutModel(false);
		// 参数是否为空
		if (HmsStringUtils.isBlank(phoneNo) || HmsStringUtils.isBlank(pwd)) {
			out.setErrorMsg("手机号或确认密码不可为空");
			return out;
		}
		// 获取当前用户，判断密码是否正确
		LoginUser loginUser = SessionUtils.getSessionLoginUser();
		if (null == loginUser) {
			out.setErrorMsg("用户登录过期，请重新登录……");
			return out;
		}
		// 老板权限才可以解绑手机
		if (!SessionUtils.isHotelManager()) {
			out.setErrorMsg("不没有权限解绑手机号，请联系酒店老板");
			return out;
		}
		// 校验解绑手机号为前台手机号
		if (phoneNo.equals(loginUser.getUser().getLoginname())) {
			out.setErrorMsg("只能解绑前台手机号码");
			return out;
		}
		String pwdByMd5 = HmsStringUtils.encrypByMd5(pwd, 32);
		// 校验密码
		if (!pwdByMd5.equals(loginUser.getUser().getPsw())) {
			out.setErrorMsg("用户密码校验错误，请重新输入……");
			return out;
		}
		// 获取手机号用户信息
		User user = this.findHmsUserByLoginName(phoneNo);
		if (null == user) {
			out.setErrorMsg("没有该手机号用户信息");
			return out;
		}
		long hotelid = loginUser.getThisHotel().getId();
		long userId = user.getId();
		// 获取二维码信息
		if (HmsStringUtils.isNoneBlank(tag)) {
			Qrcode qrCode = this.findQrcodeByTag(tag, hotelid);
			if (null != qrCode) {
				// 判断该二维码是否为此用户绑定
				if (qrCode.getUserid() > 0 && qrCode.getUserid() != userId) {
					out.setErrorMsg("该二维码不为此用户绑定");
					return out;
				}
				// 解绑二维码
				int unbindQrcodeCount = this.unbindQrcode(hotelid, tag, userId);
				if (unbindQrcodeCount != 1) {
					out.setErrorMsg("手机二维码解绑失败");
					return out;
				}
			}
		}
		// 获取酒店前台角色
		Role hotelWaiterRole = this.findHotelRoleByHotelIdAndType(hotelid, HmsHRoleTypeEnum.Waiter.getValue());
		if (null == hotelWaiterRole) {
			out.setErrorMsg("获取酒店前台角色信息失败");
			return out;
		}
		// 删除用户信息
		int delUserCount = this.updateHmsUserById(userId);
		if (delUserCount != 1) {
			out.setErrorMsg("删除用户信息失败");
			return out;
		}
		// 删除用户绑定角色
		if (!this.delUserInHotel(userId)) {
			out.setErrorMsg("解绑用户酒店信息失败");
			return out;
		}
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * 获取格式化之后开业时间、装修时间
	 * @param eHotel 原数据对象
	 * @param fmtHotel 格式化之后数据对象
	 */
	public EHotelWithStringDate copyEhotel2EhotelWithStringDate(EHotelWithBLOBs eHotel) {
		EHotelWithStringDate fmtHotel = new EHotelWithStringDate();
        BeanCopier beanCopier = BeanCopier.create(EHotelWithBLOBs.class, EHotelWithStringDate.class, false);
        beanCopier.copy(eHotel, fmtHotel, null);
        Date openTime = fmtHotel.getOpenTime();
        Date repairTime = fmtHotel.getRepairTime();
        if (null != openTime) {
        	fmtHotel.setFmtOpenTime(HmsDateUtils.getFormatDateString(fmtHotel.getOpenTime(), HmsDateUtils.FORMAT_DATE));
        }
        if (null != repairTime) {
        	fmtHotel.setFmtRepairTime(HmsDateUtils.getFormatDateString(fmtHotel.getRepairTime(), HmsDateUtils.FORMAT_DATE));
        }
        return fmtHotel;
	}
	
	/**
	 * 删除酒店用户
	 * @param userId 用户id
	 * @return 受影响条数
	 */
	private boolean delUserInHotel(long userId) {
		UserRoleCriteria example = new UserRoleCriteria();
		example.createCriteria().andUseridEqualTo(userId);
		return this.getUserRoleMapper().deleteByExample(example) == 1 ? true : false;
	}
	
	/**
	 * 删除用户
	 * @param userId 用户id
	 * @return 受影响条数
	 */
	private int updateHmsUserById(long userId) {
		UserCriteria example = new UserCriteria();
		example.createCriteria().andIdEqualTo(userId).andVisibleEqualTo(HmsVisibleEnum.T.getValue());
		User u = this.getUserInfo(example);
		if (null == u) {
			return 0;
		}
		u.setVisible(HmsVisibleEnum.F.getValue());
		u.setEndtime(new Date());
		return this.getUserMapper().updateByPrimaryKey(u);
	}
	
	/**
	 * 验证手机
	 * @param phoneNo 手机好吗
	 * @param verifyCode 验证码
	 * @param out 返回结果
	 * @throws IOException 
	 */
	private void verifyPhoneCode(String phoneNo, int verifyCode, OutModel out) throws IOException {
		if(!HmsStringUtils.isAllowPhoneNo(phoneNo)) {
			out.setErrorMsg("您输入的手机号不合法");
			out.setSuccess(false);
			return;
		}
		if (!HmsRedisCacheUtils.verifyCodeIsValid(phoneNo, verifyCode)) {
			out.setErrorMsg("您输入的手机号不是和验证码不匹配");
			out.setSuccess(false);
			return;
		}
		out.setSuccess(true);
	}
	
	/**
	 * 根据角色类型 获取角色对象
	 * @param hotelId 酒店ID
	 * @param roleType 角色类型
	 * @return 角色对象
	 */
	private Role findHotelRoleByHotelIdAndType(long hotelId, int roleType) {
		RoleCriteria example = new RoleCriteria();
		example.createCriteria().andHotelidEqualTo(hotelId).andTypeEqualTo(roleType);
		List<Role> list = this.getRoleMapper().selectByExample(example);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 通过用户id、酒店id获取用户二维码
	 * @return 二维码对象
	 * @throws SessionTimeOutException 
	 */
	private Qrcode findQrcodeByThisUserId() throws SessionTimeOutException {
		LoginUser loginUser = SessionUtils.getSessionLoginUser();
		if (null != loginUser) {
			User user = loginUser.getUser();
			EHotelWithBLOBs thisHotel = loginUser.getThisHotel();
			Qrcode qrCode = this.findQrcodeByUserId(user.getId(), thisHotel.getId());
			return qrCode;
		}
		return null;
	}
	
	/**
	 * 解绑二维码
	 * @param hotleId
	 * @param qrcode
	 * @param userId
	 * @return
	 */
	private int unbindQrcode(long hotleId, String qrcode, long userId) {
		QrcodeCriteria example = new QrcodeCriteria();
		example.createCriteria().andHotelidEqualTo(hotleId).andUseridEqualTo(userId).andTagEqualTo(qrcode);
		Qrcode q = this.getQrcodeInfo(example);
		if (null == q) {
			return 0;
		}
		q.setUserid(null);
		return this.getQrcodeMapper().updateByPrimaryKey(q);
	}
	
	/**
	 * 修改用户昵称
	 * @param name 昵称
	 * @param loginName 登录名
	 * @return 受影响条数
	 */
	private int modifyUserName(String name, String  loginName) {
		UserCriteria example = new UserCriteria();
		example.createCriteria().andLoginnameEqualTo(loginName);
		User u = this.getUserInfo(example);
		if (u == null) {
			return 0;
		}
		u.setName(name);
		return this.getUserMapper().updateByPrimaryKey(u);
	}
	
	/**
	 * 绑定用户二维码
	 * @param qrcodeTag 二维码标志
	 * @param addUser 用户信息
	 * @param out 返回结果
	 * @param hmsUserService 服务
	 * @throws SessionTimeOutException 
	 */
	private void bindUserQrcode(String qrcodeTag, User addUser, OutModel out) throws SessionTimeOutException {
		EHotel hotel = SessionUtils.getSessionLoginUser().getThisHotel();
		// tag不为空则可以添加二维码
		if (StringUtils.isNotBlank(qrcodeTag)) {
			// 判断二维码标示是否合法
			if (!HmsStringUtils.isAllowQrcodeTag(qrcodeTag)) {
				out.setErrorMsg("二维码标示不合法");
				out.setSuccess(false);
				return;
			}
			// 该二维码是否已绑定
			Qrcode qrcodeByTag = this.findQrcodeByTag(qrcodeTag, hotel.getId());
			if(null == qrcodeByTag.getUserid()){
				qrcodeByTag.setUserid(0L);
			}
			// 已绑定，并且不是该用户
			if (null != qrcodeByTag && null == qrcodeByTag.getUserid() && qrcodeByTag.getUserid() > 0 && qrcodeByTag.getUserid() != addUser.getId()) {
				out.setErrorMsg("该二维码已被其他用户绑定");
				out.setSuccess(false);
				return;
			}
			// 未绑定
			Qrcode qrcodeByUser = this.findQrcodeByUserId(addUser.getId(), hotel.getId());
			// 如果该用户已绑定二维码，则先解绑再绑定
			boolean isOk = false;
			if (null != qrcodeByUser) {
				isOk = this.updateQrcode(qrcodeTag, addUser.getId(), hotel.getId(), qrcodeByUser.getTag());
			} else {
				isOk = this.updateQrcode(qrcodeTag, addUser.getId(), hotel.getId(), null);
			}
			out.setSuccess(isOk);
		}
	}
	
	/**
	 * 修改二维码绑定
	 * @param tag 要绑定二维码标示
	 * @param userid 用户id
	 * @param hotelid 酒店id
	 * @param oldTag 已绑定二维码标示
	 * @return 绑定是否成功
	 */
	private boolean updateQrcode(String tag, long userid, long hotelid, String oldTag) {
		int count = 0;
		if (null != oldTag) {
			count = this.updateQrcodeUser(tag, userid, hotelid, oldTag);
			if (count > 0) {
				this.updateNewQrcode(hotelid, oldTag, tag);
			}
		} else {
			count = this.updateQrcode(userid, hotelid, tag);
		}
		return count == 1 ? true : false;
	}
	
	/**
	 * 绑定用户
	 * @param userid 用户id
	 * @param hotelid 酒店id
	 * @param tag tag
	 * @return 受影响条数
	 */
	private int updateQrcode(long userid, long hotelid, String tag) {
		QrcodeCriteria example = new QrcodeCriteria();
		example.createCriteria().andHotelidEqualTo(hotelid).andTagEqualTo(tag);
		Qrcode q = this.getQrcodeInfo(example);
		if (null == q) {
			return 0;
		}
		q.setUserid(userid);
		return this.getQrcodeMapper().updateByPrimaryKey(q);
	}
	
	/**
	 * 绑定新用户二维码
	 * @param hotelid 酒店ID
	 * @param oldTag 旧tag
	 * @param tag 新tag
	 * @return 受影响条数
	 */
	private int updateNewQrcode(long hotelid, String oldTag, String tag) {
		QrcodeCriteria example = new QrcodeCriteria();
		example.createCriteria().andHotelidEqualTo(hotelid).andTagEqualTo(oldTag).andUseridIsNull();
		Qrcode q = this.getQrcodeInfo(example);
		if (null == q) {
			return 0;
		}
		q.setTag(tag);
		return this.getQrcodeMapper().updateByPrimaryKey(q);
	}
	
	/**
	 * 替换二维码
	 * @param tag 标记
	 * @param userid 用户id
	 * @param hotelid 酒店id
	 * @param oldTag 老tag
	 * @return 受影响条数
	 */
	private int updateQrcodeUser(String tag, long userid, long hotelid, String oldTag) {
		QrcodeCriteria example = new QrcodeCriteria();
		example.createCriteria().andHotelidEqualTo(hotelid).andUseridEqualTo(userid).andTagEqualTo(oldTag);
		Qrcode q = this.getQrcodeInfo(example);
		if (null == q) {
			return 0;
		}
		q.setTag(tag);
		return this.getQrcodeMapper().updateByPrimaryKey(q);
	}
	
	/**
	 * 根据用户id 获取指定二维码对象
	 * @param userId 用户id
	 * @param hotelId 酒店id
	 * @return 指定二维码对象
	 */
	private Qrcode findQrcodeByUserId(long userId, long hotelId) {
		QrcodeCriteria example = new QrcodeCriteria();
		example.createCriteria().andHotelidEqualTo(hotelId).andUseridEqualTo(userId);
		List<Qrcode> list = this.getQrcodeMapper().selectByExample(example);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 获取制定tag二维码
	 * @param qrcodeTag tag
	 * @param hotelId 酒店id
	 * @return 指定二维码对象
	 */
	private Qrcode findQrcodeByTag(String qrcodeTag, long hotelId) {
		if (HmsStringUtils.isBlank(qrcodeTag)) {
			return null;
		}
		QrcodeCriteria example = new QrcodeCriteria();
		example.createCriteria().andHotelidEqualTo(hotelId).andTagEqualTo(qrcodeTag);
		List<Qrcode> list = this.getQrcodeMapper().selectByExample(example);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 添加用户角色信息
	 * @param addUser 添加用户
	 * @param out 返回结果
	 * @param hmsUserService 服务
	 * @throws SessionTimeOutException 
	 */
	private void addHotelUserRole(User addUser, OutModel out) throws SessionTimeOutException {
		EHotel hotel = SessionUtils.getSessionLoginUser().getThisHotel();
		// 获取当前酒店角色列表
		List<Role> hmsRoles = this.findHotelRoleIds(hotel.getId());
		// 找出前台角色ID
		long roleId = 0;
		for (Role role : hmsRoles) {
			if (null != role.getType() && role.getType() == HmsHRoleTypeEnum.Waiter.getValue()) {
				roleId = role.getId();
				break;
			}
		}
		if (roleId <= 0) {
			out.setSuccess(false);
			out.setErrorMsg("获取前台角色异常");
			return;
		}
		// 添加对应角色ID到用户角色中
		UserRole userRole = this.addHotelUserRole(addUser.getId(), roleId);
		if (null == userRole) {
			out.setSuccess(false);
			out.setErrorMsg("添加用户角色异常");
			return;
		}
	}
	
	/**
	 * 添加用户角色信息
	 * @param userid 用户id
	 * @param roleid 角色id
	 * @return 用户角色添加实体
	 */
	private UserRole addHotelUserRole(long userid, long roleid) {
		UserRole userRole = new UserRole();
		userRole.setUserid(userid);
		userRole.setRoleid(roleid);
		this.getUserRoleMapper().insert(userRole);
		return userRole;
	}
	
	/**
	 * 添加用户
	 * @param phoneNo 手机号
	 * @param password 密码
	 * @param name 昵称
	 * @param addUser 获取添加之后用户
	 * @param out 输出结果
	 * @param hmsUserService 服务
	 * @throws SessionTimeOutException 
	 */
	private User addHotelUser(String phoneNo, String password, String name, OutModel out) throws SessionTimeOutException {
		if (StringUtils.isBlank(password)) {
			out.setErrorMsg("输入密码不合法");
			out.setSuccess(false);
			return null;
		}
		// 添加用户表
		Group group = SessionUtils.getSessionLoginUser().getGroup();
		User addUser = this.addHotelUser(phoneNo, HmsStringUtils.encrypByMd5(password, 32),
				group.getId(), name);
		// 用户添加是否成功
		if (null == addUser) {
			out.setErrorMsg("用户添加异常");
			out.setSuccess(false);
			return null;
		}
		return addUser;
	}
	
	/**
	 * 添加酒店用户信息
	 * @param loginname 登录名
	 * @param psw 密码
	 * @param groupid 组名
	 * @param name 昵称
	 * @return 添加实体对象
	 */
	private User addHotelUser(String loginname, String psw, long groupid, String name) {
		User user = new User();
		user.setLoginname(loginname);
		user.setPsw(psw);
		user.setGroupid(groupid);
		user.setName(name);
		user.setBegintime(new Date());
		user.setVisible(HmsVisibleEnum.T.getValue());
		this.getUserMapper().insert(user);
		return user;
	}
	
	
	/**
	 * 添加og日志
	 * @param log 日志对象
	 * @return 日志对象
	 * @throws SessionTimeOutException 
	 */
	private OperateLog addOperateLog(String tableName, String funcCode, String funcName) throws SessionTimeOutException {
		// 获取当前登录用户
		LoginUser loginUser;
		OperateLog log = new OperateLog();
		try {
			loginUser = SessionUtils.getSessionLoginUser();
			User user = loginUser.getUser();
			if (null == user) {
				return null;
			}
			log.setUsercode(user.getLoginname());
			log.setUsername(user.getName());
			log.setUsertype(ContentUtils.HMS);
		} catch (SessionTimeOutException e) {
			MUser pmsUser = SessionUtils.getSessionPmsUser();
			// pms用户也不存在（没有登录）
			if (null == pmsUser) {
				return null;
			}
			log.setUsercode(pmsUser.getLoginname());
			log.setUsername(pmsUser.getName());
			log.setUsertype(ContentUtils.PMS);
		}
		log.setTatablename(tableName);
		log.setFunctioncode(funcCode);
		log.setFunctionname(funcName);
		log.setHotelid(SessionUtils.getThisHotelId());
		log.setOperatetime(new Date());
		log.setIp(RequestUtils.getIp());
		this.getOperateLogMapper().insert(log);
		return log;
	}
	
	/**
	 * 店长修改员工密码
	 * @param modifyUserLoginName 被修改店员登录名
	 * @param out 返回结果
	 * @param newPwd 新密码
	 * @param hmsUserService 服务 
	 */
	private void modifyWaiterPwd(String modifyUserLoginName, OutModel out, String newPwd) {
		User modifyUser = this.findHmsUserByLoginName(modifyUserLoginName);
		if (null == modifyUser) {
			out.setErrorMsg("该用户不存在");
			return;
		}
		if (StringUtils.isBlank(newPwd)) {
			out.setErrorMsg("新密码不能为空");
			return;
		}
		String newPwdMd5 = HmsStringUtils.encrypByMd5(newPwd, 32);
		int count = this.modifyUserPwd(newPwdMd5, modifyUser.getId());
		if (count != 1) {
			out.setErrorMsg("密码修改异常");
			return;
		}
		out.setSuccess(true);
	}
	
	/**
	 * 根据登录名 获取用户对象
	 * @param modifyUserLoginName 登录名
	 * @return 用户对象
	 */
	private User findHmsUserByLoginName(String modifyUserLoginName) {
		UserCriteria example = new UserCriteria();
		example.createCriteria().andLoginnameEqualTo(modifyUserLoginName).andVisibleEqualTo(HmsVisibleEnum.T.getValue());
		List<User> list = this.getUserMapper().selectByExample(example);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 修改自己密码
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @param out 返回结果
	 * @param user 当前用户对象
	 * @param hmsUserService 服务
	 */
	private void modifyYourselfPwd(String oldPwd, String newPwd, OutModel out, User user) {
		if (!(StringUtils.isNotBlank(oldPwd) && StringUtils.isNotBlank(newPwd))) {
			out.setErrorMsg("请填写新旧密码");
			return;
		} 
		String oldPwdMd5 = HmsStringUtils.encrypByMd5(oldPwd, 32);
		String newPwdMd5 = HmsStringUtils.encrypByMd5(newPwd, 32);
		if (!oldPwdMd5.equals(user.getPsw())) {
			out.setErrorMsg("旧密码填写错误");
			return;
		}
		int count = this.modifyUserPwd(newPwdMd5, user.getId());
		if (count != 1) {
			out.setErrorMsg("密码修改异常");
			return;
		}
		out.setSuccess(true);
	}
	
	/**
	 * 修改密码
	 * @param newPwdMd5 新密码
	 * @param userId 用户名
	 * @param loginname 登录名
	 * @return 受影响条数
	 */
	private int modifyUserPwd(String newPwdMd5, long userId) {
		User user = new User();
		user.setId(userId);
		user.setPsw(newPwdMd5);
		return this.getUserMapper().updateByPrimaryKeySelective(user);
	}
	
	/**
	 * 合并用户二维码列表
	 * @throws SessionTimeOutException 
	 */
	private void extendHQrcodeUser(List<QrcodeUser> qrcodeUserList) throws SessionTimeOutException {
		EHotel hotel = SessionUtils.getSessionLoginUser().getThisHotel();
		// 获取用户列表
		List<User> userList = this.findUserByIds(this.findUserRoleListByRoleIds(this.findHotelRoleIds(hotel.getId())));
		// 二维码列表
		List<Qrcode> hmsHQrcodes = this.getI2DimCodesService().findI2DimCodeList();
		String content = "";
		//B规则逻辑
		//查询thotel的rulecode
		int ruleCode = findRuleCode(hotel.getId());
		if(ruleCode == HmsRuleCodeEnum.B_RULE.getRuleCode()){
			//读取配置
			try {
				content = HmsFileUtils.getSysContentItem(ContentUtils.RULE_B_URL);
			} catch (IOException e) {
				logger.error("获取B规则二维码信息报错",e);
			}
		}
		// 合并二维码和用户
		for (User user : userList) {
			QrcodeUser qrcodeUser = new QrcodeUser();
			qrcodeUser.setLoginname(user.getLoginname());
			qrcodeUser.setName(user.getName());
			qrcodeUser.setUserid(user.getId());
			if(ruleCode == HmsRuleCodeEnum.B_RULE.getRuleCode()){
				qrcodeUser.setContent(content);
				qrcodeUser.setTag("");
			}else{
				for (Qrcode qrcode : hmsHQrcodes) {
					if (user.getId().equals(qrcode.getUserid())) {
						qrcodeUser.setContent(qrcode.getContent());
						qrcodeUser.setTag(qrcode.getTag());
					}
				}
			}
			qrcodeUserList.add(qrcodeUser);
		}
	}
	
	/**
	 * 获取角色列表
	 * @param hotelId 酒店id
	 * @return 角色列表
	 */
	private List<Role> findHotelRoleIds(long hotelId) {
		RoleCriteria example = new RoleCriteria();
		example.createCriteria().andHotelidEqualTo(hotelId);
		return this.getRoleMapper().selectByExample(example);
	}
	
	/**
	 * 用户角色列表
	 * @param list 角色列表
	 * @return 用户角色列表
	 */
	private List<UserRole> findUserRoleListByRoleIds(List<Role> list) {
		if (list.size() > 0) {
			List<Long> roleIds = new ArrayList<Long>();
			for (Role r : list) {
				roleIds.add(r.getId());
			}
			UserRoleCriteria example = new UserRoleCriteria();
			example.createCriteria().andRoleidIn(roleIds);
			return this.getUserRoleMapper().selectByExample(example);
		}
		return new ArrayList<UserRole>();
	}
	
	/**
	 * 获取用户列表
	 * @param list 用户角色列表
	 * @return 用户列表
	 */
	private List<User> findUserByIds(List<UserRole> list) {
		if (list.size() > 0) {
			List<Long> userIds = new ArrayList<Long>();
			for (UserRole ur : list) {
				userIds.add(ur.getUserid());
			}
			UserCriteria example = new UserCriteria();
			example.createCriteria().andIdIn(userIds).andVisibleEqualTo(HmsVisibleEnum.T.getValue());
			return this.getUserMapper().selectByExample(example);
		}
		return new ArrayList<User>();
	}
	
	/**
	 * 前台获取酒店用户列表
	 * @param qrcodeUserList 用户列表
	 * @param i2DimCodesService 服务
	 * @throws SessionTimeOutException 
	 */
	private void findHotelUserByWaiter(List<QrcodeUser> qrcodeUserList) throws SessionTimeOutException {
		User user = SessionUtils.getSessionLoginUser().getUser();
		QrcodeUser qrcodeUser = new QrcodeUser();
		Qrcode qrcode = this.findI2DimCode();
		qrcodeUser.setLoginname(user.getLoginname());
		qrcodeUser.setName(user.getName());
		qrcodeUser.setUserid(user.getId());
		
		String content = "";
		String tag = "";
		//B规则逻辑
		//查询thotel的rulecode
		long hotelId = SessionUtils.getThisHotelId();
		int ruleCode = findRuleCode(hotelId);
		if(ruleCode == HmsRuleCodeEnum.B_RULE.getRuleCode()){
			//读取配置
			try {
				content = HmsFileUtils.getSysContentItem(ContentUtils.RULE_B_URL);
			} catch (IOException e) {
				logger.error("获取B规则二维码信息报错",e);
			}
		}else if (null != qrcode){
			content = qrcode.getContent();
			tag = qrcode.getTag();
		}
		qrcodeUser.setContent(content);
		qrcodeUser.setTag(tag);	
		qrcodeUserList.add(qrcodeUser);
	}
	
	/**
	 * 获取二维码打印数据列表
	 * @return 数据列表
	 * @throws SessionTimeOutException 
	 */
	private Qrcode findI2DimCode() throws SessionTimeOutException {
		EHotelWithBLOBs thisHotel = SessionUtils.getSessionLoginUser().getThisHotel();
		User user = SessionUtils.getSessionLoginUser().getUser();
		QrcodeCriteria example = new QrcodeCriteria();
		example.createCriteria().andHotelidEqualTo(thisHotel.getId()).andUseridEqualTo(user.getId());
		List<Qrcode> list = this.getQrcodeMapper().selectByExample(example);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 设置session登录用户信息
	 * @param loginname 登录名
	 */
	private void setSessionLoginUser(User user) {
		LoginUser loginUser = new LoginUser();
		//获取用户信息
		loginUser.setUser(user);
		//获取分组信息
		Group group = this.getGroupMapper().selectByPrimaryKey(user.getGroupid());
		loginUser.setGroup(group);
		//判断；如果是老板则直接获取酒店列表
		if (user.getLoginname().equals(group.getRegphone())) {
			//获取用户分组中间表列表
			List<GroupHotel> groupHotelList = this.getHotelByGroupId(group.getId());
			List<Long> hotelIds = new ArrayList<Long>();
			for (GroupHotel gh : groupHotelList) {
				hotelIds.add(gh.getHotelid());
			}
			//获取老板对应e酒店列表
			loginUser.setHotels(this.getHotels(hotelIds));
			SessionUtils.setRegUser(user);
		//前台通过前台角色获取对应酒店
		} else { 
			List<Role> roleList = this.getMyRoles(user.getId());
			
			// 获取酒店列表
			List<Long> hotelIds =  new ArrayList<Long>();
			for (Role r : roleList) {
				hotelIds.add(r.getHotelid());
			}
			//获取前台管理酒店列表
			loginUser.setHotels(this.getHotels(hotelIds));
		}
		//添加用户所属角色
		loginUser.setRoles(this.getMyRoles(user.getId()));
		//设置session 登录用户信息
		SessionUtils.setSessionLoginUser(loginUser);
	}

	/**
	 * 获取我的角色列表
	 * @param userId 用户id
	 * @return 我的角色列表
	 */
	private List<Role> getMyRoles(long userId) {
		//获取用户角色中间表数据
		List<UserRole> userRoleList = this.getUserRoles(userId);
		List<Long> userRoleIds = new ArrayList<Long>(); 
		for (UserRole ur : userRoleList) {
			userRoleIds.add(ur.getRoleid());
		}
		if (userRoleIds.size() > 0) {
			List<Role> roleList = this.getRoles(userRoleIds);
			return roleList;
		}
		return null;
	}
	
	/**
	 * 获取分组酒店
	 * @param groupId 分组id
	 * @return 酒店id集合
	 */
	private List<GroupHotel> getHotelByGroupId(long groupId) {
		GroupHotelCriteria groupHotelCriteria = new GroupHotelCriteria();
		groupHotelCriteria.createCriteria().andGroupidEqualTo(groupId);
		return this.getGroupHotelMapper().selectByExample(groupHotelCriteria);
	}
	
	/**
	 * 获取酒店列表
	 * @param hotelIds 酒店ids
	 * @return 酒店列表
	 */
	private List<EHotelWithBLOBs> getHotels(List<Long> hotelIds) {
		if (hotelIds.size() > 0) {
			EHotelCriteria eHotelCriteria = new EHotelCriteria();
			eHotelCriteria.createCriteria().andIdIn(hotelIds);
			return this.geteHotelMapper().selectByExampleWithBLOBs(eHotelCriteria);
		}
		return null;
	}
	
	/**
	 * 获取用户角色信息
	 * @param userId 用户id
	 * @return 用户角色列表
	 */
	private List<UserRole> getUserRoles(long userId) {
		UserRoleCriteria userRoleCriteria = new UserRoleCriteria();
		userRoleCriteria.createCriteria().andUseridEqualTo(userId);
		return this.getUserRoleMapper().selectByExample(userRoleCriteria);
	}
	
	/**
	 * 获取角色列表
	 * @param roleIds 角色id
	 * @return 角色列表
	 */
	private List<Role> getRoles(List<Long> roleIds) {
		if (roleIds.size() > 0 ) {
			RoleCriteria roleCriteria = new RoleCriteria();
			roleCriteria.createCriteria().andIdIn(roleIds);
			return this.getRoleMapper().selectByExample(roleCriteria);
		}
		return null;
	}
	
	/**
	 * 重置view对象
	 * @param loginUser 登录用户
	 * @param view 试图
	 * @throws IOException 例外
	 */
	private void setView(LoginUser loginUser, ModelAndView view) throws IOException {
		view.setViewName("home/home");
		view.addObject("user", loginUser.getUser());
		view.addObject("thisUser", JSONObject.fromObject(loginUser.getUser()).toString());
		view.addObject("hotels", JSONArray.fromObject(loginUser.getHotels()).toString());
		view.addObject("group", JSONObject.fromObject(loginUser.getGroup()).toString());
		view.addObject("hostAddress", HmsFileUtils.getSysContentItem(ContentUtils.HOST_ADDRESS));
		String qiniuDownloadAddress = HmsFileUtils.getSysContentItem(ContentUtils.QINIU_DOWNLOAD_ADDRESS);
		view.addObject("qiniuDownloadAddress", qiniuDownloadAddress);
		LogUtils.logStep(logger, "七牛下载地址-" + qiniuDownloadAddress);
	}
	

	/**
	 * 取T表对应酒店id的rulecode值
	 * @param hotels 酒店id
	 */
	public int findRuleCode(long hotelId){
		logger.info("查询规则码");
		//查询T表
		THotelWithBLOBs thotel = this.gettHotelMapper().selectByPrimaryKey(hotelId);
		//判断查询结果
		if(thotel != null){
			//返回当前酒店rulecode值 
			return thotel.getRulecode();
		}
		//默认返回A规则
		return HmsRuleCodeEnum.A_RULE.getRuleCode();
	}
	
	/**
	 * 获取用户对对象
	 * @param example 查询条件
	 * @return 用户对象
	 */
	private User getUserInfo(UserCriteria example) {
		List<User> list = this.getUserMapper().selectByExample(example);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 获取二维码对象
	 * @param example 查询条件
	 * @return 二维码对象
	 */
	private Qrcode getQrcodeInfo(QrcodeCriteria example) {
		List<Qrcode> list = this.getQrcodeMapper().selectByExample(example);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	private UserMapper getUserMapper() {
		return userMapper;
	}

	private GroupMapper getGroupMapper() {
		return groupMapper;
	}

	private GroupHotelMapper getGroupHotelMapper() {
		return groupHotelMapper;
	}

	private EHotelMapper geteHotelMapper() {
		return eHotelMapper;
	}

	private UserRoleMapper getUserRoleMapper() {
		return userRoleMapper;
	}

	private RoleMapper getRoleMapper() {
		return roleMapper;
	}

	private MUserMapper getmUserMapper() {
		return mUserMapper;
	}

	private MRoleUserMapper getmRoleUserMapper() {
		return mRoleUserMapper;
	}
	
	private THotelMapper gettHotelMapper() {
		return tHotelMapper;
	}

	private QrcodeMapper getQrcodeMapper() {
		return qrcodeMapper;
	}

	private OperateLogMapper getOperateLogMapper() {
		return operateLogMapper;
	}
	
	private I2DimCodesService getI2DimCodesService() {
		return i2DimCodesService;
	}

	private SmsService getSmsService() {
		return smsService;
	}
	
}
