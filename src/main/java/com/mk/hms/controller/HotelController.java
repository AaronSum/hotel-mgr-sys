package com.mk.hms.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.hms.enums.HmsTFacilityBindingEnum;
import com.mk.hms.enums.HmsVisibleEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.City;
import com.mk.hms.model.District;
import com.mk.hms.model.EHotel;
import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.MUser;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.Province;
import com.mk.hms.model.VerifyPhoneModel;
import com.mk.hms.service.HotelService;
import com.mk.hms.service.UserService;
import com.mk.hms.utils.HmsDateUtils;
import com.mk.hms.utils.HmsRedisCacheUtils;
import com.mk.hms.utils.HmsStringUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.BussinesszonesHotel;
import com.mk.hms.view.EHotelWithStringDate;
import com.mk.hms.view.RegHotel;

/**
 * 酒店 控制器
 * @author hdy 
 *
 */
@Controller
@RequestMapping("/hotel")
public class HotelController {

	@Autowired
	private HotelService hotelService = null;
	
	@Autowired
	private UserService userService = null;
	
	/**
	 *  获取指定省份信息
	 * @return 省份信息
	 */
	@RequestMapping("/province")
	@ResponseBody
	public Province findProvinceByCode(String provcode) {
		return this.getHotelService().findProvinceByCode(provcode);
	}
	
	/**
	 * 获取省份信息列表
	 * @return 省份信息列表
	 */
	@RequestMapping("/provinces")
	@ResponseBody
	public List<Province> findProvinceList() {
		return this.getHotelService().findProvinceList();
	}
	
	/**
	 *  获取指定城市信息
	 * @return 城市信息
	 */
	@RequestMapping("/city")
	@ResponseBody
	public City findCityByCode(String citycode) {
		return this.getHotelService().findCityByCode(citycode);
	}
	
	/**
	 *  获取指定省份城市信息列表
	 * @return 城市信息列表
	 */
	@RequestMapping("/cities")
	@ResponseBody
	public List<City> findCityList(int proId) {
		return this.getHotelService().findCityList(proId);
	}
	
	/**
	 *  获取指定省份城市信息列表
	 * @return 城市信息列表
	 */
	@RequestMapping("/citiesbycode")
	@ResponseBody
	public List<City> findCityListByCode(String provcode) {
		return this.getHotelService().findCityListByCode(provcode);
	}
	
	/**
	 *  获取指定区县信息
	 * @return 区县信息
	 */
	@RequestMapping("/district")
	@ResponseBody
	public District findDistrictByCode(String discode) {
		return this.getHotelService().findDistrictByCode(discode);
	}
	
	/**
	 * 获取指定城市区县信息列表
	 * @return 区县信息列表
	 */
	@RequestMapping("/districts")
	@ResponseBody
	public List<District> findDistrictList(int cityId) {
		return this.getHotelService().findDistrictList(cityId);
	}
	
	/**
	 * 获取指定城市区县信息列表
	 * @return 区县信息列表
	 */
	@RequestMapping("/districtsbycode")
	@ResponseBody
	public List<District> findDistrictListByCode(String citycode) {
		return this.getHotelService().findDistrictListByCode(citycode);
	}
	
