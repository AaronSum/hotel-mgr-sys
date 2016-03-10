package com.mk.hms.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.mk.hms.model.HmsUserModel;
import com.mk.hms.model.MUser;
import com.mk.hms.view.SessionUser;

public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return SpringContextUtil.applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) SpringContextUtil.getApplicationContext().getBean(name);
	}

	public static <T> T getBean(Class<T> requiredType) {
		return SpringContextUtil.getApplicationContext().getBean(requiredType);
	}

	
	/**
	 * 
	 * @return 获取当前登录用户的ID
	 */
	public static Long getCurrentLoginUserId() {
		SessionUser user = (SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return user.getId();
	}
	
	/**
	 * 
	 * @return 当前登录用户
	 */
	public static SessionUser getCurrentLoginUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			SessionUser user = new SessionUser();
			String canonicalName = principal.getClass().getCanonicalName();
			if (HmsUserModel.class.getCanonicalName().equals(canonicalName)) {
				HmsUserModel hmsUser = (HmsUserModel) principal;
				user.setId(hmsUser.getId());
				user.setName(hmsUser.getName());
				user.setLoginname(hmsUser.getLoginname());
				user.setSys(ContentUtils.HMS);
				return user;
			} else if (MUser.class.getCanonicalName().equals(canonicalName)) {
				MUser mUser = (MUser) principal;
				user.setId(mUser.getId());
				user.setName(mUser.getName());
				user.setLoginname(mUser.getLoginname());
				user.setSys(ContentUtils.PMS);
				return user;
			}
		}
		
		return null;
	}
}
