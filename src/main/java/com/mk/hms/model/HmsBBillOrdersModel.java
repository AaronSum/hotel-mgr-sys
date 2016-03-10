package com.mk.hms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 账单详情实体
 * 
 * @author hdy
 *
 */
@Entity
@Table(name = "b_bill_orders")
public class HmsBBillOrdersModel {

	private long id;
	private long hotelId;
	private long orderId;
	private int orderType;
	private String roomTypeName;
	private String roomNo;
	private Date beginTime;
	private Date endTime;
	private int dayNumber;
	private double allCost;
	private double userCost;
	private double cutCost;
	private double hotelGive;
	private double otherGive;
	private double serviceCost;
	private Date createTime;
	private int Invalidreason;

	@Id
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "hotelId")
	public long getHotelId() {
		return hotelId;
	}

	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	@Column(name = "orderId")
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	@Column(name = "orderType")
	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	@Column(name = "roomTypeName")
	public String getRoomTypeName() {
		return roomTypeName;
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}

	@Column(name = "roomNo")
	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	@Column(name = "beginTime")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	@Column(name = "endTime")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "dayNumber")
	public int getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
	}

	@Column(name = "allCost")
	public double getAllCost() {
		return allCost;
	}

	public void setAllCost(double allCost) {
		this.allCost = allCost;
	}

	@Column(name = "userCost")
	public double getUserCost() {
		return userCost;
	}

	public void setUserCost(double userCost) {
		this.userCost = userCost;
	}

	@Column(name = "cutCost")
	public double getCutCost() {
		return cutCost;
	}

	public void setCutCost(double cutCost) {
		this.cutCost = cutCost;
	}

	@Column(name = "hotelGive")
	public double getHotelGive() {
		return hotelGive;
	}

	public void setHotelGive(double hotelGive) {
		this.hotelGive = hotelGive;
	}

	@Column(name = "otherGive")
	public double getOtherGive() {
		return otherGive;
	}

	public void setOtherGive(double otherGive) {
		this.otherGive = otherGive;
	}

	@Column(name = "serviceCost")
	public double getServiceCost() {
		return serviceCost;
	}

	public void setServiceCost(double serviceCost) {
		this.serviceCost = serviceCost;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "Invalidreason")
	public int getInvalidreason() {
		return Invalidreason;
	}

	public void setInvalidreason(int invalidreason) {
		Invalidreason = invalidreason;
	}
}
