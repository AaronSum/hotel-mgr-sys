package com.mk.hms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户角色实体
 * @author hdy
 *
 */
@Entity
@Table(name = "h_role")
public class HmsRoleModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3139008897530020370L;
	private long id;
	private long hotelid;
	private String name;
	private int type;

	@Id
	@Column(name = "id", nullable = false, length = 20)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "hotelid", nullable = true, length = 20)
	public long getHotelid() {
		return hotelid;
	}

	public void setHotelid(long hotelid) {
		this.hotelid = hotelid;
	}

	@Column(name = "name", nullable = true, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "type", nullable = true, length = 11)
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
