package com.mk.hms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.OutModel;
import com.mk.hms.service.MobileLoginService;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.Login;
import com.mk.hms.view.LoginUser;

/***
 * mobile login
 * @author hdy
 *
 */
@Controller
@RequestMapping("/mobile")
public class MobileLoginController {

	@Autowired
	private MobileLoginService mobileLoginService;
	
	
	/**
	 * 切换酒店
	 * @param hotelId 酒店编码
	 * @throws SessionTimeOutException 异常
	 * @return 输出对象 
	 */
	@RequestMapping("/login/{hotelId}")
	@ResponseBody
	public OutModel changeHotel(@PathVariable long hotelId) throws SessionTimeOutException {
		return this.getMobileLoginService().changeHotel(hotelId);
	}
	
	/**
	 * 用户登录
	 * @return 用户信息
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/login")
	@ResponseBody
	public OutModel isExitLoginUser(Login login) throws SessionTimeOutException {
		return this.getMobileLoginService().login(login);
	}
	
	/**
	 * 加载登录数据
	 * @return 输出对象
	 * @throws SessionTimeOutException 异常
	 */
	@RequestMapping("/reloadcontext")
	@ResponseBody
	public OutModel reloadContextData() throws SessionTimeOutException {	
		LoginUser loginUser = SessionUtils.getSessionLoginUser();
		OutModel out = new OutModel(true);
		Map<String, Object> attr = new HashMap<String, Object>();
		attr.put("user", loginUser.getUser());
		attr.put("hotelList", loginUser.getHotels());
		attr.put("thisHotel", loginUser.getThisHotel());
		out.setAttribute(attr);
		return out;
	}

	private MobileLoginService getMobileLoginService() {
		return mobileLoginService;
	}
}
