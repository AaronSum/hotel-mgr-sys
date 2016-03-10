package com.mk.hms.view;


/**
 * 当前登录用户 因为不知道是酒店登录还是客服登录，所以此处封装一个通用pojo
 * @author hdy
 *
 */
public class SessionUser {

	private Long id;
	private String loginname;
	private String psw;
	private Long groupid;
	private String name;
	private String visible;
	private Object begintime;
	private Object endtime;

	private String phone;
	private String email;
	private String regtime;
	private Integer errorlogin;
	private String nextchangepswtime;
	private String nextchangepswreasion;
	private Integer status;
	private String errorlogintime;
	private String sys;
	private String isidap;
	private String synid;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public Long getGroupid() {
		return groupid;
	}

	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public Object getBegintime() {
		return begintime;
	}

	public void setBegintime(Object begintime) {
		this.begintime = begintime;
	}

	public Object getEndtime() {
		return endtime;
	}

	public void setEndtime(Object endtime) {
		this.endtime = endtime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegtime() {
		return regtime;
	}

	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}

	public Integer getErrorlogin() {
		return errorlogin;
	}

	public void setErrorlogin(Integer errorlogin) {
		this.errorlogin = errorlogin;
	}

	public String getNextchangepswtime() {
		return nextchangepswtime;
	}

	public void setNextchangepswtime(String nextchangepswtime) {
		this.nextchangepswtime = nextchangepswtime;
	}

	public String getNextchangepswreasion() {
		return nextchangepswreasion;
	}

	public void setNextchangepswreasion(String nextchangepswreasion) {
		this.nextchangepswreasion = nextchangepswreasion;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrorlogintime() {
		return errorlogintime;
	}

	public void setErrorlogintime(String errorlogintime) {
		this.errorlogintime = errorlogintime;
	}

	public String getSys() {
		return sys;
	}

	public void setSys(String sys) {
		this.sys = sys;
	}

	public String getIsidap() {
		return isidap;
	}

	public void setIsidap(String isidap) {
		this.isidap = isidap;
	}

	public String getSynid() {
		return synid;
	}

	public void setSynid(String synid) {
		this.synid = synid;
	}
}