	/**
	 * 根据酒店Id获取其所在位置的省、市、县/地区数据
	 * @return 酒店所在位置的省、市、县/地区数据
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/location")
	@ResponseBody
	public Map<String,Long> findMyLocation() throws SessionTimeOutException {
		EHotel thisHotel = SessionUtils.getThisHotel();
		Map<String, Long> location = new HashMap<String, Long>();
		if (null != thisHotel) {
			int districtId = thisHotel.getDisId();
			if (districtId > 0) {
				District district = this.getHotelService().districtById(districtId);
				City cityModel = this.getHotelService().cityById(district.getCityID());
				location.put("district", district.getId());
				location.put("city", cityModel.getCityId());
				location.put("province", NumberUtils.toLong(cityModel.getProID() + ""));
			}
		}
		return location;
	}
	
	
	@RequestMapping("/thisHotel")
	@ResponseBody
	public EHotelWithStringDate thisHotel() throws SessionTimeOutException {
		EHotel eHotel = SessionUtils.getThisHotel();
        EHotelWithBLOBs eHotelWithBLOBs = this.getHotelService().byId(eHotel.getId());
        return this.getUserService().copyEhotel2EhotelWithStringDate(eHotelWithBLOBs);
	}

	/**
	 * 保存商圈信息
	 * @param eHotle 酒店对象
	 * @return 保存状态
	 * @throws Exception 
	 */
	@RequestMapping("/saveBussinesszones")
	@ResponseBody
	public OutModel saveBussinesszones (BussinesszonesHotel bussinesszonesHotel) throws Exception {
		OutModel out = new OutModel(false);
		String hotelName = bussinesszonesHotel.getHotelName().trim();
		// 酒店名称为空
		if (HmsStringUtils.isBlank(hotelName)) {
			out.setErrorMsg("酒店名称不能为空");
			return out;
		}
		// 检查同名酒店
		EHotel hotel = this.getHotelService().byName(hotelName);
		EHotel sessionHotel = SessionUtils.getThisHotel();
		if (null != hotel && !sessionHotel.getHotelName().equals(hotelName)) {
			out.setErrorMsg("酒店名称[" + hotelName + "]已存在");
			return out;
		}
		return this.getHotelService().saveBussinesszones(bussinesszonesHotel, sessionHotel);
	}
	
