package com.mk.hms.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mk.hms.view.HmsPmsToken;

/**
 * des加密
 * @author hdy
 *
 */
public class DESUtils {
	
	/**超时时间戳(ms) 100s*/
	public static final long timeoutTimestamp = 100000;
	
	private static final Logger logger = LoggerFactory.getLogger(DESUtils.class);
	
	/**
	 * des加密
	 * @param data 要加密数据
	 * @return 加密后数据
	 */
	public static String desCrypto(String data) {
		if (StringUtils.isBlank(data)) {
			return null;
		}
		try {
			SecureRandom random = new SecureRandom();
			// 获取密钥
			DESKeySpec desKey = new DESKeySpec(getDesKey().getBytes());
			// 创建密匙工厂
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象加密
			Cipher cipher = Cipher.getInstance("DES");
			// 密匙初始化
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 获取数据并加密
			// 执行加密
			return parseByte2HexStr(cipher.doFinal(data.getBytes()));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * des解密
	 * @param src 加密数据
	 * @return 解密之后数据
	 * @throws Exception 异常
	 */
	public static String decrypt(String src) throws Exception {
		// DES算法要求一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		// 创建DESKeySpec对象
		DESKeySpec desKey = new DESKeySpec(getDesKey().getBytes());
		// 创建密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// 将DESKeySpec转换成SecretKey
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 密匙初始化Cipher
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		// 开始解密
		return new String(cipher.doFinal(parseHexStr2Byte(src)));
	}
	
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
            if (encrypByte == 16) {
            	returnContext =  buf.toString().substring(8, 24);
            } else if (encrypByte == 32) {
            	returnContext =  buf.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  
        }  
		return returnContext;
	}
	
	/**
	 * 将二进制转换成16进制
	 * @method parseByte2HexStr
	 * @param buf
	 * @return
	 * @throws
	 * @since v1.0
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
      
    /** 
     * 将16进制转换为二进制 
     * @method parseHexStr2Byte 
     * @param hexStr 
     * @return 
     * @throws  
     * @since v1.0 
     */  
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
    
	/**
	 * DES解密token，返回token对象
	 * @param token DES加密字符串
	 * @return 返回token解密对象
	 */
	public static HmsPmsToken decryptTokenByDES (String token) {
		HmsPmsToken hpt = new HmsPmsToken();
		try {
			String decryptString = "";
			decryptString = DESUtils.decrypt(token);
			String[] decryptArray = decryptString.split(ContentUtils.CHAR_COMMA);
			for (int i = 0; i < decryptArray.length; i++) {
				String s = decryptArray[i];
				if (i == 0) {
					hpt.setLoginname(s);
				} else if (i == 1) {
					hpt.setPms(s);
				} else if (i == 2) {
					hpt.setTimes(NumberUtils.toLong(s));
				}
			}
			hpt.setToken(token);
		} catch (Exception e) {
			hpt = null;
			logger.error("decryptTokenByDES(DES解密). parse token error:" + e.getMessage(), e);
			e.printStackTrace();
		}
		return hpt;
	}
	
	/**
	 * 获取des系统配置
	 * @return 配置信息
	 * @throws IOException 异常
	 */
	private static String getDesKey() throws IOException {
		String desKey = HmsFileUtils.getSysContentItem(ContentUtils.DES_KEY);
		if (desKey.startsWith("$")) {
			desKey = "_imike_hms_des_key_";
		}
		return desKey;
	}
	
	/*public static void main(String[] args) throws Exception {
		String data  = "Hello World.您好，世界。";
		String encryptResult = desCrypto(data);// 加密

		// 然后，我们再修订以上测试代码
		System.out.println("***********************************************");
		System.out.println("加密后：" + encryptResult);
		String decryptResult = decrypt(encryptResult);// 解码
		System.out.println("解密后：" + new String(decryptResult));
	}*/
}
