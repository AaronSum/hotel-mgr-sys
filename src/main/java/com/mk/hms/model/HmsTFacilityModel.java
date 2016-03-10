package com.mk.hms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 酒店设施实体
 * @author hdy
 *
 */
@Entity
@Table(name = "t_facility")
public class HmsTFacilityModel {

	private long id; //主键
	private String facName; //设施名称
	private int facType; //设施类型
	private String binding; //可配置设备
	private int facSort; //排序
	private String visible; //是否可用

	@Id
	@Column(name = "id", nullable = false, length = 40)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "facName", nullable = true, length = 50)
	public String getFacName() {
		return facName;
	}

	public void setFacName(String facName) {
		this.facName = facName;
	}

	@Column(name = "facType", nullable = true, length = 11)
	public int getFacType() {
		return facType;
	}

	public void setFacType(int facType) {
		this.facType = facType;
	}

	@Column(name = "binding", nullable = true, length = 8)
	public String getBinding() {
		return binding;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	@Column(name = "facSort", nullable = true, length = 11)
	public int getFacSort() {
		return facSort;
	}

	public void setFacSort(int facSort) {
		this.facSort = facSort;
	}

	@Column(name = "visible", nullable = true, length = 1)
	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}
}
