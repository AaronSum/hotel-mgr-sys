package com.mk.hms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.HmsUserModel;
import com.mk.hms.model.User;
import com.mk.hms.service.HmsUserService;
import com.mk.hms.service.UserService;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.LoginUser;

/**
 * 页面初始化跳转
 * @author hdy
 *
 */
@Controller
public class MainController {

	@Autowired
	private HmsUserService hmsUserService;
	@Autowired
	private UserService userService;
	
	/**
	 * 页面跳转
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/home/home")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView view = new ModelAndView("home/home");
		String h = request.getParameter("h");
		long thisHotelId = 0;
		if (StringUtils.isNotBlank(h) && !h.equals("0")) {
			thisHotelId = Long.parseLong(h);
		}
		LoginUser loginUser = new LoginUser();
		try {
			loginUser = SessionUtils.getSessionLoginUser();
		} catch (SessionTimeOutException e) {
			view.setViewName("home/index");
			return view;
		}
		String loginName = "";
		String password = "";
		if (null != loginUser) {
			User user = loginUser.getUser();
			loginName = user.getLoginname();
			password = user.getPsw();
			if (StringUtils.isNotBlank(loginName) && StringUtils.isNotBlank(password)) {
				HmsUserModel userModel = hmsUserService.findHmsUserByLoginName(loginName);
				if (password.equals(userModel.getPsw())) {
					view.addObject("user", loginUser.getUser());
					view.addObject("thisUser", JSONObject.fromObject(loginUser.getUser()).toString());
					view.addObject("hotels", JSONArray.fromObject(loginUser.getHotels()).toString());
					view.addObject("group", JSONObject.fromObject(loginUser.getGroup()).toString());
					EHotelWithBLOBs thisHotel = null;
					List<EHotelWithBLOBs> hotels = loginUser.getHotels();
					// 不存在酒店列表
					if (null == hotels || hotels.size() <= 0) {
						view.addObject("status", 2);
						view.addObject("context", "您没有注册酒店，请您先注册酒店信息！");
						view.setViewName("login/msg");
						return view;
					}
					if (thisHotelId == 0) {
						thisHotel = loginUser.getHotels().get(0);
					} else {
						for (EHotelWithBLOBs hotel : loginUser.getHotels()) {
							if (hotel.getId() == thisHotelId) {
								thisHotel = hotel;
								break;
							}
						}
					}
					//防止地址栏中输入hotel编号，默认选中第一个
					if (null == thisHotel) {
						thisHotel = loginUser.getHotels().get(0);
					}
					view.addObject("thisHotel", JSONObject.fromObject(thisHotel).toString());
					//获取t表ruleCode
					int ruleCode = userService.findRuleCode(thisHotel.getId());
					view.addObject("ruleCode", ruleCode);
					loginUser.setThisHotel(thisHotel);
				}
			}
		}
		SessionUtils.setSessionLoginUser(loginUser);
		return view;
	}
}
