package com.mk.hms.view;

/**
 * 解密token对象
 * @author hdy
 *
 */
public class HmsPmsToken {

	private String token;
	private String loginname;
	private String pms;
	private long times;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPms() {
		return pms;
	}

	public void setPms(String pms) {
		this.pms = pms;
	}

	public long getTimes() {
		return times;
	}

	public void setTimes(long times) {
		this.times = times;
	}
}