package com.mk.hms.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * 文件工具类
 * @author hdy
 *
 */
public class HmsFileUtils extends FileUtils{
	
	/**系统配置*/
	private static Properties contentProps = null;
	
	/**
	 * 获取session url filter 配置文件
	 * @return Properties
	 * @throws IOException 异常
	 */
	public static Properties getUrlFilterProperties() throws IOException {
		Resource resource = new ClassPathResource("/urlfilter.properties");
		Properties props = PropertiesLoaderUtils.loadProperties(resource);
		return props;
	}
	
	/**
	 * 获取classPath 下.properties 配置文件数据
	 * @param fileName  文件名
	 * @return Properties properties类型数据
	 * @throws IOException 异常
	 */
	public static Properties getPropertiesFile(String fileName) throws IOException {
		Resource resource = new ClassPathResource("/" + fileName);
		Properties props = PropertiesLoaderUtils.loadProperties(resource);
		return props;
	}
	
	/**
	 * 获取系统配置
	 * @param itemKey 系统配置key值
	 * @return 配置信息
	 * @throws IOException 异常
	 */
	public static String getSysContentItem(String itemKey) throws IOException {
		if (null == contentProps) {
			Resource resource = new ClassPathResource("/context.properties");
			contentProps = PropertiesLoaderUtils.loadProperties(resource);
		}
		return contentProps.getProperty(itemKey);
	}
}
