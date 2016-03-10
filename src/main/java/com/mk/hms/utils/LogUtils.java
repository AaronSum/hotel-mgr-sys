package com.mk.hms.utils;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.User;
import com.mk.hms.service.RequestLogService;
import com.mk.hms.view.LoginUser;

/**
 * 日志工具
 * @author wangchen
 *
 */
@Component
public class LogUtils {
	
	private final static String REQ_IDENTIFIED_CODE = "\r\n_REQUEST_LOG_TAG_ID_";
	/** 操作日志对象*/
	private static RequestLogService requestLogService;
	
	/**
	 * 输出错误日志
	 */
	public static void logErr(Logger logger, String text, Exception e) {
		logErrPlusMap(logger, text, null, e);
	}
	
	/**
	 * 输出错误日志+自定义数据
	 */
	public static void logErrPlusMap(Logger logger, String text, Map customMap, Exception e) {
		try {
			HttpServletRequest request = RequestUtils.getRequest();
			StringBuilder baseInfoSdb = new StringBuilder();
			String reqIdentifiedCode = "";
		    StringBuilder cusDataSbd = new StringBuilder();
			if (request != null) {
				reqIdentifiedCode = (String)request.getAttribute(REQ_IDENTIFIED_CODE);
				LoginUser loginUser = (LoginUser)request.getAttribute("loginUser");
				if (loginUser == null) {
					loginUser = SessionUtils.getSessionLoginUser();
				}
				User user = loginUser.getUser();
				String loginName = user.getLoginname();
				String name = user.getName();
				EHotelWithBLOBs thisHotel = loginUser.getThisHotel();
				String hotelName = thisHotel.getHotelName();
				long hotelId = thisHotel.getId();
				baseInfoSdb.append("基本信息--");
				baseInfoSdb.append("登录名：" + loginName + "\r\n");
				baseInfoSdb.append("用户名：" + name + "\r\n");
				baseInfoSdb.append("酒店名：" + hotelName + "\r\n");
				baseInfoSdb.append("酒店ID：" + hotelId + "\r\n");
			}
			if (customMap != null) {
				Set set = customMap.entrySet();         
				Iterator i = set.iterator();         
				while(i.hasNext()){
					Map.Entry<String, String> entry = (Map.Entry<String, String>)i.next();
					cusDataSbd.append(entry.getKey() + ": " + entry.getValue() + "\r\n");
				}
			}
			//
			String logStr = REQ_IDENTIFIED_CODE  + ":" + reqIdentifiedCode + "\r\n_TEXT_:" + text;
			if (baseInfoSdb.length() > 0) {
				logStr += "\r\n_BASE_DATA_:" +baseInfoSdb.toString();
			}
			if (cusDataSbd.length() > 0) {
				logStr += "\r\n_CUSTOM_DATA_:" +cusDataSbd.toString();
			}
			String eMessage = "";
			if (e != null) {
				eMessage = e.getMessage();
			}
			logger.error(logStr, eMessage, e);
		} catch(Exception e2) {
			//
		}
	}
	
	/**
	 * 输出流程日志
	 */
	public static void logStep(Logger logger, String text) {
		logStepPlusMap(logger, text, null);
	}
	
