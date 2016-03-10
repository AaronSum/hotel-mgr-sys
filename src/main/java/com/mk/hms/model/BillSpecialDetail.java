package com.mk.hms.model;

import java.math.BigDecimal;
import java.util.Date;

public class BillSpecialDetail {
    private Long id;

    private Long billid;

    private Long orderid;

    private Long hotelid;

    private Date checkintime;

    private Date checkouttime;

    private Date begintime;

    private Date endtime;

    private Date ordertime;

    private Integer daynumber;

    private BigDecimal mikeprice;

    private BigDecimal discount;

    private BigDecimal lezhucoins;

    private BigDecimal orderprice;

    private Integer ordertype;

    private String roomtypename;

    private String roomno;

    private String paymethod;

    private BigDecimal usercost;

    private BigDecimal availablemoney;

    private Date createtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBillid() {
        return billid;
    }

    public void setBillid(Long billid) {
        this.billid = billid;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Long getHotelid() {
        return hotelid;
    }

    public void setHotelid(Long hotelid) {
        this.hotelid = hotelid;
    }

    public Date getCheckintime() {
        return checkintime;
    }

    public void setCheckintime(Date checkintime) {
        this.checkintime = checkintime;
    }

    public Date getCheckouttime() {
        return checkouttime;
    }

    public void setCheckouttime(Date checkouttime) {
        this.checkouttime = checkouttime;
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

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public Integer getDaynumber() {
        return daynumber;
    }

    public void setDaynumber(Integer daynumber) {
        this.daynumber = daynumber;
    }

    public BigDecimal getMikeprice() {
        return mikeprice;
    }

    public void setMikeprice(BigDecimal mikeprice) {
        this.mikeprice = mikeprice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getLezhucoins() {
        return lezhucoins;
    }

    public void setLezhucoins(BigDecimal lezhucoins) {
        this.lezhucoins = lezhucoins;
    }

    public BigDecimal getOrderprice() {
        return orderprice;
    }

    public void setOrderprice(BigDecimal orderprice) {
        this.orderprice = orderprice;
    }

    public Integer getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(Integer ordertype) {
        this.ordertype = ordertype;
    }

    public String getRoomtypename() {
        return roomtypename;
    }

    public void setRoomtypename(String roomtypename) {
        this.roomtypename = roomtypename == null ? null : roomtypename.trim();
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno == null ? null : roomno.trim();
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod == null ? null : paymethod.trim();
    }

    public BigDecimal getUsercost() {
        return usercost;
    }

    public void setUsercost(BigDecimal usercost) {
        this.usercost = usercost;
    }

    public BigDecimal getAvailablemoney() {
        return availablemoney;
    }

    public void setAvailablemoney(BigDecimal availablemoney) {
        this.availablemoney = availablemoney;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}