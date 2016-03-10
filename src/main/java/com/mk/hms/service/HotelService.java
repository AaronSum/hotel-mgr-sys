package com.mk.hms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mk.hms.enums.HmsEHotelPmsStatusEnum;
import com.mk.hms.enums.HmsEHotelStatusEnum;
import com.mk.hms.enums.HmsHRoleTypeEnum;
import com.mk.hms.enums.HmsRuleCodeEnum;
import com.mk.hms.enums.HmsTBusinessZoneTypeEnum;
import com.mk.hms.enums.HmsVisibleEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.CityMapper;
import com.mk.hms.mapper.DistrictMapper;
import com.mk.hms.mapper.EHotelBussinesszoneMapper;
import com.mk.hms.mapper.EHotelFacilityMapper;
import com.mk.hms.mapper.EHotelMapper;
import com.mk.hms.mapper.FacilityMapper;
import com.mk.hms.mapper.GroupHotelMapper;
import com.mk.hms.mapper.MRoleUserMapper;
import com.mk.hms.mapper.MUserMapper;
import com.mk.hms.mapper.OperateLogMapper;
import com.mk.hms.mapper.ProvinceMapper;
import com.mk.hms.mapper.QrcodeMapper;
import com.mk.hms.mapper.RoleMapper;
import com.mk.hms.mapper.TBussinesszoneMapper;
import com.mk.hms.mapper.THotelMapper;
import com.mk.hms.mapper.UserMapper;
import com.mk.hms.mapper.UserRoleMapper;
import com.mk.hms.model.City;
import com.mk.hms.model.CityCriteria;
import com.mk.hms.model.District;
import com.mk.hms.model.DistrictCriteria;
import com.mk.hms.model.EHotel;
import com.mk.hms.model.EHotelBussinesszone;
import com.mk.hms.model.EHotelBussinesszoneCriteria;
import com.mk.hms.model.EHotelCriteria;
import com.mk.hms.model.EHotelFacility;
import com.mk.hms.model.EHotelFacilityCriteria;
import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.Facility;
import com.mk.hms.model.FacilityCriteria;
import com.mk.hms.model.GroupHotel;
import com.mk.hms.model.MRoleUser;
import com.mk.hms.model.MRoleUserCriteria;
import com.mk.hms.model.MUser;
import com.mk.hms.model.MUserCriteria;
import com.mk.hms.model.MUserCriteria.Criteria;
import com.mk.hms.model.OperateLog;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.Province;
import com.mk.hms.model.ProvinceCriteria;
import com.mk.hms.model.Qrcode;
import com.mk.hms.model.Role;
import com.mk.hms.model.TBussinesszone;
import com.mk.hms.model.TBussinesszoneCriteria;
import com.mk.hms.model.THotelWithBLOBs;
import com.mk.hms.model.User;
import com.mk.hms.model.UserCriteria;
import com.mk.hms.model.UserRole;
import com.mk.hms.model.VerifyPhoneModel;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsDateUtils;
import com.mk.hms.utils.HmsFileUtils;
import com.mk.hms.utils.HmsStringUtils;
import com.mk.hms.utils.RequestUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.BussinesszonesHotel;
import com.mk.hms.view.LoginUser;
import com.mk.hms.view.RegHotel;

/**
 * hotel service
 * @author hdy
 *
 */
@Service
@Transactional
public class HotelService {
	
	@Autowired
	private EHotelMapper eHotelMapper = null;
	
	@Autowired
	private THotelMapper tHotelMapper = null;
	
	@Autowired
	private EHotelBussinesszoneMapper eHotelBussinesszoneMapper = null;
	
	@Autowired
	private TBussinesszoneMapper tBussinesszoneMapper = null;
	
	@Autowired
	private OperateLogMapper operateLogMapper = null;
	
	@Autowired
	private GroupHotelMapper groupHotelMapper = null;
	
	@Autowired
	private QrcodeMapper qrcodeMapper = null;
	
	@Autowired
	private RoleMapper roleMapper = null;
	
	@Autowired
	private UserRoleMapper userRoleMapper = null;
	
	@Autowired
	private ProvinceMapper provinceMapper = null;
	
	@Autowired
	private CityMapper cityMapper = null;
	
	@Autowired
	private DistrictMapper districtMapper = null;
	
	@Autowired
	private UserMapper userMapper = null;
	
	@Autowired
	private MUserMapper mUserMapper = null;
	
	@Autowired
	private MRoleUserMapper mRoleUserMapper = null;
	
	@Autowired
	private FacilityMapper facilityMapper = null;
	
	@Autowired
	private EHotelFacilityMapper eHotelFacilityMapper = null;
	
	/**
	 * 根据主键获取酒店信息
	 * @param hotelId 酒店主键
	 * @return 酒店对象
	 */
	public EHotelWithBLOBs byId (long hotelId) {
		return this.geteHotelMapper().selectByPrimaryKey(hotelId);
	}
	
	/**
	 * 根据名字获取酒店信息
	 * @param hotelName 酒店名称
	 * @return 酒店对象
	 */
	public EHotel byName(String hotelName) {
		EHotelCriteria eHotelCriteria = new EHotelCriteria();
		eHotelCriteria.createCriteria().andHotelNameEqualTo(hotelName);
		List<EHotel> eHotels = this.geteHotelMapper().selectByExample(eHotelCriteria);
		return eHotels.size() > 0 ? eHotels.get(0) : null;
	}
	
