package com.mk.hms.view;

import java.math.BigDecimal;

/**
 * 保存 房型信息 参数实体
 * @author hdy
 *
 */
public class SaveRoomType {

	private long roomTypeId;
	private BigDecimal maxArea;
	private long bedType;
	private String bedSize;
	private String pics;
	private String addFacs;
	private String minusFacs;

	public long getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(long roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public BigDecimal getMaxArea() {
		return maxArea;
	}

	public void setMaxArea(BigDecimal maxArea) {
		this.maxArea = maxArea;
	}

	public long getBedType() {
		return bedType;
	}

	public void setBedType(long bedType) {
		this.bedType = bedType;
	}

	public String getBedSize() {
		return bedSize;
	}

	public void setBedSize(String bedSize) {
		this.bedSize = bedSize;
	}

	public String getPics() {
		return pics;
	}

	public void setPics(String pics) {
		this.pics = pics;
	}

	public String getAddFacs() {
		return addFacs;
	}

	public void setAddFacs(String addFacs) {
		this.addFacs = addFacs;
	}

	public String getMinusFacs() {
		return minusFacs;
	}

	public void setMinusFacs(String minusFacs) {
		this.minusFacs = minusFacs;
	}
}
