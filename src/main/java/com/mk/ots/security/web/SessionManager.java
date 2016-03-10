package com.mk.ots.security.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionManager {

	private static Logger logger = LoggerFactory.getLogger(SessionManager.class);

	private static final ThreadLocal<HttpSession> httpSession = new ThreadLocal<HttpSession>();

	public static final String USER_DETAIL_KEY = "userDetails";

	public static void setSession(HttpSession session) {
		SessionManager.httpSession.set(session);
	}

	public static HttpSession getSession() {
		return SessionManager.httpSession.get();
	}

	public static void setAttribute(String name, Object value) {
		HttpSession localHttpSession = SessionManager.httpSession.get();
		if (localHttpSession != null) {
			localHttpSession.setAttribute(name, value);
		}
		SessionManager.loger("debug", "session not exist can not set attribute with key = '" + name + "' value = [" + value + "]");
	}

	public static Object getAttribute(String name) {
		HttpSession localHttpSession = SessionManager.httpSession.get();
		if (localHttpSession != null) {
			return localHttpSession.getAttribute(name);
		}
		SessionManager.loger("debug", "session not exist could not get attribute '" + name + "'");
		return null;
	}

	public static UserDetail getUserDetail() {
		return (UserDetail) SessionManager.getAttribute(SessionManager.USER_DETAIL_KEY);
	}

	public static void setUserDetail(UserDetail userDetail) {
		SessionManager.loger("debug", "bind user [" + userDetail.getUsername() + "] to current session :" + SessionManager.httpSession.get());
		SessionManager.setAttribute(SessionManager.USER_DETAIL_KEY, userDetail);
	}

	public static String getUserName() {
		UserDetail localUserDetail = SessionManager.getUserDetail();
		if (localUserDetail != null) {
			return localUserDetail.getUsername();
		}
		SessionManager.loger("debug", "user detail is null return null user name");
		return null;
	}

	public static Long getUserId() {
		UserDetail localUserDetail = SessionManager.getUserDetail();
		if (localUserDetail != null) {
			return localUserDetail.getUserId();
		}
		SessionManager.loger("debug", "user detail is null return null user ID");
		return 0l;
	}

	public static Object getUser() {
		UserDetail localUserDetail = SessionManager.getUserDetail();
		if (localUserDetail != null) {
			return localUserDetail.getUser();
		}
		SessionManager.loger("debug", "user detail is null return null user");
		return null;
	}

	private static void loger(String type, String message) {
		if (type.equalsIgnoreCase("debug") && SessionManager.logger.isDebugEnabled()) {
			SessionManager.logger.debug(message);
		}
	}

	public static void logout() {
		HttpSession localHttpSession = SessionManager.httpSession.get();
		if (localHttpSession != null) {
			String str = SessionManager.getUserName();
			if (SessionManager.httpSession.get() != null) {
				SessionManager.httpSession.set(null);
			}
			localHttpSession.invalidate();
			SessionManager.loger("debug", "user [" + str + "] logout.");
		}
	}

}