	/**
	 * 保存商圈信息
	 * @param bussinesszonesHotel 商圈信息对象
	 * @param hotel 酒店对象
	 * @return 保存结果
	 * @throws Exception 例外
	 */
	public OutModel saveBussinesszones(BussinesszonesHotel bussinesszonesHotel, EHotel hotel) throws Exception {
		// 返回对象
		OutModel out = new OutModel(false);
		out.setErrorMsg("信息保存失败");
		
		EHotelWithBLOBs eHotel = this.createEhotelByBussine(bussinesszonesHotel, hotel);
		
		// 过滤数据
		// 待删除商圈信息id集合
		List<Long> deleteIds = new ArrayList<Long>();
		// 待添加商圈信息列表
		List<TBussinesszone> addList = new ArrayList<TBussinesszone>();
		// 过滤商圈数据
		this.filterList(deleteIds, addList, this.fmtBussinesszones(bussinesszonesHotel.getCircleList()), this.getMyCircleList());
		// 过滤地铁数据
		this.filterList(deleteIds, addList, this.fmtBussinesszones(bussinesszonesHotel.getSubwayList()), this.getMySubwayList());
		// 过滤周边大学数据
		this.filterList(deleteIds, addList, this.fmtBussinesszones(bussinesszonesHotel.getUniversityList()), this.getMyUniversityList());
		// 保存数据
		this.saveBussinesszones(eHotel, deleteIds, addList);
		// 添加操作日志信息
		OperateLog log = this.addOperateLog("e_hotel", "saveBussinesszones", "酒店商圈信息");
		if (log.getId() <= 0) {
			out.setErrorMsg("登录用户过期");
			return out;
		}
		out.setSuccess(true);
		out.setErrorMsg("");
		return out;
	}
	
