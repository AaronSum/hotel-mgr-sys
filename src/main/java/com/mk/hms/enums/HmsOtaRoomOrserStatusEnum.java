package com.mk.hms.enums;

/**
 * 客单状态枚举
 * @author hdy
 *
 */
public enum HmsOtaRoomOrserStatusEnum {

	Ok(100, "订单提交"),
	Wait(110, "等待前台支付"),
	Paid(120, "订单支付"),
	Reserve(140, "预订完成"),
	CheckinOnLine(160, "网上入住"),
	Checkin(180, "入住"),
	Checkout(200, "离店"),
	Cancel(510, "等待退款"),
	CancelBySys(511, "系统取消"),
	Cancel4Refund(512, "已退款"),
	Cancel4NonRefund(513, "无需退款"),
	Absent(520, "未到");
	
	private int value;
	private String text;

	private HmsOtaRoomOrserStatusEnum(int value, String text) {
		this.value = value;
		this.text = text;
	}

	public int getValue() {
		return value;
	}

	public String getText() {
		return text;
	}
}
