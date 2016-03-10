package com.mk.hms.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.hms.model.OutModel;
import com.mk.hms.service.SmsService;

/**
 * 短信 控制器
 * @author hdy
 *
 */
@Controller
@RequestMapping("/sms")
public class SmsController {
	
	@Autowired
	private SmsService smsService = null;
	
	/**
	 * 发送手机验证码
	 * @return 发送手机验证码
	 * @throws IOException 
	 */
	@RequestMapping("/verifyCode")
	@ResponseBody
	public OutModel sendVerifyCode(String phoneNum) throws IOException {
		return this.getSmsService().sendSmsMsg4Phone(phoneNum);
	}

	/**
	 * 为找回密码发送手机验证码
	 * @return 发送手机验证码
	 * @throws IOException 
	 */
	@RequestMapping("/retrievePassword")
	@ResponseBody
	public OutModel sendVerifyCode4RetrievePassword(String phoneNum, String phoneNumJin) throws IOException {
		return this.getSmsService().sendVerifyCode4RetrievePassword(phoneNum, phoneNumJin);
	}
	
	private SmsService getSmsService() {
		return smsService;
	}

}
