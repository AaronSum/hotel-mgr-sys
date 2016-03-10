package com.mk.hms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 酒店t表
 * @author hdy
 *
 */
@Entity
@Table(name = "t_hotel")
public class HmsTHotelModel {

	private long id; // 主键
	private String hotelName; // 酒店名称
	private String hotelContactName; // 联系人姓名
	private Date regTime; // 注册时间
	private int disId; // 区县Id
	private String detailAddr; // 详细地址
	private double longitude; // 酒店经度
	private double latitude; // 酒店维度
	private Date openTime; // 开业时间
	private Date repairTime; // 最近装修时间
	private int roomNum; // 酒店房间数
	private String introduction; // 简介
	private String traffic; // 交通信息json格式字符串
	private String hotelpic; // 酒店图片json格式字符串
	private String peripheral; // 周边，json格式字符串
	private String businessLicenseFront; // 营业执照正面路径
	private String businessLicenseBack; // 营业执照反面路径
	private String pms; // PMS码
	private String visible; // T/F可见
	private String onLine; // 是否上线
	private String idCardFront; // 身份证正面
	private String idCardBack; // 身份证反面
	private String retentionTime; // 最晚保留时间
	private String defaultLeaveTime; // 默认离店时间
	private String needwait; // 需要等待
	private String isNewPms; // 是否是新PMS
	private String hotelphone; // 联系电话
	private int rulecode; //酒店规则字段，保持当前待审核的规则
	private String isThreshold;
	
	@Column(name = "isThreshold", length = 10)
	public String getIsThreshold() {
		return isThreshold;
	}
	
	public void setIsThreshold(String isThreshold) {
		this.isThreshold = isThreshold;
	}

	@Id
	@Column(name = "id", nullable = false, length = 20)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "hotelName", nullable = true, length = 50)
	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	@Column(name = "hotelContactName", nullable = true, length = 25)
	public String getHotelContactName() {
		return hotelContactName;
	}

	public void setHotelContactName(String hotelContactName) {
		this.hotelContactName = hotelContactName;
	}

	@Column(name = "regTime", nullable = true, length = 20)
	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	@Column(name = "disId", nullable = true, length = 11)
	public int getDisId() {
		return disId;
	}

	public void setDisId(int disId) {
		this.disId = disId;
	}

	@Column(name = "detailAddr", nullable = true, length = 100)
	public String getDetailAddr() {
		return detailAddr;
	}

	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}

	@Column(name = "longitude", nullable = true, length = 12)
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude", nullable = true, length = 12)
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "openTime", nullable = true, length = 20)
	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	@Column(name = "repairTime", nullable = true, length = 20)
	public Date getRepairTime() {
		return repairTime;
	}

	public void setRepairTime(Date repairTime) {
		this.repairTime = repairTime;
	}

	@Column(name = "roomNum", nullable = true, length = 1)
	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	@Column(name = "introduction", nullable = true)
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@Column(name = "traffic", nullable = true)
	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	@Column(name = "hotelpic", nullable = true)
	public String getHotelpic() {
		return hotelpic;
	}

	public void setHotelpic(String hotelpic) {
		this.hotelpic = hotelpic;
	}

	@Column(name = "peripheral", nullable = true)
	public String getPeripheral() {
		return peripheral;
	}

	public void setPeripheral(String peripheral) {
		this.peripheral = peripheral;
	}

	@Column(name = "businessLicenseFront", nullable = true, length= 100)
	public String getBusinessLicenseFront() {
		return businessLicenseFront;
	}

	public void setBusinessLicenseFront(String businessLicenseFront) {
		this.businessLicenseFront = businessLicenseFront;
	}

	@Column(name = "businessLicenseBack", nullable = true, length= 100)
	public String getBusinessLicenseBack() {
		return businessLicenseBack;
	}

	public void setBusinessLicenseBack(String businessLicenseBack) {
		this.businessLicenseBack = businessLicenseBack;
	}

	@Column(name = "pms", nullable = true, length= 50)
	public String getPms() {
		return pms;
	}

	public void setPms(String pms) {
		this.pms = pms;
	}
	
	@Column(name = "visible", nullable = true, length= 1)
	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	@Column(name = "idCardFront", nullable = true, length= 255)
	public String getIdCardFront() {
		return idCardFront;
	}

	public void setIdCardFront(String idCardFront) {
		this.idCardFront = idCardFront;
	}

	@Column(name = "idCardBack", nullable = true, length= 255)
	public String getIdCardBack() {
		return idCardBack;
	}

	public void setIdCardBack(String idCardBack) {
		this.idCardBack = idCardBack;
	}

	@Column(name = "retentionTime", nullable = true, length= 6)
	public String getRetentionTime() {
		return retentionTime;
	}

	public void setRetentionTime(String retentionTime) {
		this.retentionTime = retentionTime;
	}

	@Column(name = "defaultLeaveTime", nullable = true, length= 6)
	public String getDefaultLeaveTime() {
		return defaultLeaveTime;
	}

	public void setDefaultLeaveTime(String defaultLeaveTime) {
		this.defaultLeaveTime = defaultLeaveTime;
	}

	@Column(name = "onLine", nullable = true, length = 1)
	public String getOnLine() {
		return onLine;
	}

	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}

	@Column(name = "needwait", nullable = true, length = 1)
	public String getNeedwait() {
		return needwait;
	}

	public void setNeedwait(String needwait) {
		this.needwait = needwait;
	}

	@Column(name = "isNewPms", nullable = true, length = 1)
	public String getIsNewPms() {
		return isNewPms;
	}

	public void setIsNewPms(String isNewPms) {
		this.isNewPms = isNewPms;
	}

	@Column(name = "hotelphone", nullable = true, length = 30)
	public String getHotelphone() {
		return hotelphone;
	}

	public void setHotelphone(String hotelphone) {
		this.hotelphone = hotelphone;
	}
	
	@Column(name = "rulecode", nullable = true, length = 20)
	public int getRulecode() {
		return rulecode;
	}

	public void setRulecode(int rulecode) {
		this.rulecode = rulecode;
	}
}
