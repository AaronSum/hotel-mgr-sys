package com.mk.hms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 获取pms用户角色实体
 * @author hdy
 *
 */
@Entity
@Table(name = "m_role")
public class HmsMRoleModel {

	private long id;
	private String name;
	private String desction;

	@Id
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "desction")
	public String getDesction() {
		return desction;
	}

	public void setDesction(String desction) {
		this.desction = desction;
	}
}