	/**
	 * 注册酒店
	 * @param regHotel 酒店对象
	 * @return 注册状态
	 * @throws IOException 
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/regHotel")
	@ResponseBody
	public OutModel regHotel(RegHotel regHotel) throws IOException, SessionTimeOutException {	
		// 返回对象
		OutModel out = new OutModel(false);
		String hotelName = regHotel.getHotelName();
		if (HmsStringUtils.isBlank(hotelName)) {
			out.setErrorMsg("酒店名称不能为空");
			return out;
		}
		hotelName = hotelName.trim();
		// 检查同名酒店
		EHotel hotel = this.getHotelService().byName(hotelName);
		if (null != hotel) {
			out.setErrorMsg("酒店名称[" + hotelName + "]已存在");
			return out;
		}
		return this.getHotelService().regHotel(regHotel);
	}
	
	/**
	 * 下一步
	 * @return 操作状态
	 * @throws IOException 
	 */
	@RequestMapping("/retrievePasswordNext")
	@ResponseBody
	public OutModel retrievePasswordNext(String phoneNum, int verifyCode) throws IOException {
		OutModel out = new OutModel(false);
		// 数据为空
		if (HmsStringUtils.isBlank(phoneNum) || verifyCode <= 0) {
			out.setErrorMsg("手机号或验证码不能为空");
			return out;
		}
		// 获取缓存中是否存在该手机号验证码
		if (!HmsRedisCacheUtils.verifyCodeIsValid(phoneNum, verifyCode)) {
			out.setErrorMsg("手机号和验证码不配配");
			return out;
		}
		//加入session 找回密码用户
		VerifyPhoneModel retrievePasswordUser = new VerifyPhoneModel();
		retrievePasswordUser.setPhnoeNo(phoneNum);
		retrievePasswordUser.setVerifyCode(verifyCode);
		SessionUtils.setRetrievePasswordUser(retrievePasswordUser);
		// 返回状态
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * 修改密码
	 * @return 修改状态
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/modifyPassword")
	@ResponseBody
	public OutModel modifyPassword(String newPassword, String newPasswordJin) throws SessionTimeOutException {
		return this.getHotelService().modifyPassword(newPassword, newPasswordJin);
	}
	
	/**
	 * 获取pms用户列表
	 * @return pms用户列表
	 */
	@RequestMapping("/pmsUsers")
	@ResponseBody
	public List<MUser> findPmsUserList() {
		return this.getHotelService().findPmsUsers();
	}
	
	/**
	 * 获取pms用户列表
	 * @return pms用户列表
	 */
	@RequestMapping("/pmsUserByCode")
	@ResponseBody
	public OutModel findPmsUserByPmsCode(String pmsCode) {
		MUser mUser = this.getHotelService().findPmsUserByPmsCode(pmsCode);
		OutModel out = new OutModel(false);
		if (null == mUser || mUser.getStatus() != 1) {
			out.setErrorMsg("该pms用户不存在");
			return out;
		}
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * 是否是审核用户角色
	 * @return 审核用户角色状态
	 * @throws IOException 
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/isCheckRole")
	@ResponseBody
	public OutModel isPmsCheckUserRole() throws IOException, SessionTimeOutException {
		return this.getHotelService().isPmsCheckUserRole();
	}
	
	/**
	 * 获取pms用户管理酒店信息
	 * @return 酒店信息状态
	 */
	@RequestMapping("/pmsHotel")
	@ResponseBody
	public OutModel getPmsHotel(long hotelId) {
		OutModel out = new OutModel(false);
		EHotelWithBLOBs eHotel = this.getHotelService().byId(hotelId);
		// 判断hotel信息是否存在
		if (null == eHotel) {
			out.setErrorMsg("酒店信息为空");
			return out;
		}
		SessionUtils.setSessionPmsHotel(eHotel);
		//设置参数
		Map<String, Object> paras = new HashMap<String, Object>();
		//查询T表rulecode
		int ruleCode = this.getHotelService().findRuleCode(hotelId);
		//设计酒店信息
		paras.put("eHotel", this.getUserService().copyEhotel2EhotelWithStringDate(eHotel));
		//设置规则码
		paras.put("ruleCode", ruleCode);
		out.setAttribute(paras);
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * 获取酒店设施列表和当前酒店已配置设施
	 * @return 设施列表对象
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/facilies")
	@ResponseBody
	public Map<String, Object> getTFaciliesMap() throws SessionTimeOutException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tFacilities", this.getHotelService().finsTFacilities(HmsTFacilityBindingEnum.Hotel.getValue(), HmsVisibleEnum.T.getValue()));
		map.put("myTFacilities", this.getHotelService().findEHotelFacilities());
		map.put("oldTFacilities", map.get("myTFacilities"));
		return map;
	}
	
	/**
	 * 保存酒店设备信息
	 * @return 保存状态数据
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/saveTFacilies")
	@ResponseBody
	public OutModel saveTFaciliesList(String tFaciliesString) throws SessionTimeOutException {
		return this.getHotelService().saveTFaciliesList(tFaciliesString);
	}
	
	/**
	 * 获取商圈信息
	 * @return 商圈信息
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/bussinesszones")
	@ResponseBody
	public Map<String, Object> getBussinesszoneList() throws SessionTimeOutException {
		return this.getHotelService().getBussinesszoneList();
	}
	
	/**
	 * 保存酒店交通信息
	 * @return 交通信息
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/saveTraffic")
	@ResponseBody
	public OutModel saveEHotelTraffic(String metro, String airport, String bus, String taxi) throws SessionTimeOutException {
		return this.getHotelService().saveEHotelTraffic(metro, airport, bus, taxi);
	}
	
	/**
	 * 保存酒店周边信息
	 * @return 周边信息
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/savePeripheral")
	@ResponseBody
	public OutModel saveEHotelPeripheral(String restaurant, String scenicSpot, String others) throws SessionTimeOutException {
		return this.getHotelService().saveEHotelPeripheral(restaurant, scenicSpot, others);
	}
	
	private HotelService getHotelService() {
		return hotelService;
	}

	private UserService getUserService() {
		return userService;
	}
	
}
