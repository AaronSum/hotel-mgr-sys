package com.mk.hms.model;

import java.math.BigDecimal;
import java.util.Date;

public class BillOrderDetail {

    private Long id;
    private Long hotelId;
    private String hotelName;
    private Long orderId;
    private Long orderType;
    private Date beginTime;
    private Date endTime;
    private BigDecimal totalPrice;
    private BigDecimal serviceCost;
    private Long ruleCode;
    private Long spreadUser;
    private Integer invalidReason;
    private Date checkinTime;
    private BigDecimal prepaymentDiscount;
    private BigDecimal toPayDiscount;
    private Date orderCreateTime;
    private Date billTime;
    private BigDecimal settlementPrice;
    private BigDecimal userCost;
    private BigDecimal availableMoney;
    private BigDecimal ticketMoney;
    private BigDecimal wechatPayMoney;
    private BigDecimal aliPayMoney;
    private Integer promoId;
    private Long billOrderWeekId;
    private String cityCode;
    private String cityName;
    private Date createTime;
    private Date updateTime;

    private String roomTypeName;
    private String roomNo;
    private Integer dayNumber;

    private Date checkoutTime;


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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderType() {
        return orderType;
    }

    public void setOrderType(Long orderType) {
        this.orderType = orderType;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(BigDecimal serviceCost) {
        this.serviceCost = serviceCost;
    }

    public Long getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(Long ruleCode) {
        this.ruleCode = ruleCode;
    }

    public Long getSpreadUser() {
        return spreadUser;
    }

    public void setSpreadUser(Long spreadUser) {
        this.spreadUser = spreadUser;
    }

    public Integer getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(Integer invalidReason) {
        this.invalidReason = invalidReason;
    }

    public Date getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(Date checkinTime) {
        this.checkinTime = checkinTime;
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

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public Date getBillTime() {
        return billTime;
    }

    public void setBillTime(Date billTime) {
        this.billTime = billTime;
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

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public Long getBillOrderWeekId() {
        return billOrderWeekId;
    }

    public void setBillOrderWeekId(Long billOrderWeekId) {
        this.billOrderWeekId = billOrderWeekId;
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

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public Date getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(Date checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }


    public BigDecimal getBillCost() {
        BigDecimal billCost = BigDecimal.ZERO;
        billCost = billCost.add(this.getSettlementPrice() == null ? BigDecimal.ZERO : this.getSettlementPrice());
        billCost = billCost.add(this.getPrepaymentDiscount() == null ? BigDecimal.ZERO : this.getPrepaymentDiscount());
        billCost = billCost.add(this.getToPayDiscount() == null ? BigDecimal.ZERO : this.getToPayDiscount());
        billCost = billCost.subtract(this.getServiceCost() == null ? BigDecimal.ZERO : this.getServiceCost());
        return billCost;
    }

}