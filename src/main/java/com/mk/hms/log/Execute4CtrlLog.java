package com.mk.hms.log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.EHotelMapper;
import com.mk.hms.model.AopLogWithBLOBs;
import com.mk.hms.model.EHotelCriteria;
import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.OutModel;
import com.mk.hms.task.RequestLogTask;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.DESUtils;
import com.mk.hms.utils.HmsDateUtils;
import com.mk.hms.utils.HmsThreadPool;
import com.mk.hms.utils.RequestUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.utils.SpringContextUtil;
import com.mk.hms.view.AddPrice;
import com.mk.hms.view.HmsPmsToken;
import com.mk.hms.view.HmsPmsUser;
import com.mk.hms.view.LogData;
import com.mk.hms.view.SessionUser;

/**
 * 执行业务日志操作
 * @author hdy
 *
 */
@Component
public class Execute4CtrlLog {
	
	private static EHotelMapper eHotelMapper;
	
	@Autowired
	public void setEHotelMapper(EHotelMapper eHotelMapper) {
		Execute4CtrlLog.eHotelMapper = eHotelMapper;
	}
	
	/**
	 * 添加眯客房价log
	 * @param pjd 
	 * @param result
	 * @throws SessionTimeOutException 
	 */
	public static void addLog4SaveBasePrice(ProceedingJoinPoint pjd, Object result) throws SessionTimeOutException {
		SessionUser currUser = SpringContextUtil.getCurrentLoginUser();
		if (null == currUser) {
			return;
		}
		Object[] args = pjd.getArgs();
		BigDecimal value = null;
		Long roomTypeId = null;
		String type = null;
		for (Object o : args) {
			if (BigDecimal.class.getCanonicalName().equals(o.getClass().getCanonicalName())) {
				value = (BigDecimal) o;
			} else if (Long.class.getCanonicalName().equals(o.getClass().getCanonicalName())) {
				roomTypeId = (Long) o;
			} else if (String.class.getCanonicalName().equals(o.getClass().getCanonicalName())) {
				type = (String) o;
			}
		}
		EHotelWithBLOBs thisHotel = SessionUtils.getThisHotel();
		long hotelId = thisHotel.getId();
		
		String token = SessionUtils.getToken4PmsUser();
		if (StringUtils.isNoneBlank(token)) {
			currUser.setSys(ContentUtils.PMS);
		}
		
		AopLogWithBLOBs aoplog = initLog(thisHotel, currUser);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("value", value);
		params.put("roomTypeId", roomTypeId);
		params.put("type", type);
		aoplog.setParams(JSONObject.fromObject(params).toString());
		aoplog.setResult(JSONObject.fromObject(result).toString());
		OutModel out = (OutModel) result;
		if (!out.isSuccess()) {
			aoplog.setErrormsg(out.getErrorMsg());
		}
		List<LogData> data = new ArrayList<LogData>();
		StringBuffer sb = new StringBuffer();
		String time = HmsDateUtils.getFormatDateString(new Date(), HmsDateUtils.FORMAT_DATETIME);
		if (StringUtils.isNoneBlank(token)) {
			HmsPmsToken hpt = DESUtils.decryptTokenByDES(token);
			sb.append("pms用户为:【").append(hpt.getLoginname()).append("】，通过token登录hms系统。操作");
		}
		sb.append("当前酒店id为:【").append(hotelId).append("】，").append("酒店名称为:【").append(thisHotel.getHotelName());
		sb.append("】。将房型id为:【").append(roomTypeId).append("】的房型，修改类型为:【").append(type).append("】的【眯客价】");
		if("price".equals(type)){
			sb.append("调整为").append(value.doubleValue()).append("元");
		}else if("subprice".equals(type)){
			sb.append("下降为").append(value.doubleValue()).append("元");
		}else if("subper".equals(type)){
			sb.append("下降").append(value.doubleValue());
		}
		data.add(new LogData(null, null, time, sb.toString()));
		aoplog.setData(JSONArray.fromObject(data).toString());
		execute(aoplog);
	}
	
