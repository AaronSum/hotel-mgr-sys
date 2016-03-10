package com.mk.hms.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiniu.util.Auth;

public class HmsQiniuUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(HmsQiniuUtils.class);
	
	/**
	 * 获取上传凭证key
	 * @return 返回凭证key
	 */
	public static String generateSimpleUploadTicket() {
		//api地址：http://developer.qiniu.com/docs/v6/sdk/java-sdk.html
		try {
			Auth auth = Auth.create(HmsFileUtils.getSysContentItem(ContentUtils.QINIU_ACCESS_KEY),
					HmsFileUtils.getSysContentItem(ContentUtils.QINIU_SECRET_KEY));
			return auth.uploadToken(HmsFileUtils.getSysContentItem(ContentUtils.QINIU_BUCKET));
		} catch (IOException e) {
			HmsQiniuUtils.logger.error("获取七牛上传凭证异常：" + e.getMessage(), e);
		}		
		return null;
	}

	/**
	 * 获取下载凭证
	 * @param key 文件key
	 * @param treatMethod 处理管道
	 * @return 下载凭证
	 */
	public static String generateDownloadTicket(String key, String treatMethod) {
		//七牛api地址：http://developer.qiniu.com/docs/v6/sdk/java-sdk.html#private-download
		try {
			Auth auth = Auth.create(HmsFileUtils.getSysContentItem(ContentUtils.QINIU_ACCESS_KEY),
					HmsFileUtils.getSysContentItem(ContentUtils.QINIU_SECRET_KEY));
			return auth.privateDownloadUrl(HmsFileUtils.getSysContentItem(ContentUtils.QINIU_DOWNLOAD_ADDRESS) + key
					+ treatMethod, Long.parseLong(HmsFileUtils.getSysContentItem(ContentUtils.QINIU_INVALIDATION_TIME)));
		} catch (Exception e) {
			HmsQiniuUtils.logger.error("获取七牛下载凭证异常：" + e.getMessage(), e);
		}
		return null;
	}
}
