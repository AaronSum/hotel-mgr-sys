package com.mk.hms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.hms.model.OutModel;
import com.mk.hms.service.Hms4PmsService;

/**
 * hms2pms 接口
 * @author hdy
 *
 */
@Controller
@RequestMapping("/hms4pms")
public class Hms4PmsController {
	
	@Autowired
	private Hms4PmsService hms4PmsService;
	
	/**
	 * 获取token
	 * @param userId 用户id
	 * @param hotelId 酒店id
	 * @param userName 用户登录名
	 * @param userType 用户类型1，老板；2，前台
	 * @return 输出对象
	 */
	@RequestMapping("/token")
	@ResponseBody
	public OutModel getToken4Pms(String json, HttpServletRequest request) {
		return this.getHms4PmsService().getToken(json, request);
	}
	
	/**
	 * 登录hms系统
	 * @param token pms token
	 * @return 登录页面
	 */
	@RequestMapping("/login/{token}")
	public ModelAndView loginHms4Pms(@PathVariable String token) {
		return this.getHms4PmsService().loginHms4Pms(token);
	}
	
	/**
	 * 错误信息跳转 
	 * @param success 成功标志
	 * @return
	 */
	@RequestMapping("/login/error/{msg}")
	public ModelAndView loginError(@PathVariable String msg) {
		return this.getHms4PmsService().loginError(msg);
	}
	
	private Hms4PmsService getHms4PmsService() {
		return hms4PmsService;		
	}
}
