package com.mk.hms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.enums.HmsHRoleTypeEnum;
import com.mk.hms.enums.HmsVisibleEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.HmsUserMapper;
import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.HmsGroupHotelModel;
import com.mk.hms.model.HmsGroupModel;
import com.mk.hms.model.HmsHQrcodeModel;
import com.mk.hms.model.HmsRoleModel;
import com.mk.hms.model.HmsUserModel;
import com.mk.hms.model.HmsUserRoleModel;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.User;
import com.mk.hms.utils.HmsStringUtils;
import com.mk.hms.utils.RequestUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.LoginUser;

/**
 * 酒店用户信息
 * @author hdy
 */
@Service
@Transactional
public class HmsUserService{

	@Autowired
	private HmsUserMapper hmsUserMapper;
	
	/**
	 * 查找某个用户信息
	 * @param loginname 登录名
	 * @return 用户信息
	 */
	public HmsUserModel findHmsUserByLoginName(String loginname) {
		return hmsUserMapper.findHmsUserByLoginName(loginname);
	}
	
	/**
	 * 查找某个用户信息
	 * @param loginname 登录名
	 * @return 用户信息
	 */
	public HmsUserModel findHmsUserById(long id) {
		return hmsUserMapper.findHmsUserById(id);
	}
	
	/**
	 * 根据酒店id, 获取用户列表
	 * @param hotelId 酒店id
	 * @return 用户列表
	 */
	public List<HmsRoleModel> findHotelRoleIds(long hotelId) {
		return hmsUserMapper.findHotelRoleIds(hotelId);
	}
	
	/**
	 * 根据酒店id, 获取用户列表
	 * @param hotelId 酒店id
	 * @return 用户列表
	 */
	public List<HmsUserRoleModel> findUserRoleListByRoleIds(String roleIds) {
		return hmsUserMapper.findUserRoleListByRoleIds(roleIds);
	}
	
	/**
	 * 根据用户ids, 获取用户课表
	 * @param ids 用户id
	 * @return 用户列表
	 */
	public List<HmsUserModel> findHmsUserByIds(String ids) {
		return hmsUserMapper.findHmsUserByIds(ids);
	}
	
	/**
	 * 获取分组对象
	 * @param hotelId 酒店id
	 * @return 分组对象
	 */
	public HmsGroupHotelModel findGroupByHotelId(long hotelId) {
		return hmsUserMapper.findGroupByHotelId(hotelId);
	}
	
	/**
	 * 获取分组对象
	 * @param id id
	 * @return 分组对象
	 */
	public HmsGroupModel findGroup(long id) {
		return hmsUserMapper.findGroup(id);
	}
	
	/**
	 * 设置session登录用户信息
	 * @param loginname 登录名
	 */
	/*public void setSessionLoginUser(String loginname) {
		HmsLoginUserModel loginUser = new HmsLoginUserModel();
		//获取用户信息
		HmsUserModel user = hmsUserMapper.findHmsUserByLoginName(loginname);
		loginUser.setUser(user);
		//获取分组信息
		HmsGroupModel group = hmsUserMapper.findGroup(user.getGroupid());
		loginUser.setGroup(group);
		//判断；如果是老板则直接获取酒店列表
		if (user.getLoginname().equals(group.getRegphone())) {
			//获取用户分组中间表列表
			List<HmsGroupHotelModel> groupHotelList = hmsUserMapper.findGroupHotelList(group.getId());
			Object[] bossHotelIds = new Object[groupHotelList.size()];
			for (int i = 0; i < groupHotelList.size(); i++) {
				bossHotelIds[i] = groupHotelList.get(i).getHotelid();
			}
			String bossEHotelInSql = StringUtils.join(bossHotelIds, ContentUtils.CHAR_COMMA);
			if (StringUtils.isBlank(bossEHotelInSql)) {
				SessionUtils.setSessionLoginUser(loginUser);
				return;
			}
			HmsSessionUtils.setRegUser(user);
			//获取老板对应e酒店列表
			loginUser.setHotels(hmsUserMapper.findEHotels(bossEHotelInSql));
		//前台通过前台角色获取对应酒店
		} else { 
			//获取用户角色中间表数据
			List<HmsUserRoleModel> userRoleList = hmsUserMapper.findUserRoleList(user.getId());
			Object[] roleIds = new Object[userRoleList.size()];
			for (int i = 0; i < userRoleList.size(); i++) {
				roleIds[i] = userRoleList.get(i).getRoleid();
			}
			String roleInSql = StringUtils.join(roleIds, ContentUtils.CHAR_COMMA);
			//获取角色列表
			List<HmsRoleModel> roleList = hmsUserMapper.findRoles(roleInSql);
			Object[] waiterHotelIds = new Object[roleList.size()];
			for (int i= 0; i < roleList.size(); i++) {
				waiterHotelIds[i] = roleList.get(i).getHotelid();
			}
			String waiterEHotelInSql = StringUtils.join(waiterHotelIds, ContentUtils.CHAR_COMMA);
			if (StringUtils.isBlank(waiterEHotelInSql)) {
				HmsSessionUtils.setSessionLoginUser(loginUser);
				return;
			}
			//获取前台管理酒店列表
			loginUser.setHotels(hmsUserMapper.findEHotels(waiterEHotelInSql));
		}
		//添加用户所属角色
		loginUser.setRoles(hmsUserMapper.findMyRoles(user.getId()));
		//设置session 登录用户信息
		HmsSessionUtils.setSessionLoginUser(loginUser);
	}*/
	