	/**
	 * 添加策略价log
	 * @param pjd ProceedingJoinPoint
	 * @param result 结
	 * @throws SessionTimeOutException
	 */
	public static void addLog4AddPrice(ProceedingJoinPoint pjd, Object result) throws SessionTimeOutException {
		SessionUser currUser = SpringContextUtil.getCurrentLoginUser();
		if (null == currUser) {
			return;
		}
		Object[] args = pjd.getArgs();
		AddPrice addPrice = null;
		for (Object o : args) {
			if (AddPrice.class.getCanonicalName().equals(o.getClass().getCanonicalName())) {
				addPrice = (AddPrice) o;
				break;
			}
		}
		EHotelWithBLOBs thisHotel = SessionUtils.getThisHotel();
		long hotelId = thisHotel.getId();
		
		String token = SessionUtils.getToken4PmsUser();
		if (StringUtils.isNoneBlank(token)) {
			currUser.setSys(ContentUtils.PMS);
		}
		AopLogWithBLOBs aoplog = initLog(thisHotel, currUser);
		
		aoplog.setParams(JSONObject.fromObject(addPrice).toString());
		aoplog.setResult(JSONObject.fromObject(result).toString());
		OutModel out = (OutModel) result;
		if (!out.isSuccess()) {
			aoplog.setErrormsg(out.getErrorMsg());
		}
		List<LogData> data = new ArrayList<LogData>();
		StringBuffer sb = new StringBuffer();
		String time = HmsDateUtils.getFormatDateString(new Date(), HmsDateUtils.FORMAT_DATETIME);
		if (StringUtils.isNoneBlank(token)) {
			HmsPmsToken hpt = DESUtils.decryptTokenByDES(token);
			sb.append("pms用户为:【").append(hpt.getLoginname()).append("】，通过token登录hms系统。操作");
		}
		sb.append("当前酒店id为:【").append(hotelId).append("】，").append("酒店名称为:【").append(thisHotel.getHotelName());
		sb.append("】。将房型id为:【").append(addPrice.getRoomTypeId()).append("】的房型【策略价】从【")
		  .append(addPrice.getBeginTime()).append("】至【").append(addPrice.getEndTime())
		  .append("】修改为:【").append(addPrice.getValue());
		data.add(new LogData(null, null, time, sb.toString()));
		aoplog.setData(JSONArray.fromObject(data).toString());
		execute(aoplog);
	}
	
	/**
	 * 添加获取token日志
	 * @param pjd ProceedingJoinPoint
	 * @param result Object 结果集
	 */
	public static void addLog4GetToken4Pms (ProceedingJoinPoint pjd, Object result) {
		Object[] args = pjd.getArgs();
		HmsPmsUser hpu = null;
		String json = null;
		for (Object o : args) {
			if (null != o && String.class.getCanonicalName().equals(o.getClass().getCanonicalName())) {
				json = (String) o;
			}
		}
		if (null == json) {
			return;
		}
		hpu = (HmsPmsUser) JSONObject.toBean(JSONObject.fromObject(json), HmsPmsUser.class);
		String pmsLoginName = hpu.getUserid();
		String pms = hpu.getHotelid();
		EHotelCriteria eh = new EHotelCriteria();
		eh.createCriteria().andPmsEqualTo(pms);
		List<EHotelWithBLOBs> list = eHotelMapper.selectByExampleWithBLOBs(eh);
		EHotelWithBLOBs thisHotel = list.size() > 0 ? list.get(0) : new EHotelWithBLOBs();
		SessionUser su = new SessionUser();
		su.setLoginname(pmsLoginName);
		su.setSys(ContentUtils.PMS);
		su.setId(null);
		su.setName(pmsLoginName);
		
		AopLogWithBLOBs aoplog = initLog(thisHotel, su);
		aoplog.setParams(JSONObject.fromObject(hpu).toString());
		aoplog.setResult(JSONObject.fromObject(result).toString());
		OutModel out = (OutModel) result;
		if (!out.isSuccess()) {
			aoplog.setErrormsg(out.getErrorMsg());
		}
		String time = HmsDateUtils.getFormatDateString(new Date(), HmsDateUtils.FORMAT_DATETIME);
		List<LogData> data = new ArrayList<LogData>();
		StringBuffer sb = new StringBuffer();
		sb.append("pms用户为:【").append(pmsLoginName).append("】，").append("在【").append(time)
		  .append("】获取hms登录token。")
		  .append("登录酒店pms编码为:【").append(pms).append("】;").append("酒店id为:【")
		  .append(thisHotel.getId()).append("】，").append("酒店名称为:【").append(thisHotel.getHotelName())
		  .append("】。");
		data.add(new LogData(null, null, time, sb.toString()));
		aoplog.setData(JSONArray.fromObject(data).toString());
		execute(aoplog);
	}
	
