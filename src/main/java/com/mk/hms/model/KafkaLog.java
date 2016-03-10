package com.mk.hms.model;

import java.util.Date;

public class KafkaLog {
	/*id*/
	private long id;
	/*报文*/
	private String jsondata;
	/*创建时间*/
	private Date createtime;
	/*other1*/
	private String other1;
	/*other2*/
	private String other2;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the jsondata
	 */
	public String getJsondata() {
		return jsondata;
	}
	/**
	 * @param jsondata the jsondata to set
	 */
	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}
	/**
	 * @return the createtime
	 */
	public Date getCreatetime() {
		return createtime;
	}
	/**
	 * @param createtime the createtime to set
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * @return the orther1
	 */
	public String getOrther1() {
		return other1;
	}
	/**
	 * @param orther1 the orther1 to set
	 */
	public void setOrther1(String orther1) {
		this.other1 = orther1;
	}
	/**
	 * @return the orther2
	 */
	public String getOrther2() {
		return other2;
	}
	/**
	 * @param orther2 the orther2 to set
	 */
	public void setOrther2(String orther2) {
		this.other2 = orther2;
	}
}
