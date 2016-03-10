package com.mk.hms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单实体
 * @author hdy
 *
 */
@Entity
@Table(name = "b_otaorder")
public class HmsOtaOrderModel {

	private long id; // 主键
	private long hotelId; // 酒店主键
	private String hotelName; // 酒店名称
	private String hotelPms; // PMS编码
	private int orderMethod; // 预定方式
	private int orderType; // 订单类型
	private int priceType; // 价格类型
	private Date begintime; // 预计抵店时间
	private Date endtime; // 预计离店时间
	private long mid; // 订单所属会员id
	private long mLevel; // 会员等级id
	private Date createtime; // 创建时间
	private String promotion; // 是否促销
	private String coupon; // 是否使用优惠券
	private double totalPrice; // 总房价
	private double price; // 房价
	private int breakfastNum; // 早餐数
	private String contacts; // 联系人
	private String contactsPhone; // 联系电话
	private String contactsEmail; // 联系邮箱
	private String contactsWeixin; // 联系微信
	private String note; // 备注
	private int orderStatus; // 订单状态
	private int paystatus; // 支付状态
	private String receipt; // 是否需要发票
	private Date updatetime; // 修改时间
	private String canshow; // 是否显示
	private String hiddenOrder; // 无痕订单
	private String osType; // app系统类别
	private int version; // app版本
	private long spreadUser; // ？？？
	private int daynumber; // 预定天数
	private float allcost;  //预付
	private float realotagive;//到付

	@Id
	@Column(name = "id", nullable = false, length = 20)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "HotelId", nullable = true, length = 20)
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

	@Column(name = "Hotelpms", nullable = true, length = 100)
	public String getHotelPms() {
		return hotelPms;
	}

	public void setHotelPms(String hotelPms) {
		this.hotelPms = hotelPms;
	}

	@Column(name = "Ordermethod", nullable = true, length = 11)
	public int getOrderMethod() {
		return orderMethod;
	}

	public void setOrderMethod(int orderMethod) {
		this.orderMethod = orderMethod;
	}

	@Column(name = "Ordertype", nullable = true, length = 11)
	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	@Column(name = "Pricetype", nullable = true, length = 11)
	public int getPriceType() {
		return priceType;
	}

	public void setPriceType(int priceType) {
		this.priceType = priceType;
	}

	@Column(name = "Begintime")
	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	@Column(name = "Endtime")
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
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

	@Column(name = "Createtime")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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

	@Column(name = "Receipt", nullable = true, length = 1)
	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	@Column(name = "Updatetime")
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	@Column(name = "canshow", nullable = true, length = 1)
	public String getCanshow() {
		return canshow;
	}

	public void setCanshow(String canshow) {
		this.canshow = canshow;
	}

	@Column(name = "hiddenOrder", nullable = true, length = 1)
	public String getHiddenOrder() {
		return hiddenOrder;
	}

	public void setHiddenOrder(String hiddenOrder) {
		this.hiddenOrder = hiddenOrder;
	}

	@Column(name = "ostype", nullable = true, length = 1)
	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	@Column(name = "version", nullable = true, length = 11)
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Column(name = "spreadUser", nullable = true, length = 20)
	public long getSpreadUser() {
		return spreadUser;
	}

	public void setSpreadUser(long spreadUser) {
		this.spreadUser = spreadUser;
	}

	@Column(name = "daynumber", nullable = true, length = 11)
	public int getDaynumber() {
		return daynumber;
	}

	public void setDaynumber(int daynumber) {
		this.daynumber = daynumber;
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
	
}
