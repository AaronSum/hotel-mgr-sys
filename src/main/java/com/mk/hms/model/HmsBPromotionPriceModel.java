package com.mk.hms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 优惠券实体
 * 
 * @author hdy
 *
 */
@Entity
@Table(name = "b_promotion_price")
public class HmsBPromotionPriceModel {

	private long id;
	private long promotion;
	private double price;
	private double offlineprice;
	private long otaorderid;

	@Id
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "promotion")
	public long getPromotion() {
		return promotion;
	}

	public void setPromotion(long promotion) {
		this.promotion = promotion;
	}

	@Column(name = "price")
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(name = "offlineprice")
	public double getOfflineprice() {
		return offlineprice;
	}

	public void setOfflineprice(double offlineprice) {
		this.offlineprice = offlineprice;
	}

	@Column(name = "otaorderid")
	public long getOtaorderid() {
		return otaorderid;
	}

	public void setOtaorderid(long otaorderid) {
		this.otaorderid = otaorderid;
	}
}
