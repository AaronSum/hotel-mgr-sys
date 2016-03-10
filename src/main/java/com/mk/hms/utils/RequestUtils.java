package com.mk.hms.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/***
 * 请求工具类
 * @author hdy
 *
 */
public class RequestUtils {

	private static final Logger logger = LoggerFactory.getLogger(RequestUtils.class);
	
	/**
	 * 获取请求
	 * @return request
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 获取response
	 * @return response
	 */
	public static HttpServletResponse getResponse() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
	}
	
	/**
	 * 获取参数集合
	 * @param request 请求
	 * @return 参数map集合
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getParameters4Angular() {
		HttpServletRequest request = getRequest();
		Map<String, Object> out = new HashMap<String, Object>();
		try {
			String data = IOUtils.toString(request.getReader());
			if (StringUtils.isNotBlank(data)) {
				out = (HashMap<String, Object>)JSONObject.toBean(JSONObject.fromObject(data), HashMap.class);
			} else {
				out = getParameters4Http();
			}
		} catch (Exception e) {
			RequestUtils.logger.error("获取参数异常(IOException)：" + e.getMessage(), e);
			out = getParameters4Http();
		}
		return out;
	}
	
	/**
	 * 动态返回某个参数实体对象
	 * @param request 请求
	 * @param beanClass 实体类
	 * @return 参数实体对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getParameters4Angular(Class<T> beanClass) {
		HttpServletRequest request = getRequest();
		T bean = null;
		try {
			bean = beanClass.newInstance();
			String data = IOUtils.toString(request.getReader());
			if (StringUtils.isNotBlank(data)) {
				HashMap<String, Object> params = (HashMap<String, Object>)JSONObject.toBean(JSONObject.fromObject(data), HashMap.class);
				BeanUtils.populate(bean, params);
			}
		} catch (IOException e) {
			RequestUtils.logger.error("获取参数异常(IOException)：" + e.getMessage(), e);
		}catch (InstantiationException e) {
			RequestUtils.logger.error("获取参数异常(InstantiationException)：" + e.getMessage(), e);
		} catch (IllegalAccessException e) {
			RequestUtils.logger.error("获取参数异常(IllegalAccessException)：" + e.getMessage(), e);
		} catch (InvocationTargetException e) {
			RequestUtils.logger.error("获取参数异常(InvocationTargetException)：" + e.getMessage(), e);
		}
		return bean;
	}
	
	/**
	 * 通过表单form提交获取参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getParameters4Http() {
		HttpServletRequest request = getRequest();
		Map<String, Object> map = new HashMap<String, Object>();
        Enumeration paramNames = request.getParameterNames();  
        while (paramNames.hasMoreElements()) {  
            String paramName = (String) paramNames.nextElement();  
  
            String[] paramValues = request.getParameterValues(paramName);  
            if (paramValues.length == 1) {  
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {  
                    map.put(paramName, paramValue);  
                }  
            }  
        }
		return map;
	}
	
	/**
	 * 通过表单form提交获取参数
	 * @param beanClass 实体对象
	 * @return 参数实体
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T getParameters4Http(Class<T> beanClass) {
		HttpServletRequest request = getRequest();
		T bean = null;
		try {
			bean = beanClass.newInstance();
			Map<String, Object> map = new HashMap<String, Object>();
	        Enumeration paramNames = request.getParameterNames();  
	        while (paramNames.hasMoreElements()) {  
	            String paramName = (String) paramNames.nextElement();  
	  
	            String[] paramValues = request.getParameterValues(paramName);  
	            if (paramValues.length == 1) {  
	                String paramValue = paramValues[0];  
	                if (paramValue.length() != 0) {  
	                    map.put(paramName, paramValue);  
	                }  
	            }  
	        }
			BeanUtils.populate(bean, map);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * agularJs $http请求获取参数
	 * @return 参数结果
	 */
	public static Map<String, Object> getParameters() {
		HttpServletRequest request = getRequest();
		String method = request.getMethod();
		if (method.toLowerCase().equals(ContentUtils.GET_METHOD)) {
			return getParameters4Http();
		} else if (method.toLowerCase().equals(ContentUtils.POST_METHOD)) {
			return getParameters4Angular();
		}
		return null;
	}
	
	/**
	 *  将参数映射为实体
	 * @param beanClass 实体对象
	 * @return 参数实体
	 */
	public static <T> T getParameters(Class<T> beanClass) {
		HttpServletRequest request = getRequest();
		String method = request.getMethod();
		if (method.toLowerCase().equals(ContentUtils.GET_METHOD)) {
			return getParameters4Http(beanClass);
		} else if (method.toLowerCase().equals(ContentUtils.POST_METHOD)) {
			return getParameters4Angular(beanClass);
		}
		return null;
	}
	
	/**
	 * 获取ip地址
	 * @return ip地址字符换
	 */
	public static String getIp() {
		HttpServletRequest request = getRequest();
		String ipAddress = null;
		// ipAddress = request.getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress.contains(ContentUtils.CHAR_COMMA)) {
			ipAddress = ipAddress.split(ContentUtils.CHAR_COMMA)[0];
		}
		return ipAddress;
	}
}
