package com.mk.hms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 结算纪录信息实体
 * @author hdy
 *
 */
@Entity
@Table(name = "b_bill_confirm_check")
public class HmsBBillConfirmCheckModel {

	private long id;
	private long hotelid;
	private int confirmstatus; //暂时不用该字段
	private int checkstatus;
	private long checkuserid;
	private String checkusername;
	private Date checktime;
	private Date confirmtime;
	private String billtime;
	private Date begintime;
	private Date endtime;
	private int ordernum;
	private int prepaymentnum;
	private double prepaymentcost;
	private int topaynum;
	private double topaymon;
	private int cutofforders;
	private double cutoffcost;
	private double hoteldiscountcost;
	private double otherdiscountcost;
	private double servicecost;
	private double changecost;
	private long confirmuserid;
	private String confirmusername;
	private double billcost;
	private double finalcost;
	private double invalidcutofforders;
	private double hotelcost;
	private double splitcost;

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

	@Column(name = "confirmstatus")
	public int getConfirmstatus() {
		return confirmstatus;
	}

	public void setConfirmstatus(int confirmstatus) {
		this.confirmstatus = confirmstatus;
	}

	@Column(name = "checkstatus")
	public int getCheckstatus() {
		return checkstatus;
	}

	public void setCheckstatus(int checkstatus) {
		this.checkstatus = checkstatus;
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

	@Column(name = "checktime")
	public Date getChecktime() {
		return checktime;
	}

	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}

	@Column(name = "confirmtime")
	public Date getConfirmtime() {
		return confirmtime;
	}

	public void setConfirmtime(Date confirmtime) {
		this.confirmtime = confirmtime;
	}

	@Column(name = "billtime")
	public String getBilltime() {
		return billtime;
	}

	public void setBilltime(String billtime) {
		this.billtime = billtime;
	}

	@Column(name = "begintime")
	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	@Column(name = "endtime")
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	@Column(name = "ordernum")
	public int getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(int ordernum) {
		this.ordernum = ordernum;
	}

	@Column(name = "prepaymentnum")
	public int getPrepaymentnum() {
		return prepaymentnum;
	}

	public void setPrepaymentnum(int prepaymentnum) {
		this.prepaymentnum = prepaymentnum;
	}

	@Column(name = "prepaymentcost")
	public double getPrepaymentcost() {
		return prepaymentcost;
	}

	public void setPrepaymentcost(double prepaymentcost) {
		this.prepaymentcost = prepaymentcost;
	}

	@Column(name = "topaynum")
	public int getTopaynum() {
		return topaynum;
	}

	public void setTopaynum(int topaynum) {
		this.topaynum = topaynum;
	}

	@Column(name = "topaymon")
	public double getTopaymon() {
		return topaymon;
	}

	public void setTopaymon(double topaymon) {
		this.topaymon = topaymon;
	}

	@Column(name = "cutofforders")
	public int getCutofforders() {
		return cutofforders;
	}

	public void setCutofforders(int cutofforders) {
		this.cutofforders = cutofforders;
	}

	@Column(name = "cutoffcost")
	public double getCutoffcost() {
		return cutoffcost;
	}

	public void setCutoffcost(double cutoffcost) {
		this.cutoffcost = cutoffcost;
	}

	@Column(name = "hoteldiscountcost")
	public double getHoteldiscountcost() {
		return hoteldiscountcost;
	}

	public void setHoteldiscountcost(double hoteldiscountcost) {
		this.hoteldiscountcost = hoteldiscountcost;
	}

	@Column(name = "otherdiscountcost")
	public double getOtherdiscountcost() {
		return otherdiscountcost;
	}

	public void setOtherdiscountcost(double otherdiscountcost) {
		this.otherdiscountcost = otherdiscountcost;
	}

	@Column(name = "servicecost")
	public double getServicecost() {
		return servicecost;
	}

	public void setServicecost(double servicecost) {
		this.servicecost = servicecost;
	}

	@Column(name = "changecost")
	public double getChangecost() {
		return changecost;
	}

	public void setChangecost(double changecost) {
		this.changecost = changecost;
	}

	@Column(name = "confirmuserid")
	public long getConfirmuserid() {
		return confirmuserid;
	}

	public void setConfirmuserid(long confirmuserid) {
		this.confirmuserid = confirmuserid;
	}

	@Column(name = "confirmusername")
	public String getConfirmusername() {
		return confirmusername;
	}

	public void setConfirmusername(String confirmusername) {
		this.confirmusername = confirmusername;
	}
	
	@Column(name = "billcost")
	public double getBillcost() {
		return billcost;
	}

	public void setBillcost(double billcost) {
		this.billcost = billcost;
	}
	
	@Column(name = "finalcost")
	public double getFinalcost() {
		return finalcost;
	}

	public void setFinalcost(double finalcost) {
		this.finalcost = finalcost;
	}

	@Column(name = "invalidcutofforders")
	public double getInvalidcutofforders() {
		return invalidcutofforders;
	}

	public void setInvalidcutofforders(double invalidcutofforders) {
		this.invalidcutofforders = invalidcutofforders;
	}

	@Column(name = "hotelcost")
	public double getHotelcost() {
		return hotelcost;
	}

	public void setHotelcost(double hotelcost) {
		this.hotelcost = hotelcost;
	}
	
	@Column(name = "splitcost")
	public double getSplitcost() {
		return splitcost;
	}

	public void setSplitcost(double splitcost) {
		this.splitcost = splitcost;
	}
}
