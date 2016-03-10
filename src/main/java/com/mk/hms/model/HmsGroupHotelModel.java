package com.mk.hms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 分组、酒店中间表
 * 
 * @author hdy
 *
 */
@Entity
@Table(name = "h_group_hotel")
public class HmsGroupHotelModel {

	private long id;
	private long groupid;
	private long hotelid;

	@Id
	@Column(name = "id", nullable = false, length = 20)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "groupid", nullable = true, length = 20)
	public long getGroupid() {
		return groupid;
	}

	public void setGroupid(long groupid) {
		this.groupid = groupid;
	}

	@Column(name = "hotelid", nullable = true, length = 20)
	public long getHotelid() {
		return hotelid;
	}

	public void setHotelid(long hotelid) {
		this.hotelid = hotelid;
	}
}