	/**
	 * 输出流程日志+自定义数据
	 */
	public static void logStepPlusMap(Logger logger, String text, Map customMap) {
		try {
			if (logger == null) {
				logger = LoggerFactory.getLogger("输出流程日志");
			}
			HttpServletRequest request = RequestUtils.getRequest();
			StringBuilder baseInfoSdb = new StringBuilder();
			String reqIdentifiedCode = "";
		    StringBuilder cusDataSbd = new StringBuilder();
			if (request != null) {
				reqIdentifiedCode = (String)request.getAttribute(REQ_IDENTIFIED_CODE);
				LoginUser loginUser = (LoginUser)request.getAttribute("loginUser");
				if (loginUser == null) {
					loginUser = SessionUtils.getSessionLoginUser();
				}
				User user = loginUser.getUser();
				String loginName = user.getLoginname();
				String name = user.getName();
				EHotelWithBLOBs thisHotel = loginUser.getThisHotel();
				baseInfoSdb.append("基本信息--");
				baseInfoSdb.append("登录名：" + loginName + "\r\n");
				baseInfoSdb.append("用户名：" + name + "\r\n");
				if (thisHotel != null) {
					String hotelName = thisHotel.getHotelName();
					long hotelId = thisHotel.getId();
					baseInfoSdb.append("酒店名：" + hotelName + "\r\n");
					baseInfoSdb.append("酒店ID：" + hotelId + "\r\n");
				}
			}
			if (customMap != null) {
				Set set = customMap.entrySet();         
				Iterator i = set.iterator();         
				while(i.hasNext()){
					Map.Entry<String, String> entry = (Map.Entry<String, String>)i.next();
					cusDataSbd.append(entry.getKey() + ": " + entry.getValue() + "\r\n");
				}
			}
			//
			String logStr = REQ_IDENTIFIED_CODE  + ":" + reqIdentifiedCode + "\r\n_TEXT_:" + text;
			if (baseInfoSdb.length() > 0) {
				logStr += "\r\n_BASE_DATA_:" +baseInfoSdb.toString();
			}
			if (cusDataSbd.length() > 0) {
				logStr += "\r\n_CUSTOM_DATA_:" +cusDataSbd.toString();
			}
			logger.info(logStr);
		} catch(Exception e) {
			//
		}
	}
	
	/**
	 * 设置登录用户信息
	 * @param user 登录用户信息
	 */
	public static void logReq(Logger logger, String text, Map<String, Object> params) {
		try {
			HttpServletRequest request = RequestUtils.getRequest();
			String sessionId = request.getSession().getId();
			String reqIdentifiedCode = sessionId + "___" + new Date().getTime();
			request.setAttribute(REQ_IDENTIFIED_CODE, reqIdentifiedCode);
			String reqUrl = request.getRequestURL().toString() + "?" + request.getQueryString();
			String logStr = REQ_IDENTIFIED_CODE  + ":" + reqIdentifiedCode + "\r\n_TEXT_:" + text;
			StringBuilder paramDataSbd = new StringBuilder();
			paramDataSbd.append("\r\n请求URL: " + reqUrl + "\r\n");
			if (params != null && !params.isEmpty()) {
				Set set = params.entrySet();         
				Iterator i = set.iterator();         
				while(i.hasNext()){
					Map.Entry<String, Object> entry = (Map.Entry<String, Object>)i.next();
					String key = entry.getKey();
					String value = entry.getValue().toString();
					paramDataSbd.append(key + ": " + value + "\r\n");
				}
			}
			logStr += paramDataSbd.toString();
			StringBuilder baseInfoSdb = new StringBuilder();
			LoginUser loginUser = SessionUtils.getSessionLoginUser();
			if (loginUser != null) {
				User user = loginUser.getUser();
				String loginName = user.getLoginname();
				String name = user.getName();
				EHotelWithBLOBs thisHotel = loginUser.getThisHotel();
				baseInfoSdb.append("基本信息--");
				baseInfoSdb.append("登录名：" + loginName + "\r\n");
				baseInfoSdb.append("用户名：" + name + "\r\n");
				if (thisHotel != null) {
					String hotelName = thisHotel.getHotelName();
					long hotelId = thisHotel.getId();
					baseInfoSdb.append("酒店名：" + hotelName + "\r\n");
					baseInfoSdb.append("酒店ID：" + hotelId + "\r\n");
				}
				if (baseInfoSdb.length() > 0) {
					logStr += "_BASE_DATA_:" + baseInfoSdb.toString();
				}
				if (request != null) {
					request.setAttribute("loginUser", loginUser);
				}
			}
			logger.info(logStr);
		} catch(Exception e) {
			//
		}
	}

	public static RequestLogService getRequestLogService() {
		return requestLogService;
	}
	
	@Autowired
	public void setRequestLogService(RequestLogService requestLogService){
		LogUtils.requestLogService = requestLogService;
	}
}