	/**
	 * 修改用户密码
	 * @param pswMD5 密码(MD5加密)
	 * @param id 用户id
	 * @param loginname 登录名
	 * @return 受影响条数
	 */
	public int modifyUserPwd(String pswMD5, long id, String loginname) {
		return hmsUserMapper.modifyUserPwd(pswMD5, id, loginname);
	}
	
	/**
	 * 修改PMS用户密码
	 * @param pswMD5 密码(MD5加密)
	 * @param id 用户id
	 * @return 受影响条数
	 */
	public int modifyPmsUserPwd(String pswMD5, long id) {
		return hmsUserMapper.modifyPmsUserPwd(pswMD5, id);
	}
	
	/**
	 * 添加酒店用户信息
	 * @param loginname 登录名
	 * @param psw 密码
	 * @param groupid 组名
	 * @param name 昵称
	 * @return 添加实体对象
	 */
	public HmsUserModel addHotelUser(String loginname, String psw, long groupid, String name) {
		HmsUserModel user = new HmsUserModel();
		user.setLoginname(loginname);
		user.setPsw(psw);
		user.setGroupid(groupid);
		user.setName(name);
		user.setBegintime(new Date());
		user.setVisible(HmsVisibleEnum.T.getValue());
		hmsUserMapper.addHotelUser(user);
		return user;
	}
	
	/**
	 * 添加用户角色信息
	 * @param userid 用户id
	 * @param roleid 角色id
	 * @return 用户角色添加实体
	 */
	public HmsUserRoleModel addHotelUserRole(long userid, long roleid) {
		HmsUserRoleModel userRole = new HmsUserRoleModel();
		userRole.setUserid(userid);
		userRole.setRoleid(roleid);
		hmsUserMapper.addHotelUserRole(userRole);
		return userRole;
	}
	
	/**
	 * 根据tag，获取二维码数据
	 * @param tag 二维码标示
	 * @param hotelid 酒店id
	 * @return 二维码对象
	 */
	public HmsHQrcodeModel findQrcodeByTag(String tag, long hotelid) {
		return hmsUserMapper.findQrcodeByTag(tag, hotelid);
	}
	
	/**
	 * 根据userid，获取二维码数据
	 * @param userid 用户id
	 * @param hotelid 酒店id
	 * @return 二维码对象
	 */
	public HmsHQrcodeModel findQrcodeByUserId(long userid, long hotelid) {
		return hmsUserMapper.findQrcodeByUserId(userid, hotelid);
	}
	
	/**
	 * 修改二维码绑定
	 * @param tag 要绑定二维码标示
	 * @param userid 用户id
	 * @param hotelid 酒店id
	 * @param oldTag 已绑定二维码标示
	 * @return 绑定是否成功
	 */
	public boolean updateQrcode(String tag, long userid, long hotelid, String oldTag) {
		int count = 0;
		if (null != oldTag) {
			count = hmsUserMapper.updateQrcodeUser(tag, userid, hotelid, oldTag);
			if(count>0){
				hmsUserMapper.updateNewQrcode(hotelid, oldTag,tag);				
			}
		} else {
			count = hmsUserMapper.updateQrcode(userid, hotelid, tag);
		}
		return count == 1 ? true : false;
	}
	
	/**
	 * 获取可用二维码
	 * @param hotelid 酒店id
	 * @param userid 用户id
	 * @return 二维码列表
	 */
	public List<HmsHQrcodeModel> findAvailableQrcodes(long hotelid, long userid) {
		return hmsUserMapper.findAvailableQrocodes(hotelid, userid);
	}
	
	/**
	 * 获取可用二维码
	 * @param hotelid 酒店id
	 * @return 二维码列表
	 */
	public List<HmsHQrcodeModel> findAvailableQrcodes(long hotelid) {
		return hmsUserMapper.findAvailableQrocodesByHotel(hotelid);
	}
	
