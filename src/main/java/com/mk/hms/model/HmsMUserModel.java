package com.mk.hms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * pms 审核用户
 * @author hdy
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "m_user")
public class HmsMUserModel implements Serializable{

	private long id;
	private String name;
	private String loginname;
	private String psw;
	private String phone;
	private String email;
	private String begintime;
	private String endtime;
	private String regtime;
	private int errorlogin;
	private String nextchangepswtime;
	private String nextchangepswreasion;
	private int status;
	private String errorlogintime;
	private String sys;
	private String isIdap;
	private String synid;

	@Id
	@Column(name = "id", nullable = false, length = 20)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "name", nullable = true, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "loginname", nullable = true, length = 50)
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "psw", nullable = true, length = 32)
	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	@Column(name = "phone", nullable = true, length = 50)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "email", nullable = true, length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "begintime", nullable = true, length = 14)
	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	@Column(name = "endtime", nullable = true, length = 14)
	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	@Column(name = "regtime", nullable = true, length = 14)
	public String getRegtime() {
		return regtime;
	}

	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}

	@Column(name = "errorlogin", nullable = true, length = 11)
	public int getErrorlogin() {
		return errorlogin;
	}

	public void setErrorlogin(int errorlogin) {
		this.errorlogin = errorlogin;
	}

	@Column(name = "nextchangepswtime", nullable = true, length = 14)
	public String getNextchangepswtime() {
		return nextchangepswtime;
	}

	public void setNextchangepswtime(String nextchangepswtime) {
		this.nextchangepswtime = nextchangepswtime;
	}

	@Column(name = "nextchangepswreasion", nullable = true, length = 100)
	public String getNextchangepswreasion() {
		return nextchangepswreasion;
	}

	public void setNextchangepswreasion(String nextchangepswreasion) {
		this.nextchangepswreasion = nextchangepswreasion;
	}

	@Column(name = "status", nullable = true, length = 11)
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "errorlogintime", nullable = true, length = 14)
	public String getErrorlogintime() {
		return errorlogintime;
	}

	public void setErrorlogintime(String errorlogintime) {
		this.errorlogintime = errorlogintime;
	}

	@Column(name = "sys", nullable = true, length = 1)
	public String getSys() {
		return sys;
	}

	public void setSys(String sys) {
		this.sys = sys;
	}

	@Column(name = "isIdap", nullable = true, length = 1)
	public String getIsIdap() {
		return isIdap;
	}

	public void setIsIdap(String isIdap) {
		this.isIdap = isIdap;
	}

	@Column(name = "synid", nullable = true, length = 50)
	public String getSynid() {
		return synid;
	}

	public void setSynid(String synid) {
		this.synid = synid;
	}
}
