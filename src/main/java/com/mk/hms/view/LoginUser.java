package com.mk.hms.view;

import java.io.Serializable;
import java.util.List;

import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.Group;
import com.mk.hms.model.Role;
import com.mk.hms.model.User;

/**
 * 登录用户信息
 * @author hdy
 *
 */
public class LoginUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8350175840892507098L;
	
	private User user; // 用户信息
	private Group group; // 分组信息
	private List<Role> roles; // 角色列表
	private List<EHotelWithBLOBs> hotels; // 酒店列表
	private EHotelWithBLOBs thisHotel; //当前酒店信息

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<EHotelWithBLOBs> getHotels() {
		return hotels;
	}

	public void setHotels(List<EHotelWithBLOBs> hotels) {
		this.hotels = hotels;
	}

	public EHotelWithBLOBs getThisHotel() {
		return thisHotel;
	}

	public void setThisHotel(EHotelWithBLOBs thisHotel) {
		this.thisHotel = thisHotel;
	}
}