	/**
	 * 修改用户昵称
	 * @param loginname 登录名
	 * @param name 昵称
	 * @param tag 二维码
	 * @return 受影响条数
	 */
	public int modifyUser(String name, String loginname) {
		return hmsUserMapper.modifyUser(name, loginname);
	}
	
	/**
	 * 解绑二维码
	 * @param hotelid 酒店id
	 * @param tag 二维码标示
	 * @param userid 用户id
	 * @return 解绑受影响条数
	 */
	public int unbindQrcode(long hotelid, String tag, long userid) {
		return hmsUserMapper.unbindQrcode(hotelid, tag, userid);
	}
	
	/**
	 * 注册用户
	 * @param user 用户对象
	 * @param group 分组对象
	 * @return 用户对象
	 */
	public HmsUserModel regUser(HmsUserModel user, HmsGroupModel group) {
		int count = hmsUserMapper.addGroup(group);
		if (count == 1) {
			user.setGroupid(group.getId());
			int userCount = hmsUserMapper.addHotelUser(user);
			if (userCount == 1) {
			}
			return user;
		}
		return null;
	}
	
	/**
	 * 通过用户id、酒店id获取用户二维码
	 * @return 二维码对象
	 * @throws SessionTimeOutException 
	 */
	public HmsHQrcodeModel findQrcodeByThisUserId() throws SessionTimeOutException {
		LoginUser loginUser = SessionUtils.getSessionLoginUser();
		if (null != loginUser) {
			User user = loginUser.getUser();
			EHotelWithBLOBs thisHotel = loginUser.getThisHotel();
			HmsHQrcodeModel qrCode = findQrcodeByUserId(user.getId(), thisHotel.getId());
			return qrCode;
		}
		return null;
	}
	
	/**
	 * 解绑酒店前台手机号
	 * @return 解绑状态
	 * @throws SessionTimeOutException 
	 */
	public OutModel unbindMobilePhone() throws SessionTimeOutException {
		OutModel out = new OutModel(false);
		Map<String, Object>  params = RequestUtils.getParameters();
		String phoneNo = (String) params.get("phoneNo");
		String pwd = (String) params.get("pwd");
		String tag = (String) params.get("tag");
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
		// 老板权限才可以解绑手机
		if (!SessionUtils.isHotelManager()) {
			out.setErrorMsg("不没有权限解绑手机号，请联系酒店老板");
			return out;
		}
		// 获取手机号用户信息
		HmsUserModel user = hmsUserMapper.findHmsUserByLoginName(phoneNo);
		if (null == user) {
			out.setErrorMsg("没有该手机号用户信息");
			return out;
		}
		long hotelid = loginUser.getThisHotel().getId();
		long userId = user.getId();
		// 获取二维码信息
		HmsHQrcodeModel qrCode = hmsUserMapper.findQrcodeByTag(tag, hotelid);
		if (null != qrCode) {
			// 判断该二维码是否为此用户绑定
			if (qrCode.getUserid() > 0 && qrCode.getUserid() != userId) {
				out.setErrorMsg("该二维码不为此用户绑定");
				return out;
			}
			// 解绑二维码
			int unbindQrcodeCount = hmsUserMapper.unbindQrcode(hotelid, tag, userId);
			if (unbindQrcodeCount != 1) {
				out.setErrorMsg("手机二维码解绑失败");
				return out;
			}
		}
		// 获取酒店前台角色
		HmsRoleModel hotelWaiterRole = hmsUserMapper.findHotelRoleByHotelIdAndType(hotelid, HmsHRoleTypeEnum.Waiter.getValue());
		if (null == hotelWaiterRole) {
			out.setErrorMsg("获取酒店前台角色信息失败");
			return out;
		}
		// 删除用户信息
		int delUserCount = hmsUserMapper.updateHmsUserById(userId, new Date());
		if (delUserCount != 1) {
			out.setErrorMsg("删除用户信息失败");
			return out;
		}
		// 删除用户角色中间表信息
		/*int hotelUserRoleCount = 0;
		// int hotelUserRoleCount = hmsUserMapper.deleteUserRoleByRoleIdAndUserId(hotelWaiterRole.getId(), userId);
		List<HmsUserRoleModel> userRoleList = hmsUserMapper.findUserRoleList(userId);
		if (userRoleList.size() == 1) {
			hotelUserRoleCount = hmsUserMapper.deleteUserRoleByUserId(userId);
		} else {
			hotelUserRoleCount = hmsUserMapper.deleteUserRoleByRoleIdAndUserId(hotelWaiterRole.getId(), userId);
		}
		if (hotelUserRoleCount != 1) {
			out.setErrorMsg("删除用户角色信息失败");
			return out;
		}*/
		out.setSuccess(true);
		return out;
	}
}
