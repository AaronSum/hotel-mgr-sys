package com.mk.hms.model;

import java.math.BigDecimal;
import java.util.Date;

public class BillSpecial {
    private Long id;

    private Integer promotype;

    private Long hotelid;

    private String hotelname;

    private String citycode;

    private String cityname;

    private Integer checkstatus;

    private String billtime;

    private Date begintime;

    private Date endtime;

    private Integer ordernum;

    private BigDecimal onlinepaid;

    private BigDecimal alipaid;

    private BigDecimal wechatpaid;

    private BigDecimal billcost;

    private BigDecimal changecost;

    private BigDecimal hotelchangecost;

    private BigDecimal changecorrectcost;

    private BigDecimal finalcost;

    private BigDecimal income;

    private Date createtime;

    private Integer financestatus;

    private BigDecimal availablemoney;

    private String isfreeze;

    private Boolean feedbackIng;

    public Boolean getFeedbackIng() {
        return feedbackIng;
    }

    public void setFeedbackIng(Boolean feedbackIng) {
        this.feedbackIng = feedbackIng;
    }

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

    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname == null ? null : hotelname.trim();
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode == null ? null : citycode.trim();
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname == null ? null : cityname.trim();
    }

    public Integer getCheckstatus() {
        return checkstatus;
    }

    public void setCheckstatus(Integer checkstatus) {
        this.checkstatus = checkstatus;
    }

    public String getBilltime() {
        return billtime;
    }

    public void setBilltime(String billtime) {
        this.billtime = billtime == null ? null : billtime.trim();
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

    public Integer getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }

    public BigDecimal getOnlinepaid() {
        return onlinepaid;
    }

    public void setOnlinepaid(BigDecimal onlinepaid) {
        this.onlinepaid = onlinepaid;
    }

    public BigDecimal getAlipaid() {
        return alipaid;
    }

    public void setAlipaid(BigDecimal alipaid) {
        this.alipaid = alipaid;
    }

    public BigDecimal getWechatpaid() {
        return wechatpaid;
    }

    public void setWechatpaid(BigDecimal wechatpaid) {
        this.wechatpaid = wechatpaid;
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

    public BigDecimal getChangecorrectcost() {
        return changecorrectcost;
    }

    public void setChangecorrectcost(BigDecimal changecorrectcost) {
        this.changecorrectcost = changecorrectcost;
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

    public String getIsfreeze() {
        return isfreeze;
    }

    public void setIsfreeze(String isfreeze) {
        this.isfreeze = isfreeze == null ? null : isfreeze.trim();
    }

    public BigDecimal getHotelchangecost() {
        return hotelchangecost;
    }

    public void setHotelchangecost(BigDecimal hotelchangecost) {
        this.hotelchangecost = hotelchangecost;
    }
}