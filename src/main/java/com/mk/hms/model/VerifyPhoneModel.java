package com.mk.hms.model;

import java.io.Serializable;

/**
 * 手机验证码缓存对象
 * 
 * @author hdy
 *
 */
@SuppressWarnings("serial")
public class VerifyPhoneModel implements Serializable{

	private String phnoeNo;
	private int verifyCode;

	public String getPhnoeNo() {
		return phnoeNo;
	}

	public void setPhnoeNo(String phnoeNo) {
		this.phnoeNo = phnoeNo;
	}

	public int getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(int verifyCode) {
		this.verifyCode = verifyCode;
	}
}
