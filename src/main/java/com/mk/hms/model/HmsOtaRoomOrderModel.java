package com.mk.hms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 客单实体
 * @author hdy
 *
 */
@Entity
@Table(name = "b_otaroomorder")
public class HmsOtaRoomOrderModel {

	private long id; // 主键
	private long otaOrderId; // 订单主键
	private long linkRoomOrderId; // 关联客单号
	private long pmsRoomOrderId; // 本系统PMS客单表ID
	private String pmsRoomOrderNo; // Pms系统中客单编号
	private long hotelId; // 酒店主键
	private String hotelName; // 酒店名称
	private String hotelPms; // PMS编码
	private int orderMethod; // 预定方式
	private int orderType; // 订单类型
	private int priceType; // 价格类型
	private Date begintime; // 预计抵店时间
	private Date endtime; // 预计离店时间
	private long roomTypeId; // 房型ID
	private String roomTypeName; // 房型名称
	private String roomTypePms; // PMS房型编码
	private long roomId; // 房间ID
	private String roomNo; // 房间名称
	private String roomPms; // PMS房间编码
	private long mid; // 订单所属会员id
	private long mLevel; // 会员等级id
	private String promotion; // 是否促销
	private String coupon; // 是否使用优惠券
	private String promotionNo; // 促销代码
	private double totalPrice; // 总房价
	private double price; // 房价
	private int breakfastNum; // 早餐数
	private String contacts; // 联系人
	private String contactsPhone; // 联系电话
	private String contactsEmail; // 联系邮箱
	private String contactsWeixin; // 联系微信
	private String note; // 备注
	private int orderStatus; // 订单状态
	private String fmOrderStatus; // 订单状态文字表述
	private int paystatus; // 支付状态
	private Date paytime; // 支付时间
	private String receipt; // 是否需要发票
	private String reeceiptTitle; // 发票抬头
	private Date updatetime; // 修改时间

	@Id
	@Column(name = "id", nullable = false, length = 20)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "OtaOrderId", nullable = true, length = 20)
	public long getOtaOrderId() {
		return otaOrderId;
	}

	public void setOtaOrderId(long otaOrderId) {
		this.otaOrderId = otaOrderId;
	}

	@Column(name = "LinkRoomOrderId", nullable = true, length = 20)
	public long getLinkRoomOrderId() {
		return linkRoomOrderId;
	}

	public void setLinkRoomOrderId(long linkRoomOrderId) {
		this.linkRoomOrderId = linkRoomOrderId;
	}

	@Column(name = "PMSRoomOrderId", nullable = true, length = 20)
	public long getpMSRoomOrderId() {
		return pmsRoomOrderId;
	}

	public void setpMSRoomOrderId(long pMSRoomOrderId) {
		this.pmsRoomOrderId = pMSRoomOrderId;
	}
	
	@Column(name = "PMSRoomOrderNo", nullable = true, length = 100)
	public String getpMSRoomOrderNo() {
		return pmsRoomOrderNo;
	}

	public void setpMSRoomOrderNo(String pMSRoomOrderNo) {
		this.pmsRoomOrderNo = pMSRoomOrderNo;
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

	@Column(name = "HotelPms", nullable = true, length = 100)
	public String getHotelPms() {
		return hotelPms;
	}

	public void setHotelPms(String hotelPms) {
		this.hotelPms = hotelPms;
	}

	@Column(name = "OrderMethod", nullable = true, length = 11)
	public int getOrderMethod() {
		return orderMethod;
	}

	public void setOrderMethod(int orderMethod) {
		this.orderMethod = orderMethod;
	}

	@Column(name = "OrderType", nullable = true, length = 11)
	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	@Column(name = "PriceType", nullable = true, length = 11)
	public int getPriceType() {
		return priceType;
	}

	public void setPriceType(int priceType) {
		this.priceType = priceType;
	}

	@Column(name = "Begintime", nullable = true)
	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	@Column(name = "Endtime", nullable = true)
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	@Column(name = "RoomTypeId", nullable = true, length = 20)
	public long getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(long roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	@Column(name = "RoomTypeName", nullable = true, length = 50)
	public String getRoomTypeName() {
		return roomTypeName;
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}

	@Column(name = "RoomTypePms", nullable = true, length = 100)
	public String getRoomTypePms() {
		return roomTypePms;
	}

	public void setRoomTypePms(String roomTypePms) {
		this.roomTypePms = roomTypePms;
	}

	@Column(name = "RoomId", nullable = true, length = 20)
	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	@Column(name = "RoomNo", nullable = true, length = 50)
	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	@Column(name = "RoomPms", nullable = true, length = 100)
	public String getRoomPms() {
		return roomPms;
	}

	public void setRoomPms(String roomPms) {
		this.roomPms = roomPms;
	}
	
	@Column(name = "Mid", nullable = true, length = 20)
	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	@Column(name = "MLevel", nullable = true, length = 20)
	public long getmLevel() {
		return mLevel;
	}

	public void setmLevel(long mLevel) {
		this.mLevel = mLevel;
	}

	@Column(name = "Promotion", nullable = true, length = 1)
	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	@Column(name = "Coupon", nullable = true, length = 1)
	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	@Column(name = "PromotionNo", nullable = true, length = 100)
	public String getPromotionNo() {
		return promotionNo;
	}

	public void setPromotionNo(String promotionNo) {
		this.promotionNo = promotionNo;
	}
	
	@Column(name = "TotalPrice", nullable = true, length = 8)
	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Column(name = "Price", nullable = true, length = 8)
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(name = "BreakfastNum", nullable = true, length = 11)
	public int getBreakfastNum() {
		return breakfastNum;
	}

	public void setBreakfastNum(int breakfastNum) {
		this.breakfastNum = breakfastNum;
	}

	@Column(name = "Contacts", nullable = true, length = 25)
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@Column(name = "ContactsPhone", nullable = true, length = 25)
	public String getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}

	@Column(name = "ContactsEmail", nullable = true, length = 50)
	public String getContactsEmail() {
		return contactsEmail;
	}

	public void setContactsEmail(String contactsEmail) {
		this.contactsEmail = contactsEmail;
	}

	@Column(name = "ContactsWeixin", nullable = true, length = 50)
	public String getContactsWeixin() {
		return contactsWeixin;
	}

	public void setContactsWeixin(String contactsWeixin) {
		this.contactsWeixin = contactsWeixin;
	}

	@Column(name = "Note", nullable = true, length = 4000)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "OrderStatus", nullable = true, length = 11)
	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(name = "Paystatus", nullable = true, length = 11)
	public int getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(int paystatus) {
		this.paystatus = paystatus;
	}

	@Column(name = "Paytime", nullable = true)
	public Date getPaytime() {
		return paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}
	
	@Column(name = "Receipt", nullable = true, length = 1)
	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	@Column(name = "ReeceiptTitle", nullable = true, length = 100)
	public String getReeceiptTitle() {
		return reeceiptTitle;
	}

	public void setReeceiptTitle(String reeceiptTitle) {
		this.reeceiptTitle = reeceiptTitle;
	}
	
	@Column(name = "Updatetime", nullable = true)
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getFmOrderStatus() {
		return fmOrderStatus;
	}

	public void setFmOrderStatus(String fmOrderStatus) {
		this.fmOrderStatus = fmOrderStatus;
	}
}
