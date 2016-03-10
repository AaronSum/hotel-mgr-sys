package com.mk.hms.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.model.OutModel;
import com.mk.hms.model.VerifyPhoneModel;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsFileUtils;
import com.mk.hms.utils.HmsRedisCacheUtils;
import com.mk.hms.utils.HttpClientUtils;

/**
 * 短信验证码 sevice
 * @author hdy
 *
 */
@Service
@Transactional
public class HmsSmsMsgService {
	
	private static final Logger logger = LoggerFactory.getLogger(HmsSmsMsgService.class);
	
	/**
	 * 发送验证码
	 * @param phone 手机号
	 * @param message 电信内容
	 * @param type 发送类型
	 * @return 发送结果
	 * @throws IOException 
	 */
	public void sendSmsMsg(String phone, int verifyCode, String message, String type, OutModel out){
		Map<String, String> params = new HashMap<String, String>();
		params.put("phone", phone);
		params.put("message", message);
		params.put("type", type);
		try {
			HmsSmsMsgService.logger.info("调用短息发送接口---开始---");
			String smsAddr = HmsFileUtils.getSysContentItem(ContentUtils.SMS_MSG_ADDRESS);
			JSONObject entityObj = HttpClientUtils.post(smsAddr, params);
			if (null == entityObj) {
				HmsSmsMsgService.logger.error("短信发送接口异常");
				out.setErrorMsg("短信发送接口异常");
				return;
			}
			if (!entityObj.getBoolean("success")) {
				HmsSmsMsgService.logger.error(entityObj.getString("errmsg") + ", 错误编码：" + entityObj.getString("errcode"));
				out.setErrorMsg(entityObj.getString("errmsg") + ", 错误编码：" + entityObj.getString("errcode"));
				return;
			}
			HmsSmsMsgService.logger.info("调用短息发送接口---结束---");
			VerifyPhoneModel verifyPhone = new VerifyPhoneModel();
			verifyPhone.setPhnoeNo(phone);
			verifyPhone.setVerifyCode(verifyCode);
			HmsRedisCacheUtils.setVerifyPhoneModel(verifyPhone);
			out.setSuccess(true);
		} catch (IOException e) {
			HmsSmsMsgService.logger.error("短信发送接口异常：" + e.getMessage(), e);
		}
	}

}