	/**
	 * 添加获取token日志
	 * @param pjd ProceedingJoinPoint
	 * @param result Object 结果集
	 */
	public static void addLog4LoginHms4Pms (ProceedingJoinPoint pjd, Object result) {
		Object[] args = pjd.getArgs();
		String token = null;
		for (Object o : args) {
			if (String.class.getCanonicalName().equals(o.getClass().getCanonicalName())) {
				token = (String) o;
			}
		}
		if (null == token) {
			return;
		}
		HmsPmsToken hpt = DESUtils.decryptTokenByDES(token);
		if (null == hpt) {
			return;
		}
		String pmsLoginName = hpt.getLoginname();
		String pms = hpt.getPms();
		EHotelCriteria eh = new EHotelCriteria();
		eh.createCriteria().andPmsEqualTo(pms);
		List<EHotelWithBLOBs> list = eHotelMapper.selectByExampleWithBLOBs(eh);
		EHotelWithBLOBs thisHotel = list.size() > 0 ? list.get(0) : new EHotelWithBLOBs();
		SessionUser su = new SessionUser();
		su.setLoginname(pmsLoginName);
		su.setSys(ContentUtils.PMS);
		su.setId(null);
		su.setName(pmsLoginName);
		
		AopLogWithBLOBs aoplog = initLog(thisHotel, su);
		aoplog.setParams(token);
		aoplog.setResult(JSONObject.fromObject(result).toString());
		String time = HmsDateUtils.getFormatDateString(new Date(), HmsDateUtils.FORMAT_DATETIME);
		List<LogData> data = new ArrayList<LogData>();
		StringBuffer sb = new StringBuffer();
		sb.append("pms用户为:【").append(pmsLoginName).append("】，").append("在【").append(time)
		  .append("】通过token登录hms。")
		  .append("登录酒店pms编码为:【").append(pms).append("】;").append("酒店id为:【")
		  .append(thisHotel.getId()).append("】，").append("酒店名称为:【").append(thisHotel.getHotelName())
		  .append("】。");
		data.add(new LogData(null, null, time, sb.toString()));
		aoplog.setData(JSONArray.fromObject(data).toString());
		execute(aoplog);
	}
	
	/**
	 * 初始化日志信息
	 * @param thisHotel 当前酒店
	 * @param currUser 当前用户
	 * @return 日志对象
	 */
	private static AopLogWithBLOBs initLog(EHotelWithBLOBs thisHotel, SessionUser currUser) {
		AopLogWithBLOBs aoplog = new AopLogWithBLOBs();
		aoplog.setHotelid(thisHotel.getId());
		aoplog.setIp(RequestUtils.getIp());
		aoplog.setTime(new Date());
		aoplog.setUrl(RequestUtils.getRequest().getRequestURI());
		aoplog.setUserid(currUser.getId());
		aoplog.setUsername(currUser.getName());
		aoplog.setLoginname(currUser.getLoginname());
		aoplog.setSys(currUser.getSys());
		return aoplog;
	}
	
	// 执行
	private static void execute(AopLogWithBLOBs aoplog) {
		RequestLogTask reqLogTask = new RequestLogTask(aoplog);
		HmsThreadPool.getDefaultPool().execute(reqLogTask);
	}
}
