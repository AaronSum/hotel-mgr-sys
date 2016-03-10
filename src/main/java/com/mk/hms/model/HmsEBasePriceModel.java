package com.mk.hms.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 基础房价e表
 * @author qhdhiman 2015-5-5
 *
 */
@Entity
@Table(name = "e_baseprice")
public class HmsEBasePriceModel {

	private long id; // 主键
	private long roomTypeId; //房型编码
	private BigDecimal price; //价格
	private BigDecimal subprice; //下浮价格
	private BigDecimal subper; //下浮比例
	private Date updateTime; //操作时间

	@Id
	@Column(name = "id", nullable = false, length = 20)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "roomTypeId", nullable = false, length = 20)
	public long getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(long roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	@Column(name = "price", nullable = true, length = 10)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name = "subprice", nullable = true, length = 10)
	public BigDecimal getSubprice() {
		return subprice;
	}

	public void setSubprice(BigDecimal subprice) {
		this.subprice = subprice;
	}

	@Column(name = "subper", nullable = true, length = 10)
	public BigDecimal getSubper() {
		return subper;
	}

	public void setSubper(BigDecimal subper) {
		this.subper = subper;
	}

	@Column(name = "updateTime", nullable = false, length = 20)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
