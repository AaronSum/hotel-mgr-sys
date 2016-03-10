package com.mk.hms.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mk.hms.mapper.MUserMapper;
import com.mk.hms.mapper.UserMapper;
import com.mk.hms.model.MUser;
import com.mk.hms.model.MUserCriteria;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.User;
import com.mk.hms.model.UserCriteria;
import com.mk.hms.model.VerifyPhoneModel;
import com.mk.hms.model.MUserCriteria.Criteria;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsFileUtils;
import com.mk.hms.utils.HmsRedisCacheUtils;
import com.mk.hms.utils.HmsStringUtils;
import com.mk.hms.utils.HttpClientUtils;

/**
 * 短信 service
 * @author hdy
 *
 */
@Service
@Transactional
public class SmsService {

	private static final Logger logger = LoggerFactory.getLogger(SmsService.class);
	
	/**短信发发送类型*/
	public final String smsMsgType = "1";
	
	@Autowired
	private UserMapper userMapper = null;
	
	@Autowired
	private MUserMapper mUserMapper = null;
	
	/**
	 * 根据登录名， 获取用户
	 * @param loginName 登录名
	 * @return 用户对象
	 */
	public User findHmsUserByLoginName(String loginName) {
		UserCriteria userCriteria = new UserCriteria();
		userCriteria.createCriteria().andLoginnameEqualTo(loginName);
		List<User> users = this.getUserMapper().selectByExample(userCriteria);
		return users.size() > 0 ? users.get(0) : null;
	}

	/**
	 * 获取销售用户
	 * @param loginName 登录名
	 * @return 销售用户实体
	 */
	public MUser findPmsUserByLoginName(String loginName) {
		MUserCriteria mUserCriteria = new MUserCriteria();
		Criteria loginNameCriteria = mUserCriteria.createCriteria();
		loginNameCriteria.andLoginnameEqualTo(loginName);
		Criteria phoneCriteria = mUserCriteria.createCriteria();
		phoneCriteria.andPhoneEqualTo(loginName);
		mUserCriteria.or(phoneCriteria);
		List<MUser> mUsers = this.getmUserMapper().selectByExample(mUserCriteria);
		return mUsers.size() > 0 ? mUsers.get(0) : null;
	}
	
