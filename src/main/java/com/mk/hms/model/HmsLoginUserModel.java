package com.mk.hms.model;

import java.io.Serializable;
import java.util.List;

/**
 * 登录用户信息
 * @author hdy
 *
 */
public class HmsLoginUserModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8350175840892507098L;
	
	private HmsUserModel user; // 用户信息
	private HmsGroupModel group; // 分组信息
	private List<HmsRoleModel> roles; // 角色列表
	private List<HmsEHotelModel> hotels; // 酒店列表
	private HmsEHotelModel thisHotel; //当前酒店信息

	public HmsUserModel getUser() {
		return user;
	}

	public void setUser(HmsUserModel user) {
		this.user = user;
	}

	public HmsGroupModel getGroup() {
		return group;
	}

	public void setGroup(HmsGroupModel group) {
		this.group = group;
	}

	public List<HmsRoleModel> getRoles() {
		return roles;
	}

	public void setRoles(List<HmsRoleModel> roles) {
		this.roles = roles;
	}

	public List<HmsEHotelModel> getHotels() {
		return hotels;
	}

	public void setHotels(List<HmsEHotelModel> hotels) {
		this.hotels = hotels;
	}

	public HmsEHotelModel getThisHotel() {
		return thisHotel;
	}

	public void setThisHotel(HmsEHotelModel thisHotel) {
		this.thisHotel = thisHotel;
	}
}
