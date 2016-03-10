package com.mk.hms.view;

import com.mk.hms.model.HotelScore;

/**
 * 评论扩展累
 * @author hdy
 *
 */
public class HotelScoreWithLoginName extends HotelScore {

	private String loginName;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
}
