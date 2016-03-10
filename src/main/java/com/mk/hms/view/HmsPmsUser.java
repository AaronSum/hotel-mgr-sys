package com.mk.hms.view;

/**
 * pms接口返回数据model
 * @author hdy
 *
 */
public class HmsPmsUser {

	private String timestamp;
	private String uuid;
	private String function;
	private String userid;
	private String hotelid;
	private String ip;
	private String macaddress;
	private int usertype;
	private int pmstypeid;
	private String pmssourcetype;

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getHotelid() {
		return hotelid;
	}

	public void setHotelid(String hotelid) {
		this.hotelid = hotelid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMacaddress() {
		return macaddress;
	}

	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}

	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	public int getPmstypeid() {
		return pmstypeid;
	}

	public void setPmstypeid(int pmstypeid) {
		this.pmstypeid = pmstypeid;
	}

	public String getPmssourcetype() {
		return pmssourcetype;
	}

	public void setPmssourcetype(String pmssourcetype) {
		this.pmssourcetype = pmssourcetype;
	}
}
