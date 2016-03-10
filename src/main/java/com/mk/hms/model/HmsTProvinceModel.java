package com.mk.hms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 省份数据列表
 * @author hdy
 *
 */
@Entity
@Table(name = "t_province")
public class HmsTProvinceModel {

	private long proId;
	private String code;
	private String proName;
	private int proSort;
	private String proRemark;
	private double latitude;
	private double longitude;

	@Id
	@Column(name = "ProID", nullable = false, length = 20)
	public long getProId() {
		return proId;
	}

	public void setProId(long proId) {
		this.proId = proId;
	}

	@Column(name = "Code", nullable = true, length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "ProName", nullable = true, length = 50)
	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	@Column(name = "ProSort", nullable = true, length = 11)
	public int getProSort() {
		return proSort;
	}

	public void setProSort(int proSort) {
		this.proSort = proSort;
	}

	@Column(name = "ProRemark", nullable = true, length = 50)
	public String getProRemark() {
		return proRemark;
	}

	public void setProRemark(String proRemark) {
		this.proRemark = proRemark;
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
