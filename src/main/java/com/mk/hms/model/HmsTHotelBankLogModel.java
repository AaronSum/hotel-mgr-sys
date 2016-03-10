package com.mk.hms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 酒店银行账号信息日志
 * @author qhdhiman
 *
 */
@Entity
@Table(name = "t_hotel_bank_log")
public class HmsTHotelBankLogModel implements Serializable{
	private long id; //主键
	private long hotelId; //酒店Id
	private String userCode; //操作人编码
	private String userName; //操作人姓名
	private String oldVal; //旧值
	private String newVal; //新值
	private Date createTime; //操作时间

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
	
	@Column(name = "userCode", nullable = false, length = 20)
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Column(name = "userName", nullable = true, length = 50)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "oldVal", nullable = true, length = 2000)
	public String getOldVal() {
		return oldVal;
	}

	public void setOldVal(String oldVal) {
		this.oldVal = oldVal;
	}

	@Column(name = "newVal", nullable = false, length = 2000)
	public String getNewVal() {
		return newVal;
	}

	public void setNewVal(String newVal) {
		this.newVal = newVal;
	}

	@Column(name = "createTime", nullable = false, length = 20)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
