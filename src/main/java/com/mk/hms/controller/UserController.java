package com.mk.hms.controller;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.hms.enums.ErrorCodeEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.Qrcode;
import com.mk.hms.model.User;
import com.mk.hms.service.UserService;
import com.mk.hms.utils.HmsRedisCacheUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.QrcodeUser;

/**
 * 用户控制器
 * @author hdy
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService = null;

	/**
	 * 注册用户
	 * @param phoneNum 用户手机号
	 * @param phoneYzm 验证码
	 * @param aPsd 
	 * @return 注册状态
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	@RequestMapping("/regUser")
	@ResponseBody
	public OutModel regUser(String phoneNum, String phoneYzm, String aPsd) throws NumberFormatException, IOException {
		OutModel out = new OutModel(false);
		// 数据完整性
		if (StringUtils.isBlank(phoneNum) || StringUtils.isBlank(phoneYzm) || StringUtils.isBlank(aPsd)) {
			out.setErrorCode(ErrorCodeEnum.ERROR_USER_1001.getValue());
			out.setErrorMsg(ErrorCodeEnum.ERROR_USER_1001.getText());
			return out;
		}
		// 验证手机号和验证码匹配
		if (!HmsRedisCacheUtils.verifyCodeIsValid(phoneNum, Integer.parseInt(phoneYzm))) {
			out.setErrorCode(ErrorCodeEnum.ERROR_USER_1002.getValue());
			out.setErrorMsg(ErrorCodeEnum.ERROR_USER_1002.getText());
			return out;
		}
		// 验证此用户是否已注册
		User user = this.getUserService().findUserByLoginName(phoneNum);
		if (null != user) {
			out.setErrorCode(ErrorCodeEnum.ERROR_USER_1003.getValue());
			out.setErrorMsg(ErrorCodeEnum.ERROR_USER_1003.getText());
			return out;
		}
		// 验证是否注册过该用户，如果注册过从新启用
		User register = this.getUserService().isRegistered(phoneNum);
		if (null != register) {
			this.getUserService().resetUser(register, aPsd);
		}
		SessionUtils.setRegUser(this.getUserService().regUser(phoneNum, aPsd));
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * 获取酒店用户二维码信息列表
	 * @return 酒店二维码信息列表
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/qrcodeUsers")
	@ResponseBody
	public List<QrcodeUser> finsHotelQrcodeUserList() throws SessionTimeOutException {
		return this.getUserService().finsHotelQrcodeUserList();
	}
	
	/**
	 * 修改用户密码
	 * @return 修改状态
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/modifyPwd")
	@ResponseBody
	public OutModel modifyUserPwd(String oldPwd, String newPwd, String modifyUserLoginName) throws SessionTimeOutException {
		return this.getUserService().modifyUserPwd(oldPwd, newPwd, modifyUserLoginName);
	}
	
	/**
	 * 发送手机验证码
	 * @return 发送状态
	 * @throws IOException 
	 */
	@RequestMapping("/verifyCode")
	@ResponseBody
	public OutModel sendVerifyCodeOfPhoneNo(String phoneNo) throws IOException {
		return this.getUserService().sendVerifyCodeOfPhoneNo(phoneNo);
	}
	
	/**
	 * 添加酒店用户信息
	 * 逻辑：1. 该用户是否已注册 
	 * 		2. 判断是否是店长，只有店长才可以添加用户
	 * 		3. 验证验证码
	 * 		4. 添加用户
	 * 		5. 添加角色
	 * 		6. 绑定二维码
	 * @return 添加返回状态
	 * @throws IOException 
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/addHotelUser")
	@ResponseBody
	public OutModel addHotelUser(String phoneNo, int verifyCode, String password, String name, String qrcodeTag) throws IOException, SessionTimeOutException {
		return this.getUserService().addHotelUser(phoneNo, verifyCode, password, name, qrcodeTag);
	}
	
	/**
	 * 获取可用二维码列表
	 * @return 可用二维码列表
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/qrcodesByUser")
	@ResponseBody
	public List<Qrcode> getAvailableQrcodefByUserList(String userLoginName) throws SessionTimeOutException {
		return this.getUserService().getAvailableQrcodeByUserLoginName(userLoginName);
	}
	
	/**
	 * 获取可用二维码列表
	 * @return 可用二维码列表
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/qrcodes")
	@ResponseBody
	public List<Qrcode> getAvailableQrcodeList() throws SessionTimeOutException {
		return this.getUserService().findAvailableQrcodesByHotelId();
	}
	
	/**
	 * 修改用户信息
	 * @return 修改状态
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public OutModel modifyUser(String name, boolean isUnbindFlag, String loginName, String qrcode) throws SessionTimeOutException {
		return this.getUserService().modifyUser(name, isUnbindFlag, loginName, qrcode);
	}
	
	/**
	 * 根据用户名，获取二维码对象
	 * @return 二维码对象
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/qrcodeByUserId")
	@ResponseBody
	public Qrcode findQrCodeByThisUserId() throws SessionTimeOutException {
		return this.getUserService().findQrCodeByThisUserId();
	}
	
	/**
	 * 解绑前台手机号码，只有酒店老板才可以操作
	 * @return 解绑状态model
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/unbindPhone")
	@ResponseBody
	public OutModel unbindMobilePhone(String phoneNo, String pwd, String tag) throws SessionTimeOutException {
		return this.getUserService().unbindMobilePhone(phoneNo, pwd, tag);
	}
	
	private UserService getUserService() {
		return userService;
	}
}
