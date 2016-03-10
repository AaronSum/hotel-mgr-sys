package com.mk.hms.model;

import java.math.BigDecimal;
import java.util.Date;

public class BillOrderWeek {
    private Long id;
    private Long hotelId;
    private String hotelName;
    private Integer checkStatus;
    private String checkStatusName;
    private String settleStatusName;
    private Date beginTime;
    private Date endTime;
    private Integer orderNum;
    private String cityCode;
    private String cityName;
    private BigDecimal qieKeMoney;
    private BigDecimal billCost;
    private BigDecimal hotelCost;
    private BigDecimal changeCost;
    private BigDecimal serviceCost;
    private BigDecimal prepaymentDiscount;
    private BigDecimal toPayDiscount;
    private BigDecimal settlementPrice;
    private BigDecimal userCost;
    private BigDecimal availableMoney;
    private BigDecimal ticketMoney;
    private BigDecimal wechatPayMoney;
    private BigDecimal aliPayMoney;
    private String isFreeze;
    private Date createTime;
    private Date updateTime;

    private Boolean feedbackIng;

    public Boolean getFeedbackIng() {
        return feedbackIng;
    }

    public void setFeedbackIng(Boolean feedbackIng) {
        this.feedbackIng = feedbackIng;
    }

    public BigDecimal getQieKeMoney() {
        return qieKeMoney;
    }

    public void setQieKeMoney(BigDecimal qieKeMoney) {
        this.qieKeMoney = qieKeMoney;
    }

    public String getSettleStatusName() {
        return settleStatusName;
    }

    public void setSettleStatusName(String settleStatusName) {
        this.settleStatusName = settleStatusName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public BigDecimal getHotelCost() {
        return hotelCost;
    }

    public void setHotelCost(BigDecimal hotelCost) {
        this.hotelCost = hotelCost;
    }

    public String getCheckStatusName() {
        return checkStatusName;
    }

    public void setCheckStatusName(String checkStatusName) {
        this.checkStatusName = checkStatusName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getBillCost() {
        return billCost;
    }

    public void setBillCost(BigDecimal billCost) {
        this.billCost = billCost;
    }

    public BigDecimal getChangeCost() {
        return changeCost;
    }

    public void setChangeCost(BigDecimal changeCost) {
        this.changeCost = changeCost;
    }

    public BigDecimal getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(BigDecimal serviceCost) {
        this.serviceCost = serviceCost;
    }

    public BigDecimal getPrepaymentDiscount() {
        return prepaymentDiscount;
    }

    public void setPrepaymentDiscount(BigDecimal prepaymentDiscount) {
        this.prepaymentDiscount = prepaymentDiscount;
    }

    public BigDecimal getToPayDiscount() {
        return toPayDiscount;
    }

    public void setToPayDiscount(BigDecimal toPayDiscount) {
        this.toPayDiscount = toPayDiscount;
    }

    public BigDecimal getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(BigDecimal settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    public BigDecimal getUserCost() {
        return userCost;
    }

    public void setUserCost(BigDecimal userCost) {
        this.userCost = userCost;
    }

    public BigDecimal getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(BigDecimal availableMoney) {
        this.availableMoney = availableMoney;
    }

    public BigDecimal getTicketMoney() {
        return ticketMoney;
    }

    public void setTicketMoney(BigDecimal ticketMoney) {
        this.ticketMoney = ticketMoney;
    }

    public BigDecimal getWechatPayMoney() {
        return wechatPayMoney;
    }

    public void setWechatPayMoney(BigDecimal wechatPayMoney) {
        this.wechatPayMoney = wechatPayMoney;
    }

    public BigDecimal getAliPayMoney() {
        return aliPayMoney;
    }

    public void setAliPayMoney(BigDecimal aliPayMoney) {
        this.aliPayMoney = aliPayMoney;
    }

    public String getIsFreeze() {
        return isFreeze;
    }

    public void setIsFreeze(String isFreeze) {
        this.isFreeze = isFreeze;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}