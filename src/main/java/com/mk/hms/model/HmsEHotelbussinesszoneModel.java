package com.mk.hms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 酒店商圈信息
 * @author hdy
 *
 */
@Entity
@Table(name = "e_hotelbussinesszone")
public class HmsEHotelbussinesszoneModel {

	private long id;
	private long businessZoneId;
	private long hotelId;

	@Id
	@Column(name = "id", nullable = false, length = 20)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "businessZoneId", nullable = true, length = 20)
	public long getBusinessZoneId() {
		return businessZoneId;
	}

	public void setBusinessZoneId(long businessZoneId) {
		this.businessZoneId = businessZoneId;
	}

	@Column(name = "hotelId", nullable = true, length = 20)
	public long getHotelId() {
		return hotelId;
	}

	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}
}
