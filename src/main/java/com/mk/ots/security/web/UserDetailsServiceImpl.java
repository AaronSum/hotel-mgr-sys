package com.mk.ots.security.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mk.hms.mapper.MUserMapper;
import com.mk.hms.model.MUser;
import com.mk.hms.model.MUserCriteria;
import com.mk.hms.service.HmsUserService;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private  HmsUserService hmsUserService = null;
	
	@Autowired
	private MUserMapper mUserMapper = null;
	/**管理端登录名后缀*/
	private final String pmsUserSuffix = "_pms";
		
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 管理端登录
		if (username.endsWith(pmsUserSuffix)) {
			MUserCriteria mUserCriteria = new MUserCriteria();
			mUserCriteria.createCriteria().andLoginnameEqualTo(username.substring(0, username.length() - 4));
			List<MUser> musers = this.getmUserMapper().selectByExample(mUserCriteria);
			return musers.size() > 0 ? musers.get(0) : null;
		}
		return this.getHmsUserService().findHmsUserByLoginName(username);
	}

	private  HmsUserService getHmsUserService() {
		return hmsUserService;
	}

	private MUserMapper getmUserMapper() {
		return mUserMapper;
	}
	
}
