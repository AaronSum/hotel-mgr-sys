package com.mk.hms.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 字符串工具类
 * @author hdy
 *
 */
public class HmsStringUtils extends StringUtils {
	
	/**
	 * 通过md5加密
	 * @param context 被加密内容
	 * @param encrypByte 几位加密
	 * @return 返回加密数据
	 */
	public static String encrypByMd5(String context, int encrypByte) {
		String returnContext = context;
		try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(context.getBytes());//update处理
            byte [] encryContext = md.digest();//调用该方法完成计算
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < encryContext.length; offset++) {//做相应的转化（十六进制）
                i = encryContext[offset];
                if (i < 0) i += 256;
                if (i < 16) buf.append("0");  
                buf.append(Integer.toHexString(i));
           }
            if (encrypByte == 32) {
            	returnContext =  buf.toString();
            } else if (encrypByte == 64) {
            	returnContext =  buf.toString().substring(8, 24);
            }  
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();  
        }  
		return returnContext;
	}
	
	/**
	 * 二维码tag是否合法
	 * @param tag tag标示
	 * @return 是否合法
	 */
	public static boolean isAllowQrcodeTag(String tag) {
		return ContentUtils.ALLOW_QRCODE_TAGS.contains(tag);
	}
	
	/**
	 * 手机号是否合法
	 * @param phoneNo 手机号
	 * @return 是否合法
	 */
	public static boolean isAllowPhoneNo(String phoneNo) {
		Pattern p = Pattern.compile("^1\\d{10}$");
		Matcher m = p.matcher(phoneNo);
		return m.matches();
	}
	
	/**
	 * 获取随机验证码
	 * @param strLength 随机数位数
	 * @return 验证码
	 */
	public static String getRandomString(int strLength) {
		 Random rm = new Random();
		 // 获得随机数
		 double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
		 // 将获得的获得随机数转化为字符串
		 String fixLenthString = String.valueOf(pross);
		 // 返回固定的长度的随机数
		 return fixLenthString.substring(1, strLength + 1);
	}
	
	/**
	 * 获取随机验证码
	 * @param strLength 随机数位数
	 * @return 验证码
	 */
	public static int getRandomNum(int strLength) {
		return Integer.parseInt(getRandomString(strLength));
	}
	
	/**
	 * 超级验证码
	 * @return 是否为超级验证码
	 * @throws IOException 
	 */
	public static boolean isRootVerifyCode(int verifyCode) throws IOException {
		// 是否存在
		if (verifyCode <= 0) {
			return false;
		}
		// 获取超级验证码
		String rootVerifyCode = "";
		rootVerifyCode = HmsFileUtils
				.getSysContentItem(ContentUtils.ROOT_VERIFY_CODE);
		// 超级验证码是否为空
		if (isBlank(rootVerifyCode)) {
			return false;
		}
		// 超级验证码是否为数字
		if (!NumberUtils.isNumber(rootVerifyCode)) {
			return false;
		}
		// 是否相等
		if (verifyCode == Integer.parseInt(rootVerifyCode)) {
			return true;
		}
		return false;
	}
}
