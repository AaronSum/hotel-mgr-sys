package com.mk.hms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 城市信息实体
 * @author hdy
 *
 */
@Entity
@Table(name = "t_city")
public class HmsTCityModel {

	private long cityId;
	private String code;
	private String cityName;
	private int proID;
	private int citySort;
	private double latitude;
	private double longitude;

	@Id
	@Column(name = "cityid", nullable = false, length = 20)
	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	@Column(name = "Code", nullable = true, length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "CityName", nullable = true, length = 50)
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name = "ProID", nullable = true, length = 11)
	public int getProID() {
		return proID;
	}

	public void setProID(int proID) {
		this.proID = proID;
	}

	@Column(name = "CitySort", nullable = true, length = 11)
	public int getCitySort() {
		return citySort;
	}

	public void setCitySort(int citySort) {
		this.citySort = citySort;
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
