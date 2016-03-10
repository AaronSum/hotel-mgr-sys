package com.mk.hms.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 客单实体
 * @author hdy
 *
 */
@Entity
public class OtaCheckinRoomOrderModel {

	private long otaOrderId; // 订单主键
	private String roomTypeName; // 房间类型名称
	private long hotelId; // 酒店主键
	private String hotelName; // 酒店名称
	private String roomNo; // 房间名称
	private String contacts; // 联系人
	private String contactsPhone; // 联系人电话
	private double totalPrice; // 总房价
	private int orderStatus; // 订单状态
	private Date begintime; // 抵达时间
	private Date endtime; // 离店时间
	private Date creattime; // 创建时间
	private String orderStatusName; // 订单状态文字表述
	private long spreadUser; //前台切客用户	
	private int invalidreason;//无效切客订单原因
	private float allcost;  //预付
	private float realotagive;//到付
	private int ordertype;//订单状态
	private int rulecode;
	private BigDecimal qiekeIncome;// 补贴金额 

	@Column(name = "OtaOrderId", nullable = true, length = 20)
	public long getOtaOrderId() {
		return otaOrderId;
	}

	public void setOtaOrderId(long otaOrderId) {
		this.otaOrderId = otaOrderId;
	}
	
	@Column(name = "RoomTypeName", nullable = true, length = 100)
	public String getRoomTypeName() {
		return roomTypeName;
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}
	
	@Column(name = "Hotelid", nullable = true, length = 20)
	public long getHotelId() {
		return hotelId;
	}

	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	@Column(name = "Hotelname", nullable = true, length = 100)
	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	@Column(name = "RoomNo", nullable = true, length = 50)
	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	
	@Column(name = "TotalPrice", nullable = true, length = 8)
	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@Column(name = "OrderStatus", nullable = true, length = 8)
	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(name = "Contacts")
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@Column(name = "ContactsPhone")
	public String getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}

	public Date getBegintime() {
		return begintime;
	}
	
	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Date getCreattime() {
		return creattime;
	}

	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public long getSpreadUser() {
		return spreadUser;
	}

	public void setSpreadUser(long spreadUser) {
		this.spreadUser = spreadUser;
	}

	public float getAllcost() {
		return allcost;
	}

	public void setAllcost(float allcost) {
		this.allcost = allcost;
	}

	public float getRealotagive() {
		return realotagive;
	}

	public void setRealotagive(float realotagive) {
		this.realotagive = realotagive;
	}

	@Column(name = "Invalidreason", nullable = true, length = 1)
	public int getInvalidreason() {
		return invalidreason;
	}

	public void setInvalidreason(int invalidreason) {
		this.invalidreason = invalidreason;
	}

	public int getOrdertype() {
		return ordertype;
	}

	@Column(name = "Ordertype", nullable = true, length = 11)
	public void setOrdertype(int ordertype) {
		this.ordertype = ordertype;
	}

	public int getRulecode() {
		return rulecode;
	}

	@Column(name = "rulecode", nullable = true, length = 11)
	public void setRulecode(int rulecode) {
		this.rulecode = rulecode;
	}

	public BigDecimal getQiekeIncome() {
		return qiekeIncome;
	}
	
	@Column(name = "qiekeIncome", nullable = true, length = 10)
	public void setQiekeIncome(BigDecimal qiekeIncome) {
		this.qiekeIncome = qiekeIncome;
	}
	
}
