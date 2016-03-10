package com.mk.hms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 销售用户信息
 * 
 * @author qhdhiman
 */
@Entity
@Table(name = "sy_org_user")
public class HmsSaleUserModel {

	private String userCode; // 用户编码
	private String userName; // 用户姓名
	private String userMobile; // 手机号
	private long hotelId; //酒店编码
	
	@Id
	@Column(name = "userCode", nullable = false, length = 40)
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	@Column(name = "userName", nullable = true, length = 40)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "userMobile", nullable = true, length = 40)
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	@Column(name = "hotelId", nullable = false, length = 40)
	public long getHotelId() {
		return hotelId;
	}
	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

}
