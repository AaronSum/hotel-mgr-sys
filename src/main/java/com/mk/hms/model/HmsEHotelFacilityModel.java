package com.mk.hms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 酒店已配置设施
 * @author hdy
 *
 */
@Entity
@Table(name = "e_hotel_facility")
public class HmsEHotelFacilityModel {

	private long id;
	private long hotelId;
	private long facId;

	@Id
	@Column(name = "id", nullable = false, length = 40)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "hotelId", nullable = true, length = 40)
	public long getHotelId() {
		return hotelId;
	}

	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	@Column(name = "facId", nullable = true, length = 40)
	public long getFacId() {
		return facId;
	}

	public void setFacId(long facId) {
		this.facId = facId;
	}
}
