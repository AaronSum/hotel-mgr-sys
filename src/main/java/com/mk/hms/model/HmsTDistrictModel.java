package com.mk.hms.model;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 区县信息实体
 * 
 * @author hdy
 *
 */
@Entity
@Resource(name = "t_district")
public class HmsTDistrictModel {

	private long id;
	private String code;
	private String disName;
	private int cityID;
	private int disSort;
	private double latitude;
	private double longitude;

	@Id
	@Column(name = "id", nullable = false, length = 20)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "Code", nullable = true, length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "DisName", nullable = true, length = 50)
	public String getDisName() {
		return disName;
	}

	public void setDisName(String disName) {
		this.disName = disName;
	}

	@Column(name = "CityID", nullable = true, length = 11)
	public int getCityID() {
		return cityID;
	}

	public void setCityID(int cityID) {
		this.cityID = cityID;
	}

	@Column(name = "DisSort", nullable = true, length = 11)
	public int getDisSort() {
		return disSort;
	}

	public void setDisSort(int disSort) {
		this.disSort = disSort;
	}

	@Column(name = "latitude", nullable = true, length = 12)
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "longitude", nullable = true, length = 12)
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
