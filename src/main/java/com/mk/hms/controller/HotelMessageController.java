package com.mk.hms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.util.DateUtil;
import com.mk.hms.service.*;
import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.mk.hms.controller.helper.HotelMessageControllerHelper;
import com.mk.hms.db.HmsJdbcTemplate;
import com.mk.hms.enums.HmsEHotelPmsStatusEnum;
import com.mk.hms.enums.HmsEHotelStatusEnum;
import com.mk.hms.enums.HmsTHotelOperateLogCheckTypeEnum;
import com.mk.hms.enums.HmsVisibleEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.EHotel;
import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.HmsBAreaRule;
import com.mk.hms.model.HmsEHotelModel;
import com.mk.hms.model.HmsERoomtypeInfoModel;
import com.mk.hms.model.HmsGroupHotelModel;
import com.mk.hms.model.HmsGroupModel;
import com.mk.hms.model.HmsHOperateLogModel;
import com.mk.hms.model.HmsMRoleUser;
import com.mk.hms.model.HmsSaleUserModel;
import com.mk.hms.model.HmsTHotelModel;
import com.mk.hms.model.MUser;
import com.mk.hms.model.OutModel;
import com.mk.hms.service.verify.HotelVerifyService;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsDateUtils;
import com.mk.hms.utils.HmsFileUtils;
import com.mk.hms.utils.HmsStringUtils;
import com.mk.hms.utils.HttpClientUtils;
import com.mk.hms.utils.HttpTools;
import com.mk.hms.utils.LogUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.Page;

/**
 * 酒店讯息控制器
 * @author hdy
 *
 */
@Controller
@RequestMapping("/hotelmessage")
public class HotelMessageController{

	private static final Logger logger = LoggerFactory.getLogger(HotelMessageController.class);
	
	@Autowired
	private HmsHotelMessageService hmsHotelMessageService;
	
	@Autowired
	private HmsHOperateLogService hmsHOperateLogService;
	
	@Autowired
	private HmsERoomTypeInfoService hmsERoomTypeInfoService;
	
	@Autowired
	private HmsTRoomTypeService hmsTRoomTypeService; //房型
	
	@Autowired
	private HmsEBasePriceService hmsEBasePriceService; //基础房价信息
	
	@Autowired
	private HmsERoomTypeInfoService hmsRoomTypeInfoService; //房型信息

	@Autowired
	private RoomSettingService roomSettingService; //房间信息

	@Autowired
	private HmsERoomTypeFacilityService hmsERoomTypeFacilityService; //房型设备
	
	@Autowired
	private HmsUserService hmsUserService;
	
	@Autowired
	private PmsUserService pmsUserService;

	@Autowired
	private HmsHotelRuleService hmsHotelRuleService;//ruleservice，次日生效数据处理service
	
	@Autowired
	private HotelVerifyService hotelVerifyServiceImpl;

	@Autowired
	private HotelRemoteService hotelRemoteService;


	/**
	 * 获取酒店信息列表
	 * @return 数据列表
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/findHotelList")
	@ResponseBody
	public Map<String, Object> findHotelList(boolean isMySelfFlag, String queryContent, Page page) throws SessionTimeOutException {
		Map<String, Object> outMap = new HashMap<String, Object>();
		if (isMySelfFlag) {
			outMap.put("total", hmsHotelMessageService.findMyHotelListCount(queryContent));
			outMap.put("rows", hmsHotelMessageService.findMyHotelList(queryContent, page));
			return outMap;
		}
		outMap.put("total", hmsHotelMessageService.findHotelListCount(queryContent));
		outMap.put("rows", hmsHotelMessageService.findHotelList(queryContent, page));
		return outMap;
	}
	
	/**
	 * 校准酒店坐标
	 * @return 数据列表
	 */
	@RequestMapping("/addjustHotelLocation")
	@ResponseBody
	public Map<String, Object> addjustHotelLocation(String adjustList) {
		int finishCount = 0;
		
		ArrayList<MorphDynaBean> list = (ArrayList<MorphDynaBean>) JSONArray.toList(JSONArray.fromObject(adjustList));
		
		for (int i = 0;i < list.size();i++) {
			MorphDynaBean mdb = list.get(i);
			int hotelId = (Integer) mdb.get("hotelId");
			double lat = (Double) mdb.get("lat");
			double lng = (Double) mdb.get("lng");
			if (hmsHotelMessageService.updateHotelLocation(hotelId, lat, lng).isSuccess()) {
				finishCount++;
			}
		}
		
		Map<String, Object> outMap = new HashMap<String, Object>();
		outMap.put("count", finishCount);
		return outMap;
	}
	
