package com.mk.hms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 二维码实体
 * 
 * @author hdy
 *
 */
@Entity
@Table(name = "h_qrcode")
public class HmsHQrcodeModel {

	private long id;
	private long hotelid;
	private long userid;
	private String content;
	private String tag;

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

	@Column(name = "userid", nullable = true, length = 20)
	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	@Column(name = "content", nullable = true, length = 255)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "tag", nullable = true, length = 255)
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
