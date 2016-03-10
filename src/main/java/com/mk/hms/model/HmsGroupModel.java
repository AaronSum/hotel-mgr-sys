package com.mk.hms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户分组
 * @author hdy
 *
 */
@Entity
@Table(name = "h_group")
public class HmsGroupModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4930880376841658559L;
	private long id;
	private String regphone;
	private String name;

	@Id
	@Column(name = "id", nullable = false, length = 20)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "regphone", nullable = true, length = 20)
	public String getRegphone() {
		return regphone;
	}

	public void setRegphone(String regphone) {
		this.regphone = regphone;
	}

	@Column(name = "name", nullable = true, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
