package com.mk.hms.controller;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.MRoleUser;
import com.mk.hms.model.MUser;
import com.mk.hms.model.OutModel;
import com.mk.hms.service.UserService;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsFileUtils;
import com.mk.hms.utils.HmsStringUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.LoginJin;

/***
 * login 金盾
 * @author hdy
 *
 */
@Controller
@RequestMapping("/loginJin")
public class LoginJinController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginJinController.class);
	
	@Autowired
	private UserService userService = null;
	
	/**
	 * 登录
	 * @return 登录跳转页面对象
	 * @throws IOException 
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/main")
	public ModelAndView login() throws IOException {
		MUser mUser = null;
		try {
			mUser = SessionUtils.getSessionPmsUser();
		} catch (SessionTimeOutException e) {
			logger.debug("session无数据，用户需重新登录");
		}
		ModelAndView view = new ModelAndView();
		view.setViewName("login/login-jin");
		if (null != mUser) {
			view.setViewName("home/home-jin");
			view.addObject("user", mUser);
			view.addObject("thisUser", JSONObject.fromObject(mUser).toString());
			MRoleUser mRoleUser = this.getUserService().getMUserRole(mUser.getId());
			String pmsCheckUserRoleCode = "";
			pmsCheckUserRoleCode = HmsFileUtils.getSysContentItem(ContentUtils.PMS_CHECK_USER_ROLE_CODE);
			view.addObject("qiniuDownloadAddress", HmsFileUtils.getSysContentItem(ContentUtils.QINIU_DOWNLOAD_ADDRESS));
			view.addObject("isCheckUser", false);
			if (NumberUtils.isNumber(pmsCheckUserRoleCode) && null != mRoleUser) {
				if (mRoleUser.getRoleid() == Integer.parseInt(pmsCheckUserRoleCode)) {
					view.addObject("isCheckUser", true);
				}
			}
			view.addObject("isForeignPerson", this.getUserService().getForeignPersonRoleCode(mUser.getId()));
		}
		return view;
	}
	
	/**
	 * 登出
	 * @return 跳转登出页面
	 */
	/*@RequestMapping(name = "loginOut")
	public ModelAndView loginOut() {
		SessionUtils.setSessionPmsUser(null);
		ModelAndView view = new ModelAndView();
		view.setViewName("login/login-jin");
		return view;
	}*/
	
	/**
	 * 验证用户是否存在
	 * @return 验证对象
	 */
	@RequestMapping("/isExit")
	@ResponseBody
	public OutModel isExit(LoginJin loginJin) {
		OutModel out = new OutModel(false);
		String loginName = loginJin.getLoginName();
		// 登录名是否为空
		if (StringUtils.isBlank(loginName)) {
			out.setErrorMsg("请填写登录名");
			return out;
		}
		// 获取用户信息
		MUser mUser = this.getUserService().getMUserByLoginname(loginName);
		if (null == mUser) {
			out.setErrorMsg("用户信息不存在");
			return out;
		}
		// 判断密码
		String mUserPwd = mUser.getPsw().toUpperCase();
		String loginPwd = HmsStringUtils.encrypByMd5(loginJin.getPassword(), 32).toUpperCase();
		if (!mUserPwd.equals(loginPwd)) {
			out.setErrorMsg("密码错误");
			return out;
		}
		// 将用户放入登录信息
		SessionUtils.setSessionPmsUser(mUser);
		// 去除酒店登录session值
		SessionUtils.setSessionLoginUser(null);
		SessionUtils.setRegUser(null);
		out.setSuccess(true);
		return out;
	}

	private UserService getUserService() {
		return userService;
	}
}
