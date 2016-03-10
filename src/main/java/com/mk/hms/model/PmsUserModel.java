package com.mk.hms.model;

import javax.persistence.Column;

/**
 * 销售用户输出视图
 * 
 * @author hdy
 *
 */
public class PmsUserModel {

	private String loginname;
	private String name;

	@Column(name = "loginname")
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
