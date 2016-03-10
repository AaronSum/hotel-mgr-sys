package com.mk.hms.view;

/**
 * 登录用户
 * @author hdy
 *
 */
public class Login {

	private String loginname; // 登录名
	private String password; // 密码
	private long h; // 当前酒店编码切换
	private String token; // pms登录使用

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getH() {
		return h;
	}

	public void setH(long h) {
		this.h = h;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
