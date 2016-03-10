package com.mk.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.mapper.PmsUserMapper;
import com.mk.hms.model.HmsMRoleUser;
import com.mk.hms.model.HmsMUserModel;
import com.mk.hms.model.PmsUserModel;

/**
 * 金盾用户service
 * @author hdy
 *
 */
@Service
@Transactional
public class PmsUserService {

	@Autowired
	private PmsUserMapper pmsUserMapper;
	
	/**
	 * 根据登录名查找用户
	 * @param loginName 登录名
	 * @return 查找对象
	 */
	public HmsMUserModel findPmsUserByLoginName(String loginName) {
		return pmsUserMapper.findPmsUserByLoginName(loginName);
	}
	
	/**
	 * 获取pms用户角色中间表对象
	 * @param userid 用户id
	 * @return 用户角色
	 */
	public HmsMRoleUser findPmsUserRoleByUserId(long userid) {
		return pmsUserMapper.findPmsUserRoleByUserId(userid);
	}
	
	/**
	 * 获取销售人员用户信息
	 * @return 销售人员信息列表
	 */
	public List<PmsUserModel> findPmsUserList() {
		return pmsUserMapper.findPmsUsers();
	}
	
	/**
	 * 获取pms用户信息
	 * @param phone 手机号
	 * @return pms用户信息
	 */
	public HmsMUserModel finPmsUserByPhone(String phone) {
		return pmsUserMapper.findPmsUserByPhone(phone);
	}
}
