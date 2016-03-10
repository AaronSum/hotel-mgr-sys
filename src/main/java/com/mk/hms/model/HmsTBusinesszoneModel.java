package com.mk.hms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商圈信息实体
 * @author hdy
 *
 */
@Entity
@Table(name = "t_businesszone")
public class HmsTBusinesszoneModel {

	private long id;
	private String name;
	private long dis; // 区县id
	private long businessZoneType; // 周边类型
	private long fatherid; // 父商圈id
	private long cityid; // 城市id

	@Id
	@Column(name = "id", nullable = false, length = 20)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "name", nullable = true, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "dis", nullable = true, length = 20)
	public long getDis() {
		return dis;
	}

	public void setDis(long dis) {
		this.dis = dis;
	}

	@Column(name = "businessZoneType", nullable = true, length = 20)
	public long getBusinessZoneType() {
		return businessZoneType;
	}

	public void setBusinessZoneType(long businessZoneType) {
		this.businessZoneType = businessZoneType;
	}

	@Column(name = "fatherid", nullable = true, length = 20)
	public long getFatherid() {
		return fatherid;
	}

	public void setFatherid(long fatherid) {
		this.fatherid = fatherid;
	}

	@Column(name = "cityid", nullable = true, length = 20)
	public long getCityid() {
		return cityid;
	}

	public void setCityid(long cityid) {
		this.cityid = cityid;
	}
}
