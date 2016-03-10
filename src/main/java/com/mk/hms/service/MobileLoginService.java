package com.mk.hms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.enums.HmsVisibleEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.EHotelMapper;
import com.mk.hms.mapper.GroupHotelMapper;
import com.mk.hms.mapper.GroupMapper;
import com.mk.hms.mapper.UserMapper;
import com.mk.hms.model.EHotelCriteria;
import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.Group;
import com.mk.hms.model.GroupHotel;
import com.mk.hms.model.GroupHotelCriteria;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.User;
import com.mk.hms.model.UserCriteria;
import com.mk.hms.utils.HmsStringUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.Login;
import com.mk.hms.view.LoginUser;

/**
 * 酒店用户信息
 * @author hdy
 */
@Service
@Transactional
public class MobileLoginService{

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private GroupMapper groupMapper;
	
	@Autowired
	private EHotelMapper ehotelMapper;
	
	@Autowired
	private GroupHotelMapper groupHotelMapper;
	
	/**
	 * 用户登录
	 * @param login 登录对象
	 * @return 输出对象
	 * @throws SessionTimeOutException 
	 */
	public OutModel login(Login login) throws SessionTimeOutException {
		OutModel out = new OutModel(false);
		out.setErrorMsg("用户名或密码错误");
		// 判空
		String loginName = login.getLoginname();
		String password = login.getPassword();
		User userInfo = null;
		if (StringUtils.isNoneBlank(loginName) && StringUtils.isNoneBlank(password)) {
			// 获取用户信息
			userInfo = getLoginUser(loginName, password);
			if (null == userInfo) {
				return out;
			}
			// 判断是否为老板用户
			Group groupInfo = this.getGroupMapper().selectByPrimaryKey(userInfo.getGroupid());
			if (null == groupInfo) {
				out.setErrorMsg("此用户不为酒店老板角色");
				return out;
			}
			this.resetOutMsg(out, userInfo, groupInfo, login);
		}
		return out;
	}

	/**
	 * 切换酒店
	 * @param hotelId 酒店id
	 * @return 输出对象
	 * @throws SessionTimeOutException 
	 */
	public OutModel changeHotel(long hotelId) throws SessionTimeOutException {
		EHotelWithBLOBs thisHotel = null;
		LoginUser loginUser = SessionUtils.getSessionLoginUser();
		List<EHotelWithBLOBs> hotelList = loginUser.getHotels();
		// 查看当前酒店是否属于当前老板
		for (EHotelWithBLOBs e : hotelList) {
			if (hotelId == e.getId().longValue()) {
				thisHotel = this.getEhotelMapper().selectByPrimaryKey(hotelId);
				break;
			}
		}
		OutModel out = new OutModel(false);
		if (null == thisHotel) {
			out.setErrorMsg("此酒店不属于当前老板");
			return out;
		}
		loginUser.setThisHotel(thisHotel);
		SessionUtils.setSessionLoginUser(loginUser);
		Map<String, Object> attr = new HashMap<String, Object>();
		attr.put("thisHotel", thisHotel);
		out.setAttribute(attr);
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * 重置返回信息
	 * @param out 返回信息
	 * @param userInfo 用户对象
	 * @param groupInfo 分组对象
	 * @param login 参数对象
	 */
	private void resetOutMsg(OutModel out, User userInfo, Group groupInfo, Login login) {
		// 获取酒店列表
		List<EHotelWithBLOBs> hotelList = this.getHotelList2Out(out, userInfo, groupInfo, login);
		out.setSuccess(true);
		out.setErrorMsg(null);
		// 重置session用户信息
		LoginUser loginUser = new LoginUser();
		loginUser.setGroup(groupInfo);
		loginUser.setHotels(hotelList);
		loginUser.setUser(userInfo);
		Map<String, Object> attr = (Map<String, Object>) out.getAttribute();
		loginUser.setThisHotel((EHotelWithBLOBs) attr.get("thisHotel"));
		SessionUtils.setSessionLoginUser(loginUser);
	}
	
	/**
	 * 获取酒店列表
	 * @param out 输出对象
	 * @param userInfo 用户对象
	 * @param groupInfo 分组对象
	 * @param login 传入对象
	 * @param thisHotel 当前酒店
	 * @return List<EHotel> 酒店信息列表
	 */
	private List<EHotelWithBLOBs> getHotelList2Out(OutModel out, User userInfo, Group groupInfo, Login login) {
		// 获取用户酒店列表
		GroupHotelCriteria groupHotel = new GroupHotelCriteria();
		groupHotel.createCriteria().andGroupidEqualTo(groupInfo.getId());
		List<GroupHotel> list = this.getGroupHotelMapper().selectByExample(groupHotel);
		List<Long> hotelids = new ArrayList<Long>();
		for (GroupHotel gh : list) {
			hotelids.add(gh.getHotelid());
		}
		List<EHotelWithBLOBs> hotelList = null;
		if (hotelids.size() > 0) {
			EHotelCriteria ehotel = new EHotelCriteria();
			ehotel.createCriteria().andIdIn(hotelids);
			hotelList = this.getEhotelMapper().selectByExampleWithBLOBs(ehotel);
			Map<String, Object> attr = new HashMap<String, Object>();
			attr.put("user", userInfo);
			attr.put("hotelList", hotelList);
			this.getThisHotel(login, hotelList, attr);
			out.setAttribute(attr);
		}
		return hotelList;
	}

	/**
	 * 获取当前酒店
	 * @param login 参数对象
	 * @param thisHotel 当前酒店对象
	 * @param hotelList 酒店列表
	 * @param attr 输出对象
	 */
	private void getThisHotel(Login login,
			List<EHotelWithBLOBs> hotelList, Map<String, Object> attr) {
		EHotelWithBLOBs thisHotel = null;
		for (EHotelWithBLOBs e : hotelList) {
			if (e.getId().longValue() == login.getH()) {
				thisHotel = e;
				break;
			}
		}
		// 没有当前酒店选择第一个酒店为当前酒店
		if (null == thisHotel) {
			thisHotel = hotelList.size() > 0 ? hotelList.get(0) : null;
		}
		attr.put("thisHotel", thisHotel);
	}

	/**
	 * 获取登录用户
	 * @param loginName 登录名
	 * @param password 密码
	 * @return 登录用户对象
	 */
	private User getLoginUser(String loginName, String password) {
		// 加密密码
		password = HmsStringUtils.encrypByMd5(password, 32);
		// 判断用户信息是否存在
		UserCriteria user = new UserCriteria();
		user.createCriteria().andLoginnameEqualTo(loginName)
			.andVisibleEqualTo(HmsVisibleEnum.T.getValue())
			.andPswEqualTo(password);
		List<User> list = this.getUserMapper().selectByExample(user);
		return list.size() > 0 ? list.get(0) : null;
	}

	private UserMapper getUserMapper() {
		return userMapper;
	}

	private GroupMapper getGroupMapper() {
		return groupMapper;
	}

	private EHotelMapper getEhotelMapper() {
		return ehotelMapper;
	}

	private GroupHotelMapper getGroupHotelMapper() {
		return groupHotelMapper;
	}
	
}
