package com.mk.hms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 跑批实体
 * @author hdy
 *
 */
@Entity
@Table(name = "t_bill_detail")
public class HmsTBillDetailModel {

	private String dayDetailType;
	private long otaorderId;
	private long otaroomorderId;
	private long hotelId;
	private String orderDate;
	private long roomId;
	private String roomNo;
	private double roomPrice;
	private double commissionRate;
	private double commission;
	private double commissionDRate;
	private double hotelDiscuntOnly;
	private double hotelDiscount;
	private double otaDiscount;
	private long conectHotelId;
	private double conectHotelprice;
	private long mId;
	private double payBack;
	private long conectPersonId;
	private double conectPersonPrice;
	private String isConfirm;
	private Date createTime;
	private Date updTime;
	private long dayDetailid;

	@Column(name = "dayDetailType")
	public String getDayDetailType() {
		return dayDetailType;
	}

	public void setDayDetailType(String dayDetailType) {
		this.dayDetailType = dayDetailType;
	}

	@Column(name = "otaorderId")
	public long getOtaorderId() {
		return otaorderId;
	}

	public void setOtaorderId(long otaorderId) {
		this.otaorderId = otaorderId;
	}

	@Column(name = "otaroomorderId")
	public long getOtaroomorderId() {
		return otaroomorderId;
	}

	public void setOtaroomorderId(long otaroomorderId) {
		this.otaroomorderId = otaroomorderId;
	}

	@Column(name = "hotelId")
	public long getHotelId() {
		return hotelId;
	}

	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	@Column(name = "orderDate")
	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	@Column(name = "roomId")
	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	@Column(name = "roomNo")
	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	@Column(name = "roomPrice")
	public double getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(double roomPrice) {
		this.roomPrice = roomPrice;
	}

	@Column(name = "commissionRate")
	public double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(double commissionRate) {
		this.commissionRate = commissionRate;
	}

	@Column(name = "commission")
	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	@Column(name = "commissionDRate")
	public double getCommissionDRate() {
		return commissionDRate;
	}

	public void setCommissionDRate(double commissionDRate) {
		this.commissionDRate = commissionDRate;
	}

	@Column(name = "hotelDiscuntOnly")
	public double getHotelDiscuntOnly() {
		return hotelDiscuntOnly;
	}

	public void setHotelDiscuntOnly(double hotelDiscuntOnly) {
		this.hotelDiscuntOnly = hotelDiscuntOnly;
	}

	@Column(name = "hotelDiscount")
	public double getHotelDiscount() {
		return hotelDiscount;
	}

	public void setHotelDiscount(double hotelDiscount) {
		this.hotelDiscount = hotelDiscount;
	}

	@Column(name = "otaDiscount")
	public double getOtaDiscount() {
		return otaDiscount;
	}

	public void setOtaDiscount(double otaDiscount) {
		this.otaDiscount = otaDiscount;
	}

	@Column(name = "conectHotelId")
	public long getConectHotelId() {
		return conectHotelId;
	}

	public void setConectHotelId(long conectHotelId) {
		this.conectHotelId = conectHotelId;
	}

	@Column(name = "conectHotelprice")
	public double getConectHotelprice() {
		return conectHotelprice;
	}

	public void setConectHotelprice(double conectHotelprice) {
		this.conectHotelprice = conectHotelprice;
	}

	@Column(name = "mId")
	public long getmId() {
		return mId;
	}

	public void setmId(long mId) {
		this.mId = mId;
	}

	@Column(name = "payBack")
	public double getPayBack() {
		return payBack;
	}

	public void setPayBack(double payBack) {
		this.payBack = payBack;
	}

	@Column(name = "conectPersonId")
	public long getConectPersonId() {
		return conectPersonId;
	}

	public void setConectPersonId(long conectPersonId) {
		this.conectPersonId = conectPersonId;
	}

	@Column(name = "conectPersonPrice")
	public double getConectPersonPrice() {
		return conectPersonPrice;
	}

	public void setConectPersonPrice(double conectPersonPrice) {
		this.conectPersonPrice = conectPersonPrice;
	}

	@Column(name = "isConfirm")
	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "updTime")
	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	@Column(name = "dayDetailid")
	public long getDayDetailid() {
		return dayDetailid;
	}

	public void setDayDetailid(long dayDetailid) {
		this.dayDetailid = dayDetailid;
	}
}
