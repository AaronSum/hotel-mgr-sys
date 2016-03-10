package com.mk.hms.view;

import java.math.BigDecimal;
import java.util.Date;

import com.mk.hms.utils.HmsDateUtils;

/**
 * 商圈酒店信息实体
 * 
 * @author hdy
 *
 */
public class BussinesszonesHotel {

	private String openTime;
	private String repairTime;
	private Date fmtOpenTime;
	private Date fmtRepairTime;
	private String begintime;
	private String endtime;
	private String hotelphone;
	private String introduction;
	private String hotelName;
	private int disId;
	private String detailAddr;
	private String circleList;
	private String subwayList;
	private String universityList;
	private BigDecimal longitude;
	private BigDecimal latitude;
	private String qtphone;
	private String pmsUser;
	private int hoteltype;

	private int provcode;
	private int citycode;
	private int discode;
	private int areacode;
	private String areaname;

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

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getHotelphone() {
		return hotelphone;
	}

	public void setHotelphone(String hotelphone) {
		this.hotelphone = hotelphone;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
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

	public String getCircleList() {
		return circleList;
	}

	public void setCircleList(String circleList) {
		this.circleList = circleList;
	}

	public String getSubwayList() {
		return subwayList;
	}

	public void setSubwayList(String subwayList) {
		this.subwayList = subwayList;
	}

	public String getUniversityList() {
		return universityList;
	}

	public void setUniversityList(String universityList) {
		this.universityList = universityList;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getQtphone() {
		return qtphone;
	}

	public void setQtphone(String qtphone) {
		this.qtphone = qtphone;
	}

	public int getHoteltype() {
		return hoteltype;
	}

	public void setHoteltype(int hoteltype) {
		this.hoteltype = hoteltype;
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

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getPmsUser() {
		return pmsUser;
	}

	public void setPmsUser(String pmsUser) {
		this.pmsUser = pmsUser;
	}

}