	/**
	 * 找回密码第一步发送短息
	 * @param retrievePassword 实体对象
	 * @return 状态
	 * @throws IOException
	 */
	public OutModel sendVerifyCode4RetrievePassword(String phoneNum, String phoneNumJin) throws IOException {
		String phoneNumStr = "";
		if (HmsStringUtils.isNoneBlank(phoneNum)) {
			phoneNumStr = phoneNum;
		} else if (HmsStringUtils.isNoneBlank(phoneNumJin)) {
			phoneNumStr = phoneNumJin;
		}
		OutModel out = new OutModel(false);
		// 手机号是否合法
		if (!HmsStringUtils.isAllowPhoneNo(phoneNumStr)) {
			out.setErrorMsg("手机号码不合法");
			return out;
		}
		// 获取缓存中是否存在该手机号验证码
		VerifyPhoneModel verifyPhone = HmsRedisCacheUtils.getVerifyPhoneObjInCache(phoneNumStr);
		if (null != verifyPhone) {
			out.setErrorMsg("验证码还未过期可继续使用");
			return out;
		}
		// 查看当前手机号是否已被注册
		// 酒店用户
		if (HmsStringUtils.isNoneBlank(phoneNum)) {
			User user = this.findHmsUserByLoginName(phoneNum);
			if (null == user) {
				out.setErrorMsg("该手机号未注册");
				return out;
			}
		// 销售人员
		} else if (HmsStringUtils.isNoneBlank(phoneNumJin)) {
			MUser mUser = this.findPmsUserByLoginName(phoneNumJin);
			if (null == mUser) {
				out.setErrorMsg("该手机号没有用户绑定，请确认您的个人信息已绑定手机号码");
				return out;
			}
		}
		VerifyPhoneModel newVerifyPhone = new VerifyPhoneModel();
		newVerifyPhone.setPhnoeNo(phoneNumStr);
		newVerifyPhone.setVerifyCode(HmsStringUtils.getRandomNum(6));
		// 调用发送验证码接口
		String message = HmsFileUtils.getSysContentItem(ContentUtils.SMS_MSG_PREFIX) + newVerifyPhone.getVerifyCode();
		// 发送验证码码
		out = this.sendSmsMsg(newVerifyPhone, message);
		if (!out.isSuccess()) {
			out.setErrorMsg("验证码发送失败");
			return out;
		}
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * 发送验证码
	 * @param phoneNum 手机号码
	 * @return 发送状态
	 * @throws IOException 例外
	 */
	public OutModel sendSmsMsg4Phone(String phoneNum) throws IOException {
		OutModel out = new OutModel(false);
		// 手机号是否合法
		if (!HmsStringUtils.isAllowPhoneNo(phoneNum)) {
			out.setErrorMsg("手机号码不合法");
			return out;
		}
		// 获取缓存中是否存在该手机号验证码
		VerifyPhoneModel verifyPhoneObj = HmsRedisCacheUtils.getVerifyPhoneObjInCache(phoneNum);
		if (null != verifyPhoneObj) {
			out.setErrorMsg("验证码还未过期可继续使用");
			return out;
		}
		// 查看当前手机号是否已被注册
		User user = this.findHmsUserByLoginName(phoneNum);
		if (null != user) {
			out.setErrorMsg("该手机号已注册");
			return out;
		}
		VerifyPhoneModel newVerifyPhone = new VerifyPhoneModel();
		newVerifyPhone.setPhnoeNo(phoneNum);
		newVerifyPhone.setVerifyCode(HmsStringUtils.getRandomNum(6));
		String message = HmsFileUtils.getSysContentItem(ContentUtils.SMS_MSG_PREFIX) + newVerifyPhone.getVerifyCode();
		return this.sendSmsMsg(newVerifyPhone, message);
	}
	
	/**
	 * 发送验证码
	 * @param phone 手机号
	 * @param message 电信内容
	 * @param type 发送类型
	 * @return 发送结果
	 * @throws IOException 
	 */
	public OutModel sendSmsMsg(VerifyPhoneModel verifyPhoneModel, String message) throws IOException{
		OutModel out = new OutModel(false);
		Map<String, String> params = new HashMap<String, String>();
		params.put("phone", verifyPhoneModel.getPhnoeNo());
		params.put("message", message);
		params.put("type", smsMsgType);
		SmsService.logger.info("调用短息发送接口---开始---");
		String smsAddr = HmsFileUtils.getSysContentItem(ContentUtils.SMS_MSG_ADDRESS);
		JSONObject entityObj = HttpClientUtils.post(smsAddr, params);
		if (null == entityObj) {
			SmsService.logger.error("短信发送接口异常");
			out.setErrorMsg("短信发送接口异常");
			return out;
		}
		if (!entityObj.getBoolean("success")) {
			SmsService.logger.error(entityObj.getString("errmsg") + ", 错误编码：" + entityObj.getString("errcode"));
			out.setErrorMsg(entityObj.getString("errmsg") + ", 错误编码：" + entityObj.getString("errcode"));
			return out;
		}
		SmsService.logger.info("调用短息发送接口---结束---");
		HmsRedisCacheUtils.setVerifyPhoneModel(verifyPhoneModel);
		System.out.println("---------------" + verifyPhoneModel.getVerifyCode() + "------------------");
		out.setSuccess(true);
		return out;
	}
	
	private UserMapper getUserMapper() {
		return userMapper;
	}

	private MUserMapper getmUserMapper() {
		return mUserMapper;
	} 
	
}
