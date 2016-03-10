package com.mk.hms.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 房型
 * @author qhdhiman 2015/5/2
 */
@Entity
@Table(name = "t_roomtype")
public class HmsTRoomtypeModel {

	private long id;
    private long thotelId;
    private long ehotelId;
    private String name;
    private String pms;
    private long bedNum;
    private long roomNum;
    private BigDecimal cost;

    @Id
    @Column(name = "id", nullable = false, length = 20)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    @Column(name = "thotelId", nullable = true, length = 20)
    public long getThotelId() {
        return thotelId;
    }

    public void setThotelId(long thotelId) {
        this.thotelId = thotelId;
    }

    @Column(name = "ehotelId", nullable = true, length = 20)
    public long getEhotelId() {
        return ehotelId;
    }

    public void setEhotelId(long ehotelId) {
        this.ehotelId = ehotelId;
    }

    @Column(name = "name", nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "pms", nullable = false, length = 20)
    public String getPms() {
        return pms;
    }

    public void setPms(String pms) {
        this.pms = pms;
    }

    @Column(name = "bedNum", nullable = true, length = 20)
    public long getBedNum() {
        return bedNum;
    }

    public void setBedNum(long bedNum) {
        this.bedNum = bedNum;
    }

    @Column(name = "roomNum", nullable = true, length = 20)
    public long getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(long roomNum) {
        this.roomNum = roomNum;
    }

    @Column(name = "cost", nullable = true, length = 10)
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
