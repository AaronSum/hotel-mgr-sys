package com.mk.hms.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.OutModel;
import com.mk.hms.service.HmsUserService;
import com.mk.hms.service.UserService;
import com.mk.hms.view.Login;

/***
 * login
 * @author hdy
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private HmsUserService hmsUserService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * login
	 * @return home.jsp
	 * @throws IOException 
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/main")
	public ModelAndView login() throws IOException{
		return this.getUserService().login(0);
	}
	
	/**
	 * login
	 * @return home.jsp
	 * @throws IOException 
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/main/{hotelId}")
	public ModelAndView changeHotel(@PathVariable long hotelId) throws IOException {
		return this.getUserService().login(hotelId);
	}
	
	/**
	 * 查看是否存在该用户
	 * @return 用户信息
	 */
	@RequestMapping("/isExit")
	@ResponseBody
	public OutModel isExitLoginUser(Login login, HttpServletRequest request) {
		return this.getUserService().isExitLoginUser(login);
	}
	
	/**
	 * 登录超时提醒实现
	 * @param isLogin 参数
	 * @return 超时页面
	 */
	@RequestMapping("/msg/{status}")
	public ModelAndView loginMsg(@PathVariable int status, HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		if (status == 1) { //登录超时或连接禁止直接访问
			view.addObject("status", 1);
			view.addObject("context", "系统超时或访问受限，将返回登录页面……");
		}
		view.setViewName("login/msg");
		return view;
	}

	private UserService getUserService() {
		return userService;
	}
}
