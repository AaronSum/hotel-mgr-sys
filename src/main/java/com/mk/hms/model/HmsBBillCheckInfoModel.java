package com.mk.hms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 结算审核明细
 * 
 * @author hdy
 *
 */
@Entity
@Table(name = "b_bill_check_info")
public class HmsBBillCheckInfoModel {

	private long id;
	private long billcheckid;
	private Date checktime;
	private Date backtime;
	private long checkuserid;
	private String checkusername;
	private long backuserid;
	private String backusername;
	private String checkmemo;
	private String backmemo;
	private int checkstatus;
	private String checkstatustext;

	@Id
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "billcheckid")
	public long getBillcheckid() {
		return billcheckid;
	}

	public void setBillcheckid(long billcheckid) {
		this.billcheckid = billcheckid;
	}

	@Column(name = "checktime")
	public Date getChecktime() {
		return checktime;
	}

	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}

	@Column(name = "checkuserid")
	public long getCheckuserid() {
		return checkuserid;
	}

	public void setCheckuserid(long checkuserid) {
		this.checkuserid = checkuserid;
	}

	@Column(name = "checkusername")
	public String getCheckusername() {
		return checkusername;
	}

	public void setCheckusername(String checkusername) {
		this.checkusername = checkusername;
	}

	@Column(name = "checkmemo")
	public String getCheckmemo() {
		return checkmemo;
	}

	public void setCheckmemo(String checkmemo) {
		this.checkmemo = checkmemo;
	}

	@Column(name = "checkstatus")
	public int getCheckstatus() {
		return checkstatus;
	}

	public void setCheckstatus(int checkstatus) {
		this.checkstatus = checkstatus;
	}

	@Column(name = "checkstatustext")
	public String getCheckstatustext() {
		return checkstatustext;
	}

	public void setCheckstatustext(String checkstatustext) {
		this.checkstatustext = checkstatustext;
	}

	@Column(name = "backuserid")
	public long getBackuserid() {
		return backuserid;
	}

	public void setBackuserid(long backuserid) {
		this.backuserid = backuserid;
	}

	@Column(name = "backusername")
	public String getBackusername() {
		return backusername;
	}

	public void setBackusername(String backusername) {
		this.backusername = backusername;
	}

	@Column(name = "backmemo")
	public String getBackmemo() {
		return backmemo;
	}

	public void setBackmemo(String backmemo) {
		this.backmemo = backmemo;
	}

	@Column(name = "backtime")
	public Date getBacktime() {
		return backtime;
	}

	public void setBacktime(Date backtime) {
		this.backtime = backtime;
	}
}