	/**
	 * 获取未安装PMS酒店信息列表
	 * @return 数据列表
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/findHotelListByPmsStatus")
	@ResponseBody
	public Map<String, Object> findHotelListByPmsStatus(boolean isMySelfFlag, String queryContent, Page page) throws SessionTimeOutException {
		Map<String, Object> outMap = new HashMap<String, Object>();
		int match = HmsEHotelPmsStatusEnum.Match.getValue();
		if (isMySelfFlag) {
			outMap.put("total", hmsHotelMessageService.findMyHotelByPmsStatusCount(queryContent, match));
			outMap.put("rows", hmsHotelMessageService.findMyHotelListByPmsStatus(queryContent, page, match));
			return outMap;
		}
		outMap.put("total", hmsHotelMessageService.findHotelByPmsStatusCount(queryContent, match));
		outMap.put("rows", hmsHotelMessageService.findHotelListByPmsStatus(queryContent, page, match));
		return outMap;
	}
	
	/**
	 * 获取待审核酒店信息列表
	 * @return 数据列表
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/findHotelListByCheck")
	@ResponseBody
	public Map<String, Object> findHotelListByCheck(boolean isMySelfFlag, String queryContent, Page page) throws SessionTimeOutException {
		Map<String, Object> outMap = new HashMap<String, Object>();
		String states = HmsEHotelStatusEnum.Submit.getValue() + ContentUtils.CHAR_COMMA + HmsEHotelStatusEnum.Editing.getValue();
		if (isMySelfFlag) {
			outMap.put("total", hmsHotelMessageService.findMyHotelByCheckCount(queryContent, states));
			outMap.put("rows", hmsHotelMessageService.findMyHotelListByCheck(queryContent, page, states));
			return outMap;
		}
		outMap.put("total", hmsHotelMessageService.findHotelByCheckCount(queryContent, states));
		outMap.put("rows", hmsHotelMessageService.findHotelListByCheck(queryContent, page, states));
		return outMap;
	}
	
	/**
	 * 获取待完善酒店信息列表
	 * @return 数据列表
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/findHotelListByStatus")
	@ResponseBody
	public Map<String, Object> findHotelListByStatus(boolean isMySelfFlag, String queryContent, Page page) throws SessionTimeOutException {
		Map<String, Object> outMap = new HashMap<String, Object>();
		int unInitial = HmsEHotelStatusEnum.UNInitial.getValue();
		if (isMySelfFlag) {
			outMap.put("total", hmsHotelMessageService.findMyHotelByStateCount(queryContent, unInitial));
			outMap.put("rows", hmsHotelMessageService.findMyHotelListByState(queryContent, page, unInitial));
			return outMap;
		}
		outMap.put("total", hmsHotelMessageService.findHotelByStateCount(queryContent, unInitial));
		outMap.put("rows", hmsHotelMessageService.findHotelListByState(queryContent, page, unInitial));
		return outMap;
	}
	
	/**
	 * 获取待审核未通过酒店信息列表
	 * @return 数据列表
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/findHotelListByReason")
	@ResponseBody
	public Map<String, Object> findHotelListByReason(boolean isMySelfFlag, String queryContent, Page page) throws SessionTimeOutException {
		Map<String, Object> outMap = new HashMap<String, Object>();
		if (isMySelfFlag) {
			outMap.put("total", hmsHotelMessageService.findMyHotelByReasonCount(queryContent));
			outMap.put("rows", hmsHotelMessageService.findMyHotelListByReason(queryContent, page));
			return outMap;
		}
		outMap.put("total", hmsHotelMessageService.findHotelByReasonCount());
		outMap.put("rows", hmsHotelMessageService.findHotelListByReason(page));
		return outMap;
	}
	
	/**
	 * 获取待审核上线酒店信息列表
	 * @return 数据列表
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/findHotelListOnLine")
	@ResponseBody
	public Map<String, Object> findHotelListOnLine(boolean isMySelfFlag, String queryContent, Page page) throws SessionTimeOutException {
		String visible = HmsVisibleEnum.T.getValue();
		Map<String, Object> outMap = new HashMap<String, Object>();
		if (isMySelfFlag) {
			outMap.put("total", hmsHotelMessageService.findMyHotelOnLineCount(queryContent, visible));
			outMap.put("rows", hmsHotelMessageService.findMyHotelListOnLine(queryContent, page, visible));
			return outMap;
		}
		outMap.put("total", hmsHotelMessageService.findHotelOnLineCount(queryContent, visible));
		outMap.put("rows", hmsHotelMessageService.findHotelListOnLine(queryContent, page, visible));
		return outMap;
	}
	
	/**
	 * 获取待审核下线酒店信息列表
	 * @return 数据列表
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/findHotelListOffLine")
	@ResponseBody
	public Map<String, Object> findHotelListOffLine(boolean isMySelfFlag, String queryContent, Page page) throws SessionTimeOutException {
		String visible = HmsVisibleEnum.F.getValue();
		Map<String, Object> outMap = new HashMap<String, Object>();
		if (isMySelfFlag) {
			outMap.put("total", hmsHotelMessageService.findMyHotelOnLineCount(queryContent, visible));
			outMap.put("rows", hmsHotelMessageService.findMyHotelListOnLine(queryContent, page, visible));
			return outMap;
		}
		outMap.put("total", hmsHotelMessageService.findHotelOnLineCount(queryContent, visible));
		outMap.put("rows", hmsHotelMessageService.findHotelListOnLine(queryContent, page, visible));
		return outMap;
	}
	
	
	/**
	 * 审核酒店信息
	 * @return 酒店审核状态
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/checkEHotel")
	@ResponseBody
	public OutModel checkEHotel(long hotelId) throws SessionTimeOutException {
		OutModel out = new OutModel(false);
		// 获取酒店信息
		EHotelWithBLOBs eHotel = hmsHotelMessageService.getEHotelById(hotelId);
		if (null == eHotel) {
			out.setErrorMsg("酒店不存在");
			return out;
		}else if(HmsVisibleEnum.F.toString().equals( eHotel.getVisible()) ){
			out.setErrorMsg("酒店已下线，不能进行审核");
			return out;			
		}
		// 判断酒店是否安装PMS
		if (StringUtils.isBlank(eHotel.getPms()) || eHotel.getPmsStatus() <= HmsEHotelPmsStatusEnum.Init.getValue()) {
			out.setErrorMsg("请先安装PMS");
			return out;
		}
		if(eHotel.getRulecode() <= 0){
			out.setErrorMsg("请设置酒店规则");
			return out;
		}
		String sql = "SELECT id,name from t_roomtype  WHERE thotelid =" + hotelId+ " and (cost is null or cost <=0) limit 0,1";
		List<Map<String, Object>> resultList = HmsJdbcTemplate.getJdbcTemplate().queryForList(sql);
		// 判断该房型的门市价是否已经同步过来
		if (!resultList.isEmpty()) {
			out.setErrorMsg(resultList.get(0).get("name") + " 门市价未正确从PMS同步");
			return out;
		}
		try {
			String pmsCheckUserRoleCode = HmsFileUtils.getSysContentItem(ContentUtils.PMS_CHECK_USER_ROLE_CODE);
			MUser mUser = SessionUtils.getSessionPmsUser();
			HmsMRoleUser mRoleUser = pmsUserService.findPmsUserRoleByUserId(mUser.getId());
			if (mRoleUser.getRoleid() != Integer.parseInt(pmsCheckUserRoleCode)) {
				out.setErrorMsg("您没有审核权限");
				return out;
			}
		} catch (IOException e1) {
			HotelMessageController.logger.info("获取客服角色编码异常");
		}
		// 校验必填项
		HotelMessageControllerHelper.checkEhotelRequiredItems(eHotel, hmsRoomTypeInfoService, out);
		if (!out.isSuccess()) {
			return out;
		}
		out.setSuccess(false); //重置标示
		
		HotelMessageController.logger.info("酒店审核开始：copy e_hotel to t_hotel by hms checker");
		// 同步e表信息到t表
		HotelMessageControllerHelper.copyEHotel2THotel(eHotel, hmsHotelMessageService,hmsTRoomTypeService,
					hmsRoomTypeInfoService,hmsEBasePriceService,hmsERoomTypeFacilityService,roomSettingService, out);
		if (!out.isSuccess()) {
			return out;
		}
		out.setSuccess(false); //重置状态
		// 获取tHotelid
		//HmsTHotelModel tHotel = hmsHotelMessageService.findTHotelById(hotelId);
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("hotelid", hotelId + "");
		JSONObject obj = null;
		String adderss = null;
		// 首次提交，添加t酒店信息
		HotelMessageController.logger.info("调用ots缓存接口---开始：invoke ots cache begin");
		// 添加酒店信息到缓存接口调用
		try {
			if (eHotel.getState() == HmsEHotelStatusEnum.Submit.getValue()) {
				adderss = HmsFileUtils.getSysContentItem(ContentUtils.CHECK_HOTEL_INSERT_ADDRESS);
			} else if (eHotel.getState() == HmsEHotelStatusEnum.Editing.getValue()) {
				adderss = HmsFileUtils.getSysContentItem(ContentUtils.CHECK_HOTEL_UPDATE_ADDRESS);
			}
			obj = HttpClientUtils.post(adderss, paramsMap);
			//kafka消息
			try{
				hotelVerifyServiceImpl.syncData(String.valueOf(hotelId));
				logger.info("kafka成功失败");
			}catch(Exception e){
				logger.error("kafka处理失败",e);
			}
			if (null == obj) {
				HotelMessageController.logger.error("调用ots缓存接口异常：" + adderss);
				out.setErrorMsg("调用ots缓存接口:同步房间缓存失败");
				return out;
			}
			if (!obj.getBoolean("success")) {
				HotelMessageController.logger.error("调用ots缓存接口错误：" + adderss
						+ obj.getString("errmsg") + "，错误编码：" + obj.getString("errcode"));
				out.setErrorMsg("调用ots缓存接口错误：" + obj.getString("errmsg") + "，错误编码：" + obj.getString("errcode"));
				return out;
			}
			HotelMessageController.logger.info("调用ots缓存接口：" + adderss);
			//刷新缓存和价格
			hotelRemoteService.hotelInit(eHotel.getCitycode().toString() ,hotelId+"");
			hotelRemoteService.updatemikeprices(hotelId+"");
			// 新加酒店自动上线
			/*if (eHotel.getState() == HmsEHotelStatusEnum.Submit.getValue()) {
				adderss = HmsFileUtils.getSysContentItem(ContentUtils.ONLINE_HOTEL_ON_ADDRESS);
				HotelMessageController.logger.info("调用ots酒店上线接口：" + adderss);
				// 上线酒店
				obj = HttpClientUtils.post(adderss, paramsMap);
				if (null == obj) {
					HotelMessageController.logger.error("调用ots酒店上线接口异常：" + adderss);
					out.setErrorMsg("调用ots酒店上线接口异常");
					return out;
				}
				if (!obj.getBoolean("success")) {
					HotelMessageController.logger.error("调用ots酒店上线接口错误：" + adderss
							+ obj.getString("errmsg") + "，错误编码：" + obj.getString("errcode"));
					out.setErrorMsg(obj.getString("errmsg") + "，错误编码：" + obj.getString("errcode"));
					return out;
				}
			}*/
		} catch(IOException e) {
			HotelMessageController.logger.error("调用ots缓存接口异常：" + adderss  + "，" + e.getMessage(), e);
		}
		HotelMessageController.logger.info("调用ots缓存接口---结束：invoke ots cache end");
		HotelMessageController.logger.info("修改酒店审核状态---开始：update check status begin");
		// 修改酒店状态
		hmsHotelMessageService.updateEHotelState(HmsEHotelStatusEnum.ManagerEditing.getValue(), eHotel.getId());
		HotelMessageController.logger.info("修改酒店审核状态---结束：update check status end");
		// 添加操作日志信息
		HmsHOperateLogModel log = new HmsHOperateLogModel("e_hotel", "checkEHotel", "审核酒店");
		HmsHOperateLogModel logModel = hmsHOperateLogService.addLog(log);
		if (null == logModel) {
			out.setErrorMsg("登录用户过期");
			return out;
		}
		// 操作成功
		out.setSuccess(true);
		return out;
	}
	/**
	 * 安装PMS
	 * @return 安装状态
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/installPms")
	@ResponseBody
	public OutModel installPms(long hotelId, String pmsCode, String pmsType) throws SessionTimeOutException {
		logger.info(String.format("酒店开始进行安装pms,[hotelId=%1$s,pmsCode=%2$s,pmsType=%3$s]",hotelId,pmsCode,pmsType));
		// 获取当前酒店讯息
		OutModel out = new OutModel(false);
		// 判断是否为空
		if (hotelId<=0 || pmsCode.length()<=0 || pmsType.length()<=0) {
			logger.error("参数错误");
			out.setErrorMsg("参数未正确输入后台");
			return out;
		}
		HmsEHotelModel eHotel = hmsHotelMessageService.findMyEHotelById(hotelId);
		// 操作session
		SessionUtils.setSessionPmsHotel(hmsHotelMessageService.getEHotelById(hotelId));
		// 所查酒店不存在
		if (null == eHotel) {
			logger.error(String.format("未查询到酒店信息[id=%1$s]",hotelId));
			out.setErrorMsg("所查酒店为空或此酒店不是您的酒店");
			return out;
		}
		// 判断是否已安装PMS
		if (eHotel.getState() > HmsEHotelStatusEnum.Init.getValue()) {
			logger.warn(String.format("PMS已安装[id=%1$s]",hotelId));
			out.setErrorMsg("PMS已安装");
			return out;
		}
		//安装pms
		logger.info(String.format("pms安装类型[id=%1$s,pmsType=%2$s]",hotelId,pmsType));
		if("OLD_PMS".equals(pmsType)){
			// 获取该pms编码酒店列表
			String pmsMsg = checkPmsCodeState(hotelId,pmsCode);
			if(!"".equals(pmsMsg)){
				out.setErrorMsg(pmsMsg);
			}else if(hmsHotelMessageService.installPms(pmsCode, new Date(),HmsVisibleEnum.F.getValue(),hotelId) < 1 ){
				logger.info(String.format("保存PMS编码时失败[id=%1$s,pmsType=%2$s]",hotelId,pmsType));
				out.setErrorMsg("保存PMS编码时失败");
			}		
		}else if("NEW_PMS".equals(pmsType)){			
			out = installNewPms(hotelId, pmsCode, pmsType);
		}
		// 添加操作日志信息
		HmsHOperateLogModel log = new HmsHOperateLogModel("e_hotel", "installPms", "安装PMS");
		HmsHOperateLogModel logModel = hmsHOperateLogService.addLog(log);
		if (null == logModel) {
			out.setErrorMsg("登录用户过期");
			return out;
		}
		out.setSuccess(out.getErrorMsg()==null || out.getErrorMsg().isEmpty());
		return out;
	}
	
	/**
	 * 检测PMS安装是否完成
	 * @return 检测状态
	 */
	@RequestMapping("/checkInstallPms")
	@ResponseBody
	public OutModel checkInstallPms(long hotelId) {
		OutModel out = new OutModel(false);
		HmsEHotelModel eHotel = hmsHotelMessageService.findEHotelById(hotelId);
		if (null == eHotel) {
			out.setErrorMsg("不存在该酒店数据");
			return out;
		}
		//PMS安装失败
		if (eHotel.getState() <= HmsEHotelStatusEnum.Init.getValue()
				|| eHotel.getPmsStatus() <= HmsEHotelPmsStatusEnum.Init.getValue()) {
			out.setErrorMsg("PMS安装失败");
			return out;
		}
		out.setSuccess(true);
		return out;
		
	}
	/**
	 * 提交酒店信息
	 * @return 提交状态
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/submitHotlInfo")
	@ResponseBody
	public OutModel submitHotlInfo() throws SessionTimeOutException {
		// 初始化返回状态
		OutModel out = new OutModel(false);
		out.setErrorMsg("保存数据失败");
		// 获取用户信息
		EHotel thisHotel = SessionUtils.getThisHotel();
		// 得到当前酒店信息
		EHotelWithBLOBs hotelInfo = hmsHotelMessageService.getEHotelById(thisHotel.getId());
		// 判断是否安装PMS
		if (hotelInfo.getPmsStatus() == HmsEHotelPmsStatusEnum.Init.getValue()) {
			out.setErrorMsg("请先安装PMS");
			return out;
		}
		// pms未初始化酒店房价、房型信息
		if (hotelInfo.getPmsStatus() == HmsEHotelPmsStatusEnum.Match.getValue()) {
			out.setErrorMsg("PMS未初始化酒店房价、房型信息");
			return out;
		}
		// 校验必填项
		HotelMessageControllerHelper.checkEhotelRequiredItems(hotelInfo, hmsRoomTypeInfoService, out);
		if (!out.isSuccess()) {
			return out;
		}
		// 未首次审核
		if (hotelInfo.getState() == HmsEHotelStatusEnum.UNInitial.getValue()) {
			out.setSuccess(false); //重置标示
			//查询您当前酒店是否对应了PMS房型、房价信息。对接完毕，改变state状态为4，然后返回；
			List<HmsERoomtypeInfoModel> eRoomtypeInfoModels = hmsERoomTypeInfoService.findsByHotelId(thisHotel.getId());
			if (eRoomtypeInfoModels.isEmpty()) {
				out.setErrorMsg("请完善酒店房型设置");
				return out;
			}
			// 对接完毕，改变state状态为3，然后返回
			hmsHotelMessageService.updateEHotelState(HmsEHotelStatusEnum.Submit.getValue(), hotelInfo.getId());
			// 添加操作日志信息
			HmsHOperateLogModel log = new HmsHOperateLogModel("e_hotel", "submitHotlInfo", "提交酒店信息，未首次审核");
			HmsHOperateLogModel logModel = hmsHOperateLogService.addLog(log);
			if (null == logModel) {
				out.setErrorMsg("登录用户过期");
				return out;
			}
			out.setSuccess(true);
			return out;
		} 
		// 第二次之后审核，直接修改状态为5
		if (hotelInfo.getState() == HmsEHotelStatusEnum.ManagerEditing.getValue()) {
			out.setSuccess(false); //重置标示
			hmsHotelMessageService.updateEHotelState(HmsEHotelStatusEnum.Editing.getValue(), hotelInfo.getId());
			// 添加操作日志信息
			HmsHOperateLogModel log = new HmsHOperateLogModel("e_hotel", "submitHotlInfo", "提交酒店信息，已通过首次审核");
			HmsHOperateLogModel logModel = hmsHOperateLogService.addLog(log);
			if (null == logModel) {
				out.setErrorMsg("登录用户过期");
				return out;
			}
			out.setSuccess(true);
			return out;
		}
		if (hotelInfo.getState() == HmsEHotelStatusEnum.Init.getValue()) {
			out.setErrorMsg("状态未处理正确，请联系PMS端");
		}else if (hotelInfo.getState() == HmsEHotelStatusEnum.Editing.getValue()) {
			out.setErrorMsg("酒店正在审核中");
		}
		out.setSuccess(false);
		return out;
	}
	
	/**
	 * 
	 * @return
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/saveHotelInfoPic")
	@ResponseBody
	public OutModel saveHotelInfoPic(String pics) throws SessionTimeOutException {
		OutModel out = new OutModel(false);
		EHotel thisHotel = SessionUtils.getThisHotel();
		if (null == thisHotel) {
			out.setErrorMsg("登录用户过期");
			return out;
		}
		int count = hmsHotelMessageService.saveHotelInfoPic(pics, thisHotel.getId());
		
		Map<String, String> logs = new HashMap<String, String>();
		logs.put("成功数量", Long.toString(count));
		logs.put("参数pics", pics);
		LogUtils.logStepPlusMap(logger, "保存完照片", logs);
		
		if (count != 1) {
			out.setErrorMsg("修改酒店图片信息异常");
			return out;
		}
		out.setSuccess(true);
		LogUtils.logStep(logger, "结束请求");
		return out;
	}
	
	/**
	 * 获取当前酒店分组信息
	 * @return 分组信息
	 */
	@RequestMapping("/getThisHotelGroup")
	@ResponseBody
	public HmsGroupModel getThisHotelGroup(long hotelId) {
		HmsGroupHotelModel grougHotel = hmsUserService.findGroupByHotelId(hotelId);
		return hmsUserService.findGroup(grougHotel.getGroupid());
	}
	
	/**
	 * 获取数据库中存放thisHotelModel
	 * @return 酒店对象
	 */
	/*@RequestMapping(params = "getThisHotelModel")
	@ResponseBody
	public EHotel getThisHotelModel() {
		EHotel eHotel = SessionUtils.getThisHotel();
		return hmsHotelMessageService.findEHotelById(eHotel.getId());
	}*/
	
	/**
	 * 获取酒店实时状态
	 * @return 酒店对象
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/getThisHotelRealStatus")
	@ResponseBody
	public OutModel getThisHotelRealStatus() throws SessionTimeOutException {
		//Map<String, Object> params = RequestUtils.getParameters();
		EHotel eHotel = SessionUtils.getThisHotel();
		long hotelId = eHotel.getId();
		Map<String, Object> statusMap  = hmsHotelMessageService.findTHotelRealStatus(hotelId);
		OutModel out = new OutModel(false);
		out.setAttribute(statusMap);
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * 下线酒店
	 * @return 下线状态
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/offlineHotel")
	@ResponseBody
	public OutModel offlineHotel(long hotelid) throws SessionTimeOutException {
		HmsTHotelModel tHotel = hmsHotelMessageService.findTHotelById(hotelid);
		OutModel out = new OutModel(false);
		if (null == tHotel) {
			HotelMessageController.logger.error("t表酒店信息不存在");
			out.setErrorMsg("此酒店信息不存在，请查看是否已上线");
			return out;
		}
		String address = "";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("hotelid", hotelid + "");
		HotelMessageController.logger.info("调用酒店下线接口---开始");
		try {
			address = HmsFileUtils.getSysContentItem(ContentUtils.ONLINE_HOTEL_OFF_ADDRESS);
			JSONObject obj = HttpClientUtils.post(address, paramsMap);
			if (null == obj) {
				HotelMessageController.logger.error("调用酒店下线接口异常");
				out.setErrorMsg("调用酒店下线接口异常");
				return out;
			}
			if (!obj.getBoolean("success")) {
				HotelMessageController.logger.error("调用酒店下线接口错误：" + obj.getString("errmsg") + "，错误代码："
						+ obj.getString("errcode"));
				out.setErrorMsg("调用酒店下线接口错误：" + obj.getString("errmsg") + "，错误代码："
						+ obj.getString("errcode"));
				return out;
			}
		} catch (Exception e) {
			HotelMessageController.logger.info("调用酒店下线接口异常：" + e.getMessage(), e);
		}
		HotelMessageController.logger.info("调用酒店下线接口---结束");
		//调用下线操作接口成功后更新下线状态
		hmsHotelMessageService.resetTHotelLine(HmsVisibleEnum.F.getValue(), HmsVisibleEnum.F.getValue(), hotelid);
		hmsHotelMessageService.resetEHotelLine(HmsVisibleEnum.F.getValue(), hotelid);
		// 下线日志
		hmsHOperateLogService.addTHotelOperateLogForOnline(tHotel, HmsTHotelOperateLogCheckTypeEnum.Offline.getValue(),
				HmsTHotelOperateLogCheckTypeEnum.Offline.getText());
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * 上线酒店
	 * @return 上线酒店
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/onLineHotel")
	@ResponseBody
	public OutModel onLineHotel(long hotelid) throws SessionTimeOutException {
		HmsTHotelModel tHotel = hmsHotelMessageService.findTHotelById(hotelid);
		OutModel out = new OutModel(false);
		if (null == tHotel) {
			HotelMessageController.logger.error("t表酒店信息不存在");
			out.setErrorMsg("此酒店信息不存在，请查看是否已审核");
			return out;
		}
		String address = "";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("hotelid", hotelid + "");
		HotelMessageController.logger.info("调用酒店上线接口---开始");
		try {
			//调用上线操作接口成功后更新上线状态
			hmsHotelMessageService.resetTHotelLine(HmsVisibleEnum.T.getValue(), HmsVisibleEnum.T.getValue(), hotelid);
			hmsHotelMessageService.resetEHotelLine(HmsVisibleEnum.T.getValue(), hotelid);
			address = HmsFileUtils.getSysContentItem(ContentUtils.ONLINE_HOTEL_ON_ADDRESS);
			JSONObject obj = HttpClientUtils.post(address, paramsMap);
			if (null == obj) {
				HotelMessageController.logger.error("调用酒店上线接口异常");
				out.setErrorMsg("调用酒店上线接口异常");
				//回滚上线操作后更新上线状态
				hmsHotelMessageService.resetTHotelLine(HmsVisibleEnum.F.getValue(), HmsVisibleEnum.F.getValue(), hotelid);
				hmsHotelMessageService.resetEHotelLine(HmsVisibleEnum.F.getValue(), hotelid);
				return out;
			}
			if (!obj.getBoolean("success")) {
				HotelMessageController.logger.error("调用酒店上线接口错误：" + obj.getString("errmsg") + "，错误代码："
						+ obj.getString("errcode"));
				out.setErrorMsg("调用酒店上线接口错误：" + obj.getString("errmsg") + "，错误代码："
						+ obj.getString("errcode"));
				//回滚上线操作后更新上线状态
				hmsHotelMessageService.resetTHotelLine(HmsVisibleEnum.F.getValue(), HmsVisibleEnum.F.getValue(), hotelid);
				hmsHotelMessageService.resetEHotelLine(HmsVisibleEnum.F.getValue(), hotelid);
				return out;
			}
		} catch (Exception e) {
			//回滚上线操作后更新上线状态
			hmsHotelMessageService.resetTHotelLine(HmsVisibleEnum.F.getValue(), HmsVisibleEnum.F.getValue(), hotelid);
			hmsHotelMessageService.resetEHotelLine(HmsVisibleEnum.F.getValue(), hotelid);
			HotelMessageController.logger.info("调用酒店下线接口异常：" + e.getMessage(), e);
		}
		HotelMessageController.logger.info("调用酒店上线接口---结束");
		// 上线日志
		hmsHOperateLogService.addTHotelOperateLogForOnline(tHotel, HmsTHotelOperateLogCheckTypeEnum.Online.getValue(),
				HmsTHotelOperateLogCheckTypeEnum.Online.getText());
		out.setSuccess(true);
		return out;
	}

	/**
	 * 获取销售人员信息列表
	 * @return
	 */
	@RequestMapping("/findSaleUserList")
	@ResponseBody
	public List<HmsSaleUserModel> findSaleUserList(String hotelIds){
		if(null==hotelIds || HmsStringUtils.isBlank(hotelIds.toString())){			
			return null;
		}else{
			return hmsHotelMessageService.findSaleUserList(hotelIds.toString());
		}
	}
	
	/**
	 * 手动更新房态信息
	 * @return 
	 */
	@RequestMapping("/refreshRoomOnlineState")
	@ResponseBody
	public OutModel refreshRoomOnlineState(long hotelId) {
		OutModel out = new OutModel(false);
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("hotelId", hotelId + "");
		paramsMap.put("token", "eb39e6bf-a2b8-44b2-90ed-2471bd7fd4a1");
		JSONObject obj = null;
		String adderss = null;
		HotelMessageController.logger.info("调用ots反查酒店房态接口---开始：invoke ots cache begin");
		try {
			adderss = HmsFileUtils.getSysContentItem(ContentUtils.REFRESH_ROOM_ONLINESTATE_ADDRESS);
			obj = HttpClientUtils.post(adderss, paramsMap);
			if (null == obj) {
				HotelMessageController.logger.error("调用ots反查酒店房态接口异常：" + adderss);
				out.setErrorMsg("调用ots反查酒店房态接口失败");
				return out;
			}
			if (!obj.getBoolean("success")) {
				HotelMessageController.logger.error("调用ots反查酒店房态接口错误：" + adderss
						+ obj.getString("errmsg") + "，错误编码：" + obj.getString("errcode"));
				out.setErrorMsg(obj.getString("errmsg") + "，错误编码：" + obj.getString("errcode"));
				return out;
			}
			HotelMessageController.logger.info("调用ots反查酒店房态接口：" + adderss);
		} catch(IOException e) {
			HotelMessageController.logger.error("调用ots反查酒店房态接口异常：" + adderss  + "，" + e.getMessage(), e);
		}
		HotelMessageController.logger.info("调用ots反查酒店房态接口---结束：invoke ots cache end");
		// 操作成功
		out.setSuccess(true);
		return out;
	}
	/**
	 * 获取地域规则字典列表
	 * @return
	 */
	@RequestMapping("/findAreaRule")
	@ResponseBody
	public List<HmsBAreaRule> findAreaRule(){
		return hmsHotelMessageService.findAreaRule();
	}
	/**
	 * 获取酒店规则
	 * @param hotelId
	 * @return ruleCode
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/getHotelRule")
	@ResponseBody
	public int getHotelRule() throws SessionTimeOutException{
		long hotelId = SessionUtils.getThisHotelId();
		return hmsHotelMessageService.getHotelRule(hotelId);
	}
	/**
	 * 设置酒店规则 1001 a规则 1002 b规则
	 * @return 
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/saveHotelRule")
	@ResponseBody
	public OutModel saveHotelRule(int ruleCode) throws SessionTimeOutException {
		long hotelId = SessionUtils.getThisHotelId();
		OutModel out = new OutModel(false);
		if(ruleCode<=0){
			out.setErrorMsg("酒店规则（ruleCode）没有传入");
		}else if(hotelId<=0){
			out.setErrorMsg("登陆超时");
		}else{
			if(hmsHotelMessageService.saveHotelRule(hotelId, ruleCode)>0){
				if(1002 == ruleCode){//a规则变为b规则
					String effectdate = HmsDateUtils.formatDate(HmsDateUtils.addDate(1))+" 00:00:01";
//					hmsHotelMessageService.insertHotelRule(hotelId, 1, "1002", effectdate, new Date());
//					hmsHotelMessageService.insertHotelRule(hotelId, 2, "T", effectdate, new Date());
					hmsHotelRuleService.insertHotelRule(hotelId, 1002, "T", effectdate);
				}else{
					hmsHotelMessageService.updateHotelRuleToA(hotelId);
				}
				out.setSuccess(true);
				// 添加操作日志信息
				HmsHOperateLogModel log = new HmsHOperateLogModel("e_hotel", "saveHotelRule", "修改酒店规则");
				hmsHOperateLogService.addLog(log);
			}else{
				out.setErrorMsg("保存时异常");				
			}
		}
		return out;
	}
	/**
	 * 设置酒店结算方式
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/setHotelThreshold")
	@ResponseBody
	public OutModel setHotelThreshold(String isThreshold) throws SessionTimeOutException {
		OutModel out = new OutModel(false);
		long hotelId = SessionUtils.getThisHotelId();
		if(null == isThreshold){
			out.setErrorMsg("阈值（isThreshold）没有传入");
		}else if(hotelId<=0){
			out.setErrorMsg("登陆超时");
		}else{
			Date first = HmsDateUtils.getNextMonthFirstDay2();
			if(hmsHotelMessageService.setHotelThreshold(isThreshold,first,hotelId)>0){
				out.setSuccess(true);
				// 添加操作日志信息
				HmsHOperateLogModel log = new HmsHOperateLogModel("t_hotel", "setHotelThreshold", "设置酒店结算方式");
				hmsHOperateLogService.addLog(log);
			}else{
				out.setErrorMsg("保存时异常");				
			}
		}
		return out;
	}
	/**
	 * 判断酒店是否可选择阈值结算
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/isHold")
	@ResponseBody
	public boolean isHold() throws SessionTimeOutException{
		long hotelId = SessionUtils.getThisHotelId();
		boolean b = false;
		if(hotelId > 0){
			HmsTHotelModel obj  = hmsHotelMessageService.findTHotelById(hotelId);
			String value = hmsHotelMessageService.getCityCode(obj.getDisId());
			if(null != value){
				b = true;
			}
		}
		return b;
	}
	/**
	 * 得到默认到阈值及判断酒店是否可选择阈值结算true
	 *  阈值标示 T OR F
	 * 判断酒店是否可选择阈值结算 boolean value true or false
	 * @return map
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/getIshold")
	@ResponseBody
	public String getIshold() throws SessionTimeOutException{
		String ishold = "F";
		ishold = hmsHotelMessageService.getHotelThreshold();
		return ishold;
	}
	/**
	 * 得到默认到阈值及
	 * @return
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/getIsholdAndStatus")
	@ResponseBody
	 public Map<String,Object> getIsholdAndStatus() throws SessionTimeOutException{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("isThreshold", getIshold());
		map.put("ishold", isHold());
		return map;
	 }
	/**
	 * 检查PMS编码是否已经存在或者被占用
	 * @param hotelId
	 * @param pmsCode
	 * @return
	 */
	private String checkPmsCodeState(long hotelId,String pmsCode){
		// 获取该pms编码酒店列表
		List<HmsEHotelModel> pmsList = hmsHotelMessageService.findMyEHotelByPms(pmsCode);
		if (pmsList.size() > 0) {
			if (pmsList.size() > 1) {
				return "错误：" + pmsList.size() + "家酒店共用该PMS编码(" + pmsCode + ")";
			}
			HmsEHotelModel duplicatedH = pmsList.get(0);
			if (duplicatedH.getId() != hotelId) {
				return "该PMS编码已被酒店：" + duplicatedH.getHotelName() + "占用";
			}
		}
		return "";
	}
	/**
	 * 使用pms系统的酒店id或编码进行安装
	 * @param hotelId
	 * @param pmsCode
	 * @param pmsType
	 * @return OutModel
	 */
	private OutModel installNewPms(long hotelId, String pmsCode, String pmsType){
		OutModel out = new OutModel(false);
		JsonObject paramsMap = new JsonObject();
		paramsMap.addProperty("hotelid",pmsCode);
		paramsMap.addProperty("pmstypeid","2");
		paramsMap.addProperty("function","online");
		paramsMap.addProperty("timestamp",DateUtil.formatDate(new Date(), "yyyyMMddhhmmss"));
		
		logger.info(String.format("安装新PMS开始,参数为%1$s", paramsMap.toString()));

		//获取服务器地址
		String address = "";//pms安装接口地址
		try{
			address = HmsFileUtils.getSysContentItem(ContentUtils.NEW_PMS_INSTALL_ADDRESS);
		}catch(IOException ioe){
			logger.error(String.format("安装服务地址未获取到[id=%1$s]",hotelId),ioe);
			out.setErrorMsg("安装服务地址获取失败");
		}
		//接口调用
		JSONObject obj = null;
		try {
			obj = JSONObject.fromObject(HttpTools.dopostjson(address, paramsMap.toString()));
		} catch(Exception e) {
			logger.error(String.format("接口调用异常:%1$s,[id=%2$s]",address,hotelId), e);
			out.setErrorMsg("调用安装新pms接口异常");
		}
		if (null == obj) {
			//服务器接口调用失败
			logger.error(String.format("调用失败，服务地址:%1$s,[id=%2$s]",address,hotelId));
			out.setErrorMsg("调用接口失败");
		}else if(!obj.getBoolean("success")) {
			//安装失败
			String errorMsg = obj.getString("errmsg");
			String errorCode= obj.getString("errcode");
			logger.error(String.format("安装失败,错误信息：%1$s,错误编码：%2$s,服务地址:%3$s,[id=%4$s]",errorMsg,errorCode,address,hotelId));
			out.setErrorMsg(errorMsg + "，错误编码：" + errorCode);
		}else{
			// 保存pmsCode
			try{
				String rtnPmsCode = obj.getString("hotelid");
				String rtnpmsHotelName = obj.getString("hotelname");
				// 获取该pms编码酒店列表
				String pmsMsg = checkPmsCodeState(hotelId, rtnPmsCode);
				if ("".equals(pmsMsg)) {
					//保存安装数据
					hmsHotelMessageService.installPms2(rtnPmsCode, rtnpmsHotelName,
							new Date(), HmsVisibleEnum.T.getValue(), hotelId,
							HmsEHotelPmsStatusEnum.Match.getValue());
				} else {
					out.setErrorMsg(pmsMsg);
				}
				logger.info(String.format("PMS安装结束[id=%1$s]",hotelId));
			}catch(Exception e){
				logger.error(String.format("安装数据保存失败[id=%1$s]",hotelId),e);
				out.setErrorMsg("数据保存失败");
			}
		}
		return out;
	}
}
