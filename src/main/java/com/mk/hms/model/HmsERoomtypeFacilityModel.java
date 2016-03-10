package com.mk.hms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 房型已配置设施
 * @author qhdhiman 2015/5/3
 */
@Entity
@Table(name = "e_roomtype_facility")
public class HmsERoomtypeFacilityModel implements Serializable{

	private long id;
	private long roomTypeId;
	private long facId;

	@Id
	@Column(name = "id", nullable = false, length = 40)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "roomTypeId", nullable = true, length = 40)
	public long getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(long roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	@Column(name = "facId", nullable = true, length = 40)
	public long getFacId() {
		return facId;
	}

	public void setFacId(long facId) {
		this.facId = facId;
	}
}
