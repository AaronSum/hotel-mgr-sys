package com.mk.hms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 酒店表操作日志
 * @author hdy
 *
 */
@Entity
@Table(name = "t_hotel_operate_log")
public class HmsTHotelOperateLogModel {

	private long id;
	private long hotelid;
	private String hotelname;
	private long usercode;
	private String username;
	private Date checktime;
	private int checktype;
	private String checktypename;

	@Id
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "hotelid")
	public long getHotelid() {
		return hotelid;
	}

	public void setHotelid(long hotelid) {
		this.hotelid = hotelid;
	}

	@Column(name = "hotelname")
	public String getHotelname() {
		return hotelname;
	}

	public void setHotelname(String hotelname) {
		this.hotelname = hotelname;
	}

	@Column(name = "usercode")
	public long getUsercode() {
		return usercode;
	}

	public void setUsercode(long usercode) {
		this.usercode = usercode;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "checktime")
	public Date getChecktime() {
		return checktime;
	}

	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}

	@Column(name = "checktype")
	public int getChecktype() {
		return checktype;
	}

	public void setChecktype(int checktype) {
		this.checktype = checktype;
	}

	@Column(name = "checktypename")
	public String getChecktypename() {
		return checktypename;
	}

	public void setChecktypename(String checktypename) {
		this.checktypename = checktypename;
	}
}
