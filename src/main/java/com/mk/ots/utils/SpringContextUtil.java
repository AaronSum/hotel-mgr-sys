package com.mk.ots.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {

	private static Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);

	private static int WARNG_TIME_FREQUENCY = 60000;

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		synchronized (SpringContextUtil.class) {
			SpringContextUtil.applicationContext = applicationContext;
			SpringContextUtil.class.notifyAll();
		}
	}

	public static ApplicationContext getApplicationContext() {
		if (SpringContextUtil.applicationContext == null) {
			synchronized (SpringContextUtil.class) {
				while (SpringContextUtil.applicationContext == null) {
					try {
						SpringContextUtil.class.wait(SpringContextUtil.WARNG_TIME_FREQUENCY);
						// 每超过60秒未完成初始化，打印一条警告消息
						SpringContextUtil.logger.warn("waiting spring init for a long time. {}", SpringContextUtil.WARNG_TIME_FREQUENCY);
					} catch (InterruptedException ex) {
					}
				}
			}
		}
		return SpringContextUtil.applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) SpringContextUtil.getApplicationContext().getBean(name);
	}

	public static <T> T getBean(Class<T> requiredType) {
		return SpringContextUtil.getApplicationContext().getBean(requiredType);
	}
}
