package com.mk.hms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 酒店银行账号信息
 * @author qhdhiman
 *
 */
@Entity
@Table(name = "t_hotel_bank")
public class HmsTHotelBankModel implements Serializable{
	private long id; //主键
	private long hotelId; //酒店Id
	private String name; //户名
	private String account; //账号
	private String bank; //开户行
	private String bankBranch; //支行
	private String transferType; //转帐类型，1：同城（针对上海市），2:异地

	@Id
	@Column(name = "id", nullable = false, length = 20)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "hotelId", nullable = false, length = 20)
	public long getHotelId() {
		return hotelId;
	}

	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "account", nullable = false, length = 100)
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "bank", nullable = false, length = 100)
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "bankBranch", nullable = false, length = 100)
	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	@Column(name = "transferType", nullable = false, length = 50)
	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	
}
