package com.mk.hms.model;

import java.math.BigDecimal;
import java.util.Date;

public class BillSpecialDay {
    private Long id;

    private Integer promotype;

    private Long hotelid;

    private Date begintime;

    private Date endtime;

    private Integer orderid;

    private BigDecimal onlinepaied;

    private BigDecimal alipaied;

    private BigDecimal wechatpaied;

    private BigDecimal billcost;

    private BigDecimal changecost;

    private BigDecimal finalcost;

    private BigDecimal income;

    private Date createtime;

    private Integer financestatus;

    private BigDecimal availablemoney;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPromotype() {
        return promotype;
    }

    public void setPromotype(Integer promotype) {
        this.promotype = promotype;
    }

    public Long getHotelid() {
        return hotelid;
    }

    public void setHotelid(Long hotelid) {
        this.hotelid = hotelid;
    }

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public BigDecimal getOnlinepaied() {
        return onlinepaied;
    }

    public void setOnlinepaied(BigDecimal onlinepaied) {
        this.onlinepaied = onlinepaied;
    }

    public BigDecimal getAlipaied() {
        return alipaied;
    }

    public void setAlipaied(BigDecimal alipaied) {
        this.alipaied = alipaied;
    }

    public BigDecimal getWechatpaied() {
        return wechatpaied;
    }

    public void setWechatpaied(BigDecimal wechatpaied) {
        this.wechatpaied = wechatpaied;
    }

    public BigDecimal getBillcost() {
        return billcost;
    }

    public void setBillcost(BigDecimal billcost) {
        this.billcost = billcost;
    }

    public BigDecimal getChangecost() {
        return changecost;
    }

    public void setChangecost(BigDecimal changecost) {
        this.changecost = changecost;
    }

    public BigDecimal getFinalcost() {
        return finalcost;
    }

    public void setFinalcost(BigDecimal finalcost) {
        this.finalcost = finalcost;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getFinancestatus() {
        return financestatus;
    }

    public void setFinancestatus(Integer financestatus) {
        this.financestatus = financestatus;
    }

    public BigDecimal getAvailablemoney() {
        return availablemoney;
    }

    public void setAvailablemoney(BigDecimal availablemoney) {
        this.availablemoney = availablemoney;
    }
}