package com.mk.hms.view;

import java.util.Date;

import com.mk.hms.utils.HmsDateUtils;

/**
 * 注册酒店实体
 * 
 * @author hdy
 *
 */
public class RegHotel {

	private String hotelName;
	private String hotelPhone;
	private String managerName;
	private String pmsCode;
	private int disId;
	private String detailAddr;
	private double longitude;
	private double latitude;

	private int provcode;
	private int citycode;
	private int discode;
	private int areacode;

	private String qtPhone;
	private String openTime;
	private String repairTime;
	private Date fmtOpenTime;
	private Date fmtRepairTime;
	private String introduction;
	private String areaname;

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelPhone() {
		return hotelPhone;
	}

	public void setHotelPhone(String hotelPhone) {
		this.hotelPhone = hotelPhone;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getPmsCode() {
		return pmsCode;
	}

	public void setPmsCode(String pmsCode) {
		this.pmsCode = pmsCode;
	}

	public int getDisId() {
		return disId;
	}

	public void setDisId(int disId) {
		this.disId = disId;
	}

	public String getDetailAddr() {
		return detailAddr;
	}

	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public int getProvcode() {
		return provcode;
	}

	public void setProvcode(int provcode) {
		this.provcode = provcode;
	}

	public int getCitycode() {
		return citycode;
	}

	public void setCitycode(int citycode) {
		this.citycode = citycode;
	}

	public int getDiscode() {
		return discode;
	}

	public void setDiscode(int discode) {
		this.discode = discode;
	}

	public int getAreacode() {
		return areacode;
	}

	public void setAreacode(int areacode) {
		this.areacode = areacode;
	}

	public String getQtPhone() {
		return qtPhone;
	}

	public void setQtPhone(String qtPhone) {
		this.qtPhone = qtPhone;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getRepairTime() {
		return repairTime;
	}

	public void setRepairTime(String repairTime) {
		this.repairTime = repairTime;
	}

	public Date getFmtOpenTime() {
		return HmsDateUtils.getDateFromString(openTime,
				HmsDateUtils.FORMAT_DATE);
	}

	public Date getFmtRepairTime() {
		return HmsDateUtils.getDateFromString(repairTime,
				HmsDateUtils.FORMAT_DATE);
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

}
