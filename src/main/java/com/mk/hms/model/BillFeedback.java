package com.mk.hms.model;

import java.math.BigDecimal;
import java.util.Date;

public class BillFeedback {
    private Long id;
    private Integer status;
    private Integer type;
    private Long fromBill;
    private Long toBill;
    private Long hotelId;
    private String hotelName;
    private BigDecimal hotelChange;
    private String hotelReason;
    private BigDecimal mkChange;
    private String mkReason;
    private Date createTime;
    private String createBy;
    private String updateBy;

    private String typeName;
    private String statusName;
    private BigDecimal billCost;
    private Date beginTime;
    private Date endTime;


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public BigDecimal getBillCost() {
        return billCost;
    }

    public void setBillCost(BigDecimal billCost) {
        this.billCost = billCost;
    }
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getFromBill() {
        return fromBill;
    }

    public void setFromBill(Long fromBill) {
        this.fromBill = fromBill;
    }

    public Long getToBill() {
        return toBill;
    }

    public void setToBill(Long toBill) {
        this.toBill = toBill;
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

    public BigDecimal getHotelChange() {
        return hotelChange;
    }

    public void setHotelChange(BigDecimal hotelChange) {
        this.hotelChange = hotelChange;
    }

    public String getHotelReason() {
        return hotelReason;
    }

    public void setHotelReason(String hotelReason) {
        this.hotelReason = hotelReason;
    }

    public BigDecimal getMkChange() {
        return mkChange;
    }

    public void setMkChange(BigDecimal mkChange) {
        this.mkChange = mkChange;
    }

    public String getMkReason() {
        return mkReason;
    }

    public void setMkReason(String mkReason) {
        this.mkReason = mkReason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

}