	/**
	 * 注册酒店
	 * @param regHotel 酒店对象
	 * @return 注册成功酒店信息
	 * @throws IOException 例外
	 * @throws SessionTimeOutException 
	 */
	public OutModel regHotel(RegHotel regHotel) throws IOException, SessionTimeOutException {
		
		OutModel out = new OutModel(false);
		// 初始化酒店数据
		EHotelWithBLOBs hotel = createRegHotel(regHotel);
		
		// 注册用户信息是否过期
		User user = SessionUtils.getRegUser();
		if (null == user) {
			out.setErrorMsg("注册用户信息已过期，请先登录系统继续添加酒店");
			return out;
		}
		
		// 保存酒店数据
		EHotelWithBLOBs returnHotel = this.saveRegHotel(hotel, user);
		if (returnHotel.getId() < 0) {
			out.setErrorMsg("注册酒店错误");
			return out;
		}
		SessionUtils.setRegHotel(returnHotel);
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * 获取指定省份
	 * @param provcode 省份code
	 * @return 指定省份信息
	 */
	public Province findProvinceByCode(String provcode) {
		ProvinceCriteria provinceCriteria = new ProvinceCriteria();
		provinceCriteria.createCriteria().andCodeEqualTo(provcode);
		return this.getProvinceMapper().selectByExample(provinceCriteria).get(0);
	}

	/**
	 * 获取省份列表
	 * @return 省份列表
	 */
	public List<Province> findProvinceList() {
		ProvinceCriteria provinceCriteria = new ProvinceCriteria();
		provinceCriteria.setOrderByClause("ProSort ASC");
		return this.getProvinceMapper().selectByExample(provinceCriteria);
	}
	
	/**
	 * 获取指定城市
	 * @param citycode 城市code
	 * @return 指定城市信息
	 */
	public City findCityByCode(String citycode) {
		CityCriteria cityCriteria = new CityCriteria();
		cityCriteria.createCriteria().andCodeEqualTo(citycode);
		return this.getCityMapper().selectByExample(cityCriteria).get(0);
	}
	
	/**
	 * 获取指定省份城市
	 * @param proId 省份Id
	 * @return 指定省份城市信息列表
	 */
	public List<City> findCityList(int proId) {
		CityCriteria cityCriteria = new CityCriteria();
		cityCriteria.setOrderByClause("CitySort ASC");
		cityCriteria.createCriteria().andProIDEqualTo(proId);
		return this.getCityMapper().selectByExample(cityCriteria);
	}
	
	/**
	 * 获取指定省份城市
	 * @param provcode 省份code
	 * @return 指定省份城市信息列表
	 */
	public List<City> findCityListByCode(String provcode) {
		Province province = findProvinceByCode(provcode);
		Long proId = province.getProId();
		CityCriteria cityCriteria = new CityCriteria();
		cityCriteria.setOrderByClause("CitySort ASC");
		cityCriteria.createCriteria().andProIDEqualTo(proId.intValue());
		return this.getCityMapper().selectByExample(cityCriteria);
	}
	
	/**
	 * 获取指定区县
	 * @param discode 区县code
	 * @return 指定区县信息
	 */
	public District findDistrictByCode(String discode) {
		DistrictCriteria districtCriteria = new DistrictCriteria();
		districtCriteria.createCriteria().andCodeEqualTo(discode);
		return this.getDistrictMapper().selectByExample(districtCriteria).get(0);
	}
	
	/**
	 * 获取指定城市区县信息列表
	 * @param cityId 城市id
	 * @return 区县信息列表
	 */
	public List<District> findDistrictList(int cityId) {
		DistrictCriteria districtCriteria = new DistrictCriteria();
		districtCriteria.setOrderByClause("DisSort ASC");
		districtCriteria.createCriteria().andCityIDEqualTo(cityId);
		return this.getDistrictMapper().selectByExample(districtCriteria);
	}
	
	/**
	 * 获取指定城市区县信息列表
	 * @param citycode 城市code
	 * @return 区县信息列表
	 */
	public List<District> findDistrictListByCode(String citycode) {
		City city = findCityByCode(citycode);
		Long cityId = city.getCityId();
		DistrictCriteria districtCriteria = new DistrictCriteria();
		districtCriteria.setOrderByClause("DisSort ASC");
		districtCriteria.createCriteria().andCityIDEqualTo(cityId.intValue());
		return this.getDistrictMapper().selectByExample(districtCriteria);
	}
	
	/**
	 * 获取地理位置区县id
	 * @param id 区县id
	 * @return 坐标对象
	 */
	public District districtById(long id) {
		return this.getDistrictMapper().selectByPrimaryKey(id);
	}
	
	/**
	 * 获取城市坐标对象
	 * @param id 城市id
	 * @return 城市主键
	 */
	public City cityById (long id) {
		return this.getCityMapper().selectByPrimaryKey(id);
	}
	
	/**
	 * 修改密码
	 * @param newPassword 用户新密码
	 * @param newPasswordJin 销售新密码
	 * @return
	 * @throws SessionTimeOutException 
	 */
	public OutModel modifyPassword(String newPassword, String newPasswordJin) throws SessionTimeOutException {
		OutModel out = new OutModel(false);
		String newPasswordStr = "";
		if (HmsStringUtils.isNotBlank(newPassword)) {
			newPasswordStr = newPassword;
		} else if (HmsStringUtils.isNotBlank(newPasswordJin)) {
			newPasswordStr = newPasswordJin;
		}
		if (HmsStringUtils.isBlank(newPasswordStr)) {
			out.setErrorMsg("密码不能为空");
			return out;
		}
		VerifyPhoneModel retrievePasswordUser = SessionUtils.getRetrievePasswordUser();
		if (null == retrievePasswordUser) {
			out.setErrorMsg("数据已过期，请从新获取验证码");
			return out;
		}
		String phoneNo = retrievePasswordUser.getPhnoeNo();
		if (HmsStringUtils.isNoneBlank(newPassword)) {
			UserCriteria userCriteria = new UserCriteria();
			userCriteria.createCriteria().andLoginnameEqualTo(phoneNo);
			List<User> users =  this.getUserMapper().selectByExample(userCriteria);
			User user = users.size() > 0 ? users.get(0) : null;
			if (null == user) {
				out.setErrorMsg("该用户不存在");
				return out;
			}
			String newPwdMd5 = HmsStringUtils.encrypByMd5(newPassword, 32);
			user.setPsw(newPwdMd5);
			this.getUserMapper().updateByPrimaryKey(user);
		} else if (HmsStringUtils.isNoneBlank(newPasswordJin)) {
			MUserCriteria mUserCriteria = new MUserCriteria();
			Criteria loginNameCriteria = mUserCriteria.createCriteria();
			loginNameCriteria.andLoginnameEqualTo(phoneNo);
			Criteria phoneCriteria = mUserCriteria.createCriteria();
			phoneCriteria.andPhoneEqualTo(phoneNo);
			mUserCriteria.or(phoneCriteria);
			List<MUser> mUsers = this.getmUserMapper().selectByExample(mUserCriteria);
			MUser mUser = mUsers.size() > 0 ? mUsers.get(0) : null;
			if (null == mUser) {
				out.setErrorMsg("该用户没有绑定此手机号，请确认您的个人信息已绑定手机号码");
				return out;
			}
			String newPwdMd5 = HmsStringUtils.encrypByMd5(newPasswordJin, 32);
			mUser.setPsw(newPwdMd5);
			this.getmUserMapper().updateByPrimaryKey(mUser);
		}
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * 获取销售用户列表
	 * @return 销售用户列表
	 */
	public List<MUser> findPmsUsers() {
		MUserCriteria mUserCriteria = new MUserCriteria();
		mUserCriteria.createCriteria();
		return this.getmUserMapper().selectByExample(mUserCriteria);
	}
	
	/**
	 * 获取销售用户对象
	 * @param pmsCode 用户code
	 * @return 销售用户实体
	 */
	public MUser findPmsUserByPmsCode(String pmsCode) {
		MUserCriteria mUserCriteria = new MUserCriteria();
		mUserCriteria.createCriteria().andLoginnameEqualTo(pmsCode);
		List<MUser> mUsers = this.getmUserMapper().selectByExample(mUserCriteria);
		return mUsers.size() > 0 ? mUsers.get(0) : null;
	} 
	
	/**
	 * 判断是否有审核角色
	 * @return 状态
	 * @throws IOException 例外
	 * @throws SessionTimeOutException 
	 */
	public OutModel isPmsCheckUserRole() throws IOException, SessionTimeOutException {
		String pmsCheckUserRoleCode = "";
		OutModel out = new OutModel(false);
		pmsCheckUserRoleCode = HmsFileUtils.getSysContentItem(ContentUtils.PMS_CHECK_USER_ROLE_CODE);
		if (HmsStringUtils.isBlank(pmsCheckUserRoleCode)) {
			out.setErrorMsg("获取审核用户角色编码失败");
			return out;
		}
		MUser mUser = SessionUtils.getSessionPmsUser();
		if (null == mUser) {
			out.setErrorMsg("PMS用户未登录");
			return out;
		}
		MRoleUserCriteria mRoleUserCriteria = new MRoleUserCriteria();
		mRoleUserCriteria.createCriteria().andUseridEqualTo(mUser.getId());
		List<MRoleUser> mRoleUsers = this.getmRoleUserMapper().selectByExample(mRoleUserCriteria);
		
		MRoleUser mRoleUser = mRoleUsers.size() > 0 ? mRoleUsers.get(0) : null;
		if (null == mRoleUser) {
			out.setErrorMsg("PMS用户角色获取失败");
			return out;
		}
		if (mRoleUser.getRoleid() == Integer.parseInt(pmsCheckUserRoleCode)) {
			out.setSuccess(true);
		}
		return out;
	}
	
	/**
	 * 获取酒店设置分类
	 * @param binding 匹配类型
	 * @param visible 是否可用标示，T－可用，F－不可用
	 * @return 酒店设置列表
	 */
	public List<Facility> finsTFacilities(String binding, String visible) {
		FacilityCriteria example = new FacilityCriteria();
		example.setOrderByClause("facType asc, facSort asc");
		example.createCriteria().andBindingEqualTo(binding).andVisibleEqualTo(visible);
		return this.getFacilityMapper().selectByExample(example);
	}
	
	/**
	 * 获取酒店已配置设备
	 * @return 酒店配置设备列表
	 * @throws SessionTimeOutException 
	 */
	public List<EHotelFacility> findEHotelFacilities() throws SessionTimeOutException {
		EHotelFacilityCriteria example = new EHotelFacilityCriteria();
		example.createCriteria().andHotelIdEqualTo(SessionUtils.getThisHotel().getId());
		return this.geteHotelFacilityMapper().selectByExample(example);
	}
	
	/**
	 * 取T表对应酒店id的rulecode值
	 * @param hotels 酒店id
	 */
	public int findRuleCode(long hotelId){
		//查询T表
		THotelWithBLOBs thotel = this.gettHotelMapper().selectByPrimaryKey(hotelId);
		//判断查询结果
		if(thotel != null){
			//返回当前酒店rulecode值 
			return null==thotel.getRulecode() ? HmsRuleCodeEnum.B_RULE.getRuleCode() : thotel.getRulecode();
		}
		//默认返回B规则
		return HmsRuleCodeEnum.B_RULE.getRuleCode();
	}
	
	/**
	 * 保存酒店设备信息
	 * @return 保存状态数据
	 * @throws SessionTimeOutException 
	 */
	public OutModel saveTFaciliesList(String tFaciliesString) throws SessionTimeOutException {
		OutModel out= new OutModel(false);
		String[] tFacilityArray = tFaciliesString.split(ContentUtils.CHAR_COMMA);
		this.saveTFacilityList(tFacilityArray);
		out.setSuccess(true);
		// 添加操作日志信息
		OperateLog logModel = this.addOperateLog("e_hotel", "saveTFacilies", "酒店设备信息");
		if (null == logModel) {
			out.setErrorMsg("登录用户过期");
			return out;
		}
		return out;
	}
	
	/**
	 * 获取商圈信息
	 * @return 商圈信息
	 * @throws SessionTimeOutException 
	 */
	public Map<String, Object> getBussinesszoneList() throws SessionTimeOutException {
		Map<String, Object> out = new HashMap<String, Object>();
		out.put("circleList", this.getBussinesszoneListByType(HmsTBusinessZoneTypeEnum.Circle.getValue()));
		out.put("myCircleList", this.getMyCircleList());
		out.put("subwayList", this.getBussinesszoneListByType(HmsTBusinessZoneTypeEnum.Subway.getValue()));
		out.put("mySubwayList", this.getMySubwayList());
		out.put("universityList", this.getBussinesszoneListByType(HmsTBusinessZoneTypeEnum.University.getValue()));
		out.put("myUniversityList", this.getMyUniversityList());
		return out;
	}
	
	/**
	 * 保存酒店交通信息
	 * @return 交通信息
	 * @throws SessionTimeOutException 
	 */
	public OutModel saveEHotelTraffic(String metro, String airport, String bus, String taxi) throws SessionTimeOutException {
		JSONObject obj = new JSONObject();
		obj.element("metro", metro);
		obj.element("airport", airport);
		obj.element("bus", bus);
		obj.element("taxi", taxi);
		String trafficText = obj.toString();
		int count = this.updateEHotelTraffic(trafficText);
		OutModel out = new OutModel();
		out.setSuccess(count == 1 ? true : false);
		if (out.isSuccess()) {
			// 添加操作日志信息
			OperateLog logModel = this.addOperateLog("e_hotel", "saveEHotelTraffic", "酒店交通信息");
			if (null == logModel) {
				out.setErrorMsg("登录用户过期");
				return out;
			}
		}
		return out;
	}
	
	/**
	 * 保存酒店周边信息
	 * @return 周边信息
	 * @throws SessionTimeOutException 
	 */
	public OutModel saveEHotelPeripheral(String restaurant, String scenicSpot, String others) throws SessionTimeOutException {
		JSONObject obj = new JSONObject();
		obj.element("restaurant", restaurant);
		obj.element("scenicSpot", scenicSpot);
		obj.element("others", others);
		String peripheralText = obj.toString();
		int count = this.updateEHotelPeripheral(peripheralText);
		OutModel out = new OutModel();
		out.setSuccess(count == 1 ? true : false);
		if (out.isSuccess()) {
			// 添加操作日志信息
			OperateLog logModel = this.addOperateLog("e_hotel", "saveEHotelPeripheral", "酒店周边信息");
			if (null == logModel) {
				out.setErrorMsg("登录用户过期");
				return out;
			}
		}
		return out;
	}

	/**
	 * 修改周边信息
	 * @param peripheralText 周边信息字符串
	 * @return 受影响条数
	 * @throws SessionTimeOutException 
	 */
	private int updateEHotelPeripheral(String peripheralText) throws SessionTimeOutException {
		EHotelWithBLOBs hotel = SessionUtils.getThisHotel();
		hotel.setPeripheral(peripheralText);
		return this.geteHotelMapper().updateByPrimaryKeyWithBLOBs(hotel);
	}
	
	/**
	 * 修改交通信息
	 * @param trafficText 交通信息字符串
	 * @return 受影响条数
	 * @throws SessionTimeOutException 
	 */
	private int updateEHotelTraffic(String trafficText) throws SessionTimeOutException {
		EHotelWithBLOBs hotel = SessionUtils.getThisHotel();
		hotel.setTraffic(trafficText);
		return this.geteHotelMapper().updateByPrimaryKeyWithBLOBs(hotel);
	}
	
	/**
	 * 根据 类型获取周边信息
	 * @param type 类型
	 * @return 周边信息列表
	 * @throws SessionTimeOutException 
	 */
	private List<TBussinesszone> getBussinesszoneListByType(long type) throws SessionTimeOutException {
		EHotel hotel = SessionUtils.getThisHotel();
		long idsId = hotel.getDisId();
		TBussinesszoneCriteria example = new TBussinesszoneCriteria();
		example.createCriteria().andDisEqualTo(idsId).andBusinessZoneTypeEqualTo(type);
		return this.getTBussinesszoneMapper().selectByExample(example);
	}
	
	/**
	 * 保存配置信息
	 * @throws SessionTimeOutException 
	 */
	private void saveTFacilityList(String[] tFacilityArray) throws SessionTimeOutException {
		long thisHotelId = SessionUtils.getThisHotel().getId();
		List<EHotelFacility> tFacilityList = this.findEHotelFacilities();
		// 相同值
		List<String> sameTFacilityList = new ArrayList<String>();
		//获取相同数组项
		for (String tFacility : tFacilityArray) {
			for (EHotelFacility eHotelFacility : tFacilityList) {
				if (tFacility.equals(eHotelFacility.getFacId() + "")) {
					sameTFacilityList.add(tFacility);
				}
			}
		}
		// 删除数据库中多余项
		List<Long>  delFacIds = new ArrayList<Long>(); //被删除fac IDs
		for (EHotelFacility eHotelFacility : tFacilityList) {
			boolean isDel = true;
			for (String s : sameTFacilityList) {
				if (s.equals(eHotelFacility.getFacId() + "")) {
					isDel = false;
				}
			}
			if (isDel) {
				delFacIds.add(eHotelFacility.getFacId());
			}
		}
		this.deleteEHotelFacilities(thisHotelId, delFacIds);
		// 添加数据库没有项
		for (String tFacility : tFacilityArray) {
			boolean isAdd = true;
			for (String s : sameTFacilityList) {
				if (s.equals(tFacility)) {
					isAdd = false;
				}
			}
			if (isAdd && StringUtils.isNotBlank(tFacility)) {
				this.addEHotelFacilities(thisHotelId, Long.parseLong(tFacility));
			}
		}
	}
	
	/**
	 * 添加酒店设备信息
	 * @param hotelId 酒店id
	 * @param facId 设备信息id
	 * @return 受影响条数
	 */
	private int addEHotelFacilities(long hotelId, long facId) {
		EHotelFacility ef = new EHotelFacility();
		ef.setHotelId(hotelId);
		ef.setFacId(facId);
		return this.geteHotelFacilityMapper().insert(ef);
	}
	
	/**
	 * 删除设备信息
	 * @param hotelId 酒店id
	 * @param delFacIds 删除id集合
	 * @return
	 */
	private int deleteEHotelFacilities(long hotelId, List<Long> delFacIds) {
		if (delFacIds.size() > 0) {
			EHotelFacilityCriteria example = new EHotelFacilityCriteria();
			example.createCriteria().andHotelIdEqualTo(hotelId).andFacIdIn(delFacIds);
			return this.geteHotelFacilityMapper().deleteByExample(example);
		}
		return 0;
	}
	
	/**
	 * 保存注册酒店
	 * @param eHotel 酒店对象
	 * @param user 用户对象
	 * @return 保存之后酒店对象
	 * @throws IOException 例外
	 */
	private EHotelWithBLOBs saveRegHotel(EHotelWithBLOBs eHotel, User user) throws IOException {
		this.geteHotelMapper().insert(eHotel);
		if (eHotel.getId() > 0) {
			// 初始化酒店分组数据
			GroupHotel groupHotel = new GroupHotel();
			groupHotel.setGroupid(user.getGroupid());
			groupHotel.setHotelid(eHotel.getId());
			this.getGroupHotelMapper().insert(groupHotel);
			if (groupHotel.getId() > 0) {
				this.addQrcode(eHotel.getId());
				this.addUserRole(eHotel.getId(), user.getId());
			}
		}
		return eHotel;
	}
	
	/**
	 * 添加用户角色
	 * @param hotelId 酒店id
	 * @param userId 用户id
	 */
	private void addUserRole(long hotelId, long userId) {
		// 添加角色
		long founderRoleId = 0;
		for (HmsHRoleTypeEnum type : HmsHRoleTypeEnum.values()) {
			Role role = new Role();
			role.setName(type.getText());
			role.setType(type.getValue());
			role.setHotelid(hotelId);
			this.getRoleMapper().insert(role);
			// 创始人角色
			if (type.getValue() == HmsHRoleTypeEnum.Founder.getValue()) {
				founderRoleId = role.getId();
			}
		}
		// 添加用户角色
		UserRole userRole = new UserRole();
		userRole.setRoleid(founderRoleId);
		userRole.setUserid(userId);
		this.getUserRoleMapper().insert(userRole);
	}
	
	/**
	 * 添加二维码
	 * @param hotelId 酒店主键
	 * @throws IOException 
	 */
	private void addQrcode(long hotelId) throws IOException {
		String[] tags = ContentUtils.ALLOW_QRCODE_TAGS.split(ContentUtils.CHAR_COMMA);
		String qrContent = HmsFileUtils.getSysContentItem(ContentUtils.QRCODE_CONTTENT_PREFIX);
		for (String tag : tags) {
			Qrcode qrcode = new Qrcode();
			qrcode.setHotelid(hotelId);
			qrcode.setTag(tag.trim());
			this.getQrcodeMapper().insert(qrcode);
			if (qrcode.getId() > 0) {
				qrcode.setContent(qrContent + hotelId + "/" + qrcode.getId());
				this.getQrcodeMapper().updateByPrimaryKeySelective(qrcode);
			}
		}
	}
	
	/**
	 * 创建注册酒店实体对象
	 * @param regHotel 注册酒店对象
	 * @return 酒店实体对象
	 */
	private EHotelWithBLOBs createRegHotel(RegHotel regHotel) {
		EHotelWithBLOBs hotel = new EHotelWithBLOBs();
		hotel.setOpenTime(regHotel.getFmtOpenTime());
		hotel.setRepairTime(regHotel.getFmtRepairTime());
		hotel.setRetentionTime("180000");
		hotel.setDefaultLeaveTime("120000");
		hotel.setHotelName(regHotel.getHotelName());
		hotel.setHotelphone(regHotel.getHotelPhone());
		hotel.setHotelContactName(regHotel.getManagerName());
		hotel.setPmsUser(regHotel.getPmsCode());
		hotel.setDisId(regHotel.getDisId());
		hotel.setDetailAddr(regHotel.getDetailAddr());
		hotel.setLongitude(NumberUtils.createBigDecimal(regHotel.getLongitude() + ""));
		hotel.setLatitude(NumberUtils.createBigDecimal(regHotel.getLatitude() + ""));
		hotel.setRegTime(new Date());
		hotel.setVisible(HmsVisibleEnum.T.getValue());
		hotel.setState(HmsEHotelStatusEnum.Init.getValue());
		hotel.setIsNewPms(HmsVisibleEnum.F.getValue());
		hotel.setPmsStatus(HmsEHotelPmsStatusEnum.Init.getValue());
		hotel.setRulecode(HmsRuleCodeEnum.B_RULE.getRuleCode());

		hotel.setIntroduction(regHotel.getIntroduction());
		hotel.setQtphone(regHotel.getQtPhone());
		hotel.setAreacode(regHotel.getAreacode());
		hotel.setProvcode(regHotel.getProvcode());
		hotel.setCitycode(regHotel.getCitycode());
		hotel.setDiscode(regHotel.getDiscode());
		hotel.setIsThreshold("T");
		hotel.setAreaname(regHotel.getAreaname());
		return hotel;
	}
	
	/**
	 * 格式化商圈信息
	 * @param bussinesszonesArray 商圈信息
	 * @return 商圈信息列表
	 */
	private List<JSONObject> fmtBussinesszones(String bussinesszonesArray) {
		// 获取周边大学信息列表
		String bussinesszones = bussinesszonesArray;
		bussinesszones = HmsStringUtils.removeStart(bussinesszones, ContentUtils.CHAR_QUOTES);
		bussinesszones = HmsStringUtils.removeEnd(bussinesszones, ContentUtils.CHAR_QUOTES);
		return JSONArray.toList(JSONArray.fromObject(bussinesszones), JSONObject.class);
	}
	
	/**
	 * 创建酒店对象
	 * @param bussinesszonesHotel 商圈对象
	 * @param hotel 酒店对象
	 * @return 酒店对象
	 * @throws Exception 例外
	 */
	private EHotelWithBLOBs createEhotelByBussine(
			BussinesszonesHotel bussinesszonesHotel, EHotel hotel)
			throws Exception {
		// 酒店修改信息
		EHotelWithBLOBs eHotel = this.geteHotelMapper().selectByPrimaryKey(hotel.getId());
		eHotel.setOpenTime(bussinesszonesHotel.getFmtOpenTime());
		eHotel.setRepairTime(bussinesszonesHotel.getFmtRepairTime());
		eHotel.setHotelphone(bussinesszonesHotel.getHotelphone());
		eHotel.setHotelName(bussinesszonesHotel.getHotelName());
		eHotel.setDetailAddr(bussinesszonesHotel.getDetailAddr());
		eHotel.setRetentionTime(HmsDateUtils.getHMSFromHMSString(bussinesszonesHotel.getBegintime()));
		eHotel.setDefaultLeaveTime(HmsDateUtils.getHMSFromHMSString(bussinesszonesHotel.getEndtime()));
		eHotel.setDisId(bussinesszonesHotel.getDisId());
		eHotel.setIntroduction(bussinesszonesHotel.getIntroduction());
		eHotel.setLatitude(bussinesszonesHotel.getLatitude());
		eHotel.setLongitude(bussinesszonesHotel.getLongitude());
		eHotel.setHoteltype(bussinesszonesHotel.getHoteltype());
		eHotel.setQtphone(bussinesszonesHotel.getQtphone());
		eHotel.setAreacode(bussinesszonesHotel.getAreacode());
		eHotel.setProvcode(bussinesszonesHotel.getProvcode());
		eHotel.setCitycode(bussinesszonesHotel.getCitycode());
		eHotel.setDiscode(bussinesszonesHotel.getDiscode());
		eHotel.setAreaname(bussinesszonesHotel.getAreaname());
		eHotel.setPmsUser(bussinesszonesHotel.getPmsUser());
		return eHotel;
	}
	
	/**
	 * 添加og日志
	 * @param log 日志对象
	 * @return 日志对象
	 * @throws SessionTimeOutException 
	 */
	private OperateLog addOperateLog(String tableName, String funcCode, String funName) throws SessionTimeOutException {
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
		log.setFunctionname(funName);
		log.setHotelid(SessionUtils.getThisHotelId());
		log.setOperatetime(new Date());
		log.setIp(RequestUtils.getIp());
		this.getOperateLogMapper().insert(log);
		return log;
	}
	
	/**
	 * 保存酒店商圈信息
	 * @param eHotel 酒店实体
	 * @param circleList 商圈信息
	 * @param subwayList 地铁信息
	 * @param universityList 周边大学
	 * @throws SessionTimeOutException 
	 */
	private void saveBussinesszones(EHotelWithBLOBs eHotel, List<Long> deleteIds, List<TBussinesszone> addList) throws SessionTimeOutException {
		// 获取当前酒店对象
		EHotel thisHotel = SessionUtils.getThisHotel();
		// 保存酒店商圈基本信息
		this.geteHotelMapper().updateByPrimaryKeyWithBLOBs(eHotel);
		// 删除商圈中替换商圈
		if (deleteIds.size() > 0) {
			EHotelBussinesszoneCriteria eHotelBussinesszoneCriteria = new EHotelBussinesszoneCriteria();
			eHotelBussinesszoneCriteria.createCriteria().andBusinessZoneIdIn(deleteIds).andHotelIdEqualTo(thisHotel.getId());
			this.getEHotelBussinesszoneMapper().deleteByExample(eHotelBussinesszoneCriteria);
		}
		// 添加商圈信息
		for (TBussinesszone businesszone : addList) {
			EHotelBussinesszone eHotelBussinesszone = new EHotelBussinesszone();
			eHotelBussinesszone.setBusinessZoneId(businesszone.getId());
			eHotelBussinesszone.setHotelId(thisHotel.getId());
			this.getEHotelBussinesszoneMapper().insert(eHotelBussinesszone);
		}
	}
	/**
	 * 根据酒店id 获取商圈信息
	 * @param hotelId 酒店id
	 * @return 商圈列表
	 * @throws SessionTimeOutException 
	 */
	private List<EHotelBussinesszone> byThisHotel() throws SessionTimeOutException {
		EHotel hotel = SessionUtils.getThisHotel();
		// 获取e表中商圈信息
		EHotelBussinesszoneCriteria eHotelBussinesszoneCriteria = new EHotelBussinesszoneCriteria();
		eHotelBussinesszoneCriteria.createCriteria().andHotelIdEqualTo(hotel.getId());
		return this.getEHotelBussinesszoneMapper().selectByExample(eHotelBussinesszoneCriteria);
	}
	
	/**
	 * 获取酒店周边商圈信息列表
	 * @return 商圈列表
	 * @throws SessionTimeOutException 
	 */
	private List<TBussinesszone> getMyCircleList() throws SessionTimeOutException {
		List<EHotelBussinesszone> list = this.byThisHotel();
		List<Long> bussinesszoneIds = new ArrayList<Long>();
		for (EHotelBussinesszone bussinesszone : list) {
			bussinesszoneIds.add(bussinesszone.getBusinessZoneId());
		}
		// 获取t表商圈信息
		TBussinesszoneCriteria tBussinesszoneCriteria = new TBussinesszoneCriteria();
		final long circle = HmsTBusinessZoneTypeEnum.Circle.getValue();
		if (bussinesszoneIds.size() == 0) {
			return null;
		}
		tBussinesszoneCriteria.createCriteria().andDisIn(bussinesszoneIds).andBusinessZoneTypeEqualTo(circle);
		return this.getTBussinesszoneMapper().selectByExample(tBussinesszoneCriteria);
	}
	
	/**
	 * 获取酒店地铁信息列表表
	 * @return 地铁数据列表
	 * @throws SessionTimeOutException 
	 */
	private List<TBussinesszone> getMySubwayList() throws SessionTimeOutException {
		List<EHotelBussinesszone> list = this.byThisHotel();
		List<Long> bussinesszoneIds = new ArrayList<Long>();
		for (EHotelBussinesszone bussinesszone : list) {
			bussinesszoneIds.add(bussinesszone.getBusinessZoneId());
		}
		// 获取t表商圈信息
		TBussinesszoneCriteria tBussinesszoneCriteria = new TBussinesszoneCriteria();
		final long subway = HmsTBusinessZoneTypeEnum.Subway.getValue();
		if (bussinesszoneIds.size() == 0) {
			return null;
		}
		tBussinesszoneCriteria.createCriteria().andIdIn(bussinesszoneIds)
			.andBusinessZoneTypeEqualTo(subway).andFatheridIsNotNull();
		return this.getTBussinesszoneMapper().selectByExample(tBussinesszoneCriteria);
	}
	
	/**
	 * 获取酒店周边大学信息列表
	 * @return 大学列表
	 * @throws SessionTimeOutException 
	 */
	private List<TBussinesszone> getMyUniversityList() throws SessionTimeOutException {
		List<EHotelBussinesszone> list = this.byThisHotel();
		List<Long> bussinesszoneIds = new ArrayList<Long>();
		for (EHotelBussinesszone bussinesszone : list) {
			bussinesszoneIds.add(bussinesszone.getBusinessZoneId());
		}
		// 获取t表商圈信息
		TBussinesszoneCriteria tBussinesszoneCriteria = new TBussinesszoneCriteria();
		final long university = HmsTBusinessZoneTypeEnum.University.getValue();
		if (bussinesszoneIds.size() == 0) {
			return null;
		}
		tBussinesszoneCriteria.createCriteria().andIdIn(bussinesszoneIds).andBusinessZoneTypeEqualTo(university);
		return this.getTBussinesszoneMapper().selectByExample(tBussinesszoneCriteria);
	}
	
	/**
	 * 过滤商圈信息列表中需要删除和需要保存数据列表
	 * @param deleteIds 需要删除商圈信息id集合
	 * @param addList 需要添加商圈信息列表
	 * @param list 需要操作商圈信息集合
	 * @param myList 数据库中已存在商圈信息
	 */
	private void filterList(List<Long> deleteIds, List<TBussinesszone> addList, 
			List<JSONObject> list, List<TBussinesszone> myList) {
		if (myList == null) {
			myList = new ArrayList<TBussinesszone>();
		}
		// 获取需要删除商圈信息id列表
		for (TBussinesszone businesszone : myList) {
			boolean isExit = false;
			for (JSONObject obj : list) {
				if (businesszone.getId() == obj.getLong("id")) {
					isExit = true;
				}
			}
			if (!isExit) {
				deleteIds.add(businesszone.getId());
			}
		}
		// 获取需要添加商圈信息列表
		for (JSONObject obj : list) {
			boolean isExit = false;
			for (TBussinesszone businesszone : myList) {
				if (businesszone.getId() == obj.getLong("id")) {
					isExit = true;
					break;
				}
			}
			if (!isExit) {
				TBussinesszone circle = new TBussinesszone();
				circle.setId(obj.getLong("id"));
				circle.setBusinessZoneType(obj.getLong("businessZoneType"));
				circle.setCityid(obj.getLong("cityid"));
				circle.setDis(obj.getLong("dis"));
				circle.setFatherid(obj.getLong("fatherid"));
				circle.setName(obj.getString("name"));
				addList.add(circle);
			}
		}
	}
	
	private EHotelMapper geteHotelMapper() {
		return eHotelMapper;
	}

	private THotelMapper gettHotelMapper() {
		return tHotelMapper;
	}

	private EHotelBussinesszoneMapper getEHotelBussinesszoneMapper() {
		return eHotelBussinesszoneMapper;
	}

	private TBussinesszoneMapper getTBussinesszoneMapper() {
		return tBussinesszoneMapper;
	}

	private OperateLogMapper getOperateLogMapper() {
		return operateLogMapper;
	}

	private GroupHotelMapper getGroupHotelMapper() {
		return groupHotelMapper;
	}

	private QrcodeMapper getQrcodeMapper() {
		return qrcodeMapper;
	}

	private RoleMapper getRoleMapper() {
		return roleMapper;
	}

	private UserRoleMapper getUserRoleMapper() {
		return userRoleMapper;
	}

	private ProvinceMapper getProvinceMapper() {
		return provinceMapper;
	}

	private CityMapper getCityMapper() {
		return cityMapper;
	}

	private DistrictMapper getDistrictMapper() {
		return districtMapper;
	}

	private UserMapper getUserMapper() {
		return userMapper;
	}

	private MUserMapper getmUserMapper() {
		return mUserMapper;
	}

	private MRoleUserMapper getmRoleUserMapper() {
		return mRoleUserMapper;
	}

	private FacilityMapper getFacilityMapper() {
		return facilityMapper;
	}

	private EHotelFacilityMapper geteHotelFacilityMapper() {
		return eHotelFacilityMapper;
	}
}
