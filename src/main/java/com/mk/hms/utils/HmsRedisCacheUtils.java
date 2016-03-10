package com.mk.hms.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Component;

import com.mk.hms.model.HmsUMemberModel;
import com.mk.hms.model.VerifyPhoneModel;
import com.mk.hms.service.HmsUMemberService;

/**
 * 缓存工具类
 * @author hdy
 *
 */
@Component
public class HmsRedisCacheUtils {

	/**缓存对象*/
	private static RedisCacheManager cacheManager;
	
	/**会员服务*/
	private static HmsUMemberService hmsUMemberService;

	@Autowired
	public void setCacheManager(RedisCacheManager cacheManager){
		HmsRedisCacheUtils.cacheManager = cacheManager;
	}
	
	@Autowired
	public void setUMember(HmsUMemberService hmsUMemberService) {
		HmsRedisCacheUtils.hmsUMemberService = hmsUMemberService;
	}
	/**
	 * 获取指定缓存
	 * @param cache 
	 * @return
	 */
	public static Cache getRedisCache(String cache) {
		// 获取缓存管理器中的缓存配置名称
		return cacheManager.getCache(cache);
	}
	
	/**
	 * 验证码是否过期
	 * @param phoneNo 手机号
	 * @return 是否过期
	 */
	public static boolean verifyCodeIsExpired(String phoneNo) {
		ValueWrapper value = getRedisCache(ContentUtils.VERIFY_PHONE_CODE_CACHE).get(ContentUtils.CACHE_KEY_PREFIX + phoneNo);
		if (null != value) {
			return true;
		}
		return false;
	}
	
	/**
	 * 手机和验证码是偶匹配
	 * @return 是偶匹配
	 * @throws IOException 
	 */
	public static boolean verifyCodeIsValid(String phoneNo, int verifyCode) throws IOException {
		// 如果是超级验证码，直接加入缓存
		if (HmsStringUtils.isRootVerifyCode(verifyCode)) {
			VerifyPhoneModel verifyPhone = new VerifyPhoneModel();
			verifyPhone.setPhnoeNo(phoneNo);
			verifyPhone.setVerifyCode(verifyCode);
			setVerifyPhoneModel(verifyPhone);
		}
		VerifyPhoneModel verifyPhone = getRedisCache(ContentUtils.VERIFY_PHONE_CODE_CACHE)
				.get(ContentUtils.CACHE_KEY_PREFIX + phoneNo, VerifyPhoneModel.class);
		if (null != verifyPhone) {
			if (verifyPhone.getPhnoeNo().equals(phoneNo) && verifyPhone.getVerifyCode() == verifyCode) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取缓存中对应手机验证码
	 * @param phoneNo 手机号
	 * @return 验证码对象
	 */
	public static VerifyPhoneModel getVerifyPhoneObjInCache(String phoneNo) {
		VerifyPhoneModel verifyPhone = getRedisCache(ContentUtils.VERIFY_PHONE_CODE_CACHE)
				.get(ContentUtils.CACHE_KEY_PREFIX + phoneNo, VerifyPhoneModel.class);
		return verifyPhone;
	}
	
	/**
	 *  加入指定缓存对象
	 * @param verifyPhone 缓存对象
	 */
	public static void setVerifyPhoneModel(VerifyPhoneModel verifyPhone) {
		getRedisCache(ContentUtils.VERIFY_PHONE_CODE_CACHE)
			.put(ContentUtils.CACHE_KEY_PREFIX + verifyPhone.getPhnoeNo(), verifyPhone);
	}
	
	/**
	 * 获取缓存中用户信息列表
	 * @return 用户信息列表
	 */
	@SuppressWarnings("unchecked")
	public static List<HmsUMemberModel> getUMemberListCache() {
		List<HmsUMemberModel> list = getRedisCache(ContentUtils.U_MEMBER_CACHE).get(ContentUtils.CACHE_U_MEMBER_KEY, ArrayList.class);
		if (null == list) {
			list = hmsUMemberService.findUMerbers();
			getRedisCache(ContentUtils.U_MEMBER_CACHE).put(ContentUtils.CACHE_U_MEMBER_KEY, list);
		} 
		return list;
	}
	
	/**
	 * 获取缓存中会员对象
	 * @param mid 会员id
	 * @return 会员对象
	 */
	public static HmsUMemberModel getUMemberInCache(long mid) {
		List<HmsUMemberModel> list = getUMemberListCache();
		for (HmsUMemberModel uMember : list) {
			if (uMember.getMid() == mid) {
				return uMember;
			}
		}
		return null;
	}
}
