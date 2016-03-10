package com.mk.hms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 账单拆分详情实体
 * 
 * @author wangchen
 *
 */
@Entity
@Table(name = "bms_hotel_bill_split")
public class HmsBBillSplitModel {

	private String id;
	private long billId;
	private String payObject;
	private String name;
	private String account;
	private double moneyAmount;
	private String bank;
	private String bankBranch;
	private String transferType;
	private Date createTime;
//	private double billcost;
//	private double changecost;
//	private double hotelcost;

	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "billId")
	public long getBillId() {
		return billId;
	}

	public void setBillId(long billId) {
		this.billId = billId;
	}

	@Column(name = "payObject")
	public String getPayObject() {
		return payObject;
	}

	public void setPayObject(String payObject) {
		this.payObject = payObject;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "account")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "moneyAmount")
	public double getMoneyAmount() {
		return moneyAmount;
	}

	public void setMoneyAmount(double moneyAmount) {
		this.moneyAmount = moneyAmount;
	}

	@Column(name = "bank")
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "bankBranch")
	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	@Column(name = "transferType")
	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
//	@Column(name = "billcost")
//	public double getBillcost() {
//		return billcost;
//	}
//
//	public void setBillcost(double billcost) {
//		this.billcost = billcost;
//	}
//	
//	@Column(name = "changecost")
//	public double getChangecost() {
//		return changecost;
//	}
//
//	public void setChangecost(double changecost) {
//		this.changecost = changecost;
//	}
//	
//	@Column(name = "hotelcost")
//	public double getHotelcost() {
//		return hotelcost;
//	}
//
//	public void setHotelcost(double hotelcost) {
//		this.hotelcost = hotelcost;
//	}

}
