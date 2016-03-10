package com.mk.hms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.mk.hms.mapper.EHotelMapper;
import com.mk.hms.mapper.RoleMapper;
import com.mk.hms.mapper.UserMapper;
import com.mk.hms.mapper.UserRoleMapper;
import com.mk.hms.model.EHotel;
import com.mk.hms.model.EHotelCriteria;
import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.Role;
import com.mk.hms.model.RoleCriteria;
import com.mk.hms.model.User;
import com.mk.hms.model.UserCriteria;
import com.mk.hms.model.UserRole;
import com.mk.hms.model.UserRoleCriteria;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.DESUtils;
import com.mk.hms.utils.HmsFileUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.HmsPmsToken;
import com.mk.hms.view.HmsPmsUser;

@Service
@Transactional
public class Hms4PmsService {
	
	private static final Logger logger = LoggerFactory.getLogger(Hms4PmsService.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private EHotelMapper eHotelMapper;
	
	/**
	 * 获取token
	 * @param userId 用户id
	 * @param hotelId 酒店id
	 * @param userName 用户登录名
	 * @param userType 用户类型1，老板；2，前台
	 * @return 输出对象
	 * @throws Exception 
	 */
	public OutModel getToken(String json, HttpServletRequest request) {
		OutModel out = new OutModel(false);
		String hostAddress = "";
		// 添加token
		Map<String, Object> attr = new HashMap<String, Object>();
		try{
			hostAddress = "http://" + HmsFileUtils.getSysContentItem(ContentUtils.HOST_ADDRESS)
					+ request.getContextPath() + "/hms4pms/login/error/";
			if (StringUtils.isBlank(json)) {
				out.setErrorMsg("传入参数不可为空");
				attr.put("rtnUrl", hostAddress + "传入参数不可为空");
				out.setAttribute(attr);
				return out;
			}
			HmsPmsUser hpu = this.parseJsonParams(json);
			String pms = hpu.getHotelid();
			String loginname = hpu.getUserid();
			int userType = hpu.getUsertype();
			// 获取数据
			if (StringUtils.isNotBlank(pms) && StringUtils.isNotBlank(loginname) && userType > 0) {
				// 判断是否存在与白名单中
	 			User u = this.checkHmsPmsWhiteList(hpu);
				if (null != u) {
					if (StringUtils.isNoneBlank(u.getLoginname())) {
						hpu.setUserid(u.getLoginname());
						String token = this.encryptionTokenByDES(hpu);
						if (StringUtils.isNoneBlank(token)) {
							attr.put("rtnUrl", "http://" + HmsFileUtils.getSysContentItem(ContentUtils.HOST_ADDRESS) 
									+ request.getContextPath() + "/hms4pms/login/" + token);
							out.setAttribute(attr);
							out.setSuccess(true);
							return out;
						}
						out.setErrorMsg("token生成异常");
						attr.put("rtnUrl", hostAddress + "token生成异常");
						out.setAttribute(attr);
						return out;
					}
					out.setErrorMsg("pms传递用户类型为[" + hpu.getUsertype() + "]的用户信息hms中不存在。请在hms中创建相应类型用户信息之后再登录。");
					attr.put("rtnUrl", hostAddress + "pms传递用户类型为:[" + hpu.getUsertype() + "]的用户信息hms中不存在。请在hms中创建相应类型用户信息之后再登录。");
					out.setAttribute(attr);
					return out;
				}
				out.setErrorMsg("获取pms数据信息与hms酒店信息不匹配。请确认pms是否同步酒店数据到hms。");
				attr.put("rtnUrl", hostAddress + "获取pms数据信息与hms酒店信息不匹配。请确认pms是否同步酒店数据到hms。");
				out.setAttribute(attr);
				return out;
			}
			out.setErrorMsg("登录名或pms编码为空");
			attr.put("rtnUrl", hostAddress + "登录名或pms编码为空");
			out.setAttribute(attr);
			return out;
		}catch(IOException e) {
			logger.error("获取主机地址失败。", e);
			out.setErrorMsg("获取主机地址失败");
			attr.put("rtnUrl", hostAddress + "获取主机地址失败");
			out.setAttribute(attr);
			return out;
		}
	}
	
	/**
	 * 解密token
	 * @param token token字符串
	 * @return 页面信息
	 */
	public ModelAndView loginHms4Pms(String token) {
		ModelAndView mav = new ModelAndView("hms4pms/login");
		mav.addObject("visible", false);
		// 解析token
		HmsPmsToken hpt = DESUtils.decryptTokenByDES(token);
		// 判断时间戳有效性
		if (null != hpt && new Date().getTime() - hpt.getTimes() < DESUtils.timeoutTimestamp) { //有效
			mav.addObject("visible", true);
			mav.addObject("loginname", hpt.getLoginname());
			mav.addObject("token", hpt.getToken());
			EHotelCriteria eh = new EHotelCriteria();
			eh.createCriteria().andPmsEqualTo(hpt.getPms());
			List<EHotelWithBLOBs> list = this.getEHotelMapper().selectByExampleWithBLOBs(eh);
			SessionUtils.setTokenHotel(list.size() > 0 ? list.get(0) : null);
		}
		return mav;
	}
	
	/**
	 * 错误页面跳转
	 * @param msg 错误信息
	 * @return 页面信息
	 */
	public ModelAndView loginError(String msg) {
		ModelAndView mav = new ModelAndView("hms4pms/login");
		mav.addObject("visible", false);
		mav.addObject("msg", msg);
		return mav;
	}
	
	/**
	 * 解析参数字符串
	 * @param json 参数字符串
	 * @return HmsPmsUser 对象
	 */
	private HmsPmsUser parseJsonParams(String json) {
		HmsPmsUser hpu = new HmsPmsUser();
		hpu = (HmsPmsUser) JSONObject.toBean(JSONObject.fromObject(json), HmsPmsUser.class);
		return hpu;
	}
	
	/**
	 * 加密token
	 * @param id 用户id
	 * @param hotelId 酒店编码
	 * @param userType 用户类型
	 * @return token字符串
	 * @throws Exception 异常
	 */
	private String encryptionTokenByDES (HmsPmsUser hmsPmsUser) {
		String dataString = hmsPmsUser.getUserid() + ContentUtils.CHAR_COMMA 
				+ hmsPmsUser.getHotelid() + ContentUtils.CHAR_COMMA + new Date().getTime();
		String desCryptoString = "";
		try {
			desCryptoString = DESUtils.desCrypto(dataString);
		} catch (Exception e) {
			logger.error("encryptionTokenByDES(DES加密). get token error:" + e.getMessage(), e);
			e.printStackTrace();
		}
		return desCryptoString;
	}
	
	/**
	 * 校验hms/pms白名单
	 * @param userId 用户id
	 * @param hotelId 酒店id
	 * @param userName 登录名
	 *  @param userType 用户类型1，老板；2，前台
	 * @return 白名单
	 */
	private User checkHmsPmsWhiteList(HmsPmsUser hmsPms) {
		int userType = hmsPms.getUsertype();
		String pms = hmsPms.getHotelid();
		return this.getUserInfoByHotelId(pms, userType);
	}

	/**
	 * 根据酒店id获取用户信息
	 * @param hotelId 酒店id
	 * @param userType 用户类型
	 * @return 用户信息
	 */
	private User getUserInfoByHotelId(String pms, int userType) {
		// 根据pms获取酒店信息
		EHotelCriteria eh = new EHotelCriteria();
		eh.createCriteria().andPmsEqualTo(pms);
		List<EHotel> ehList = this.getEHotelMapper().selectByExample(eh);
		EHotel e = ehList.size() > 0 ? ehList.get(0) : null;
		if (null == e) {
			return null;
		}
		// 根据酒店id获取角色对象
		RoleCriteria r = new RoleCriteria();
		r.createCriteria().andHotelidEqualTo(e.getId()).andTypeEqualTo(userType);
		List<Role> list = this.getRoleMapper().selectByExample(r);
		Role rInfo = list.size() > 0 ? list.get(0) : null;
		// 获取用户角色
		if (null == rInfo) {
			return new User();
		}
		UserRoleCriteria ur = new UserRoleCriteria();
		ur.createCriteria().andRoleidEqualTo(rInfo.getId());
		List<UserRole> urList = this.getUserRoleMapper().selectByExample(ur);
		if (urList.size() <= 0) {
			return new User();
		}
		return this.getUserList(urList);
	}

	/**
	 * 获取用户列表
	 * @param urList 用户id列表
	 * @return 用户列表
	 */
	private User getUserList(List<UserRole> urList) {
		List<Long> userids = new ArrayList<Long>();
		for (UserRole rr : urList) {
			userids.add(rr.getUserid());
		}
		if (userids.size() <= 0) {
			return new User();
		}
		UserCriteria uc = new UserCriteria();
		uc.createCriteria().andIdIn(userids);
		List<User> userList = this.getUserMapper().selectByExample(uc);
		// 获取用户信息
		return userList.size() > 0 ? userList.get(0) : new User();
	}
	
	private UserMapper getUserMapper() {
		return userMapper;
	}
	
	private UserRoleMapper getUserRoleMapper() {
		return userRoleMapper;
	}
	
	private RoleMapper getRoleMapper() {
		return roleMapper;
	}
	
	private EHotelMapper getEHotelMapper() {
		return eHotelMapper;
	}
}
