package com.mk.hms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 房价时间e表
 * @author qhdhiman 2015-5-6
 *
 */
@Entity
@Table(name = "t_pricetime")
public class HmsEPriceTimeModel {

	private long id; // 主键
	private String name; //名称
	private String cron;//cron表达式
	private String addCron;//额外增加cron表达式，多个以|号分隔
	private String subCron;//排除日期的cron表达式，多个以|号分隔。最高的优先级，即只要在排除列表中，即便在增加列表和主策略中也会被排除
	private long hotelId; //房型编码
	private Date updateTime; //操作时间
	private Date beginTime; //操作时间
	private Date endTime; //操作时间

	@Id
	@Column(name = "id", nullable = false, length = 20)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "cron", nullable = false, length = 100)
	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	@Column(name = "addcron", nullable = true, length = 20)
	public String getAddCron() {
		return addCron;
	}

	public void setAddCron(String addCron) {
		this.addCron = addCron;
	}

	@Column(name = "subcron", nullable = true, length = 20)
	public String getSubCron() {
		return subCron;
	}

	public void setSubCron(String subCron) {
		this.subCron = subCron;
	}

	@Column(name = "hotelid", nullable = false, length = 20)
	public long getHotelId() {
		return hotelId;
	}
	
	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	@Column(name = "Updatetime", nullable = false, length = 20)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "Begintime", nullable = false, length = 20)
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	@Column(name = "Endtime", nullable = false, length = 20)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
