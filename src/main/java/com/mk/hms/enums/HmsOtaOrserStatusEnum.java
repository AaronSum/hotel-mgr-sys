package com.mk.hms.enums;

/**
 * 客单状态枚举
 * @author hdy
 *
 */
public enum HmsOtaOrserStatusEnum {

	Ok(100, "预定"),
	Wait(110, "等待确认"),
	Paid(120, "等待支付"),
	Reserve(140, "预订完成"),
	CheckinOnLine(160, "网上入住"),
	Checkin(180, "入住"),
	Pending(190, "挂帐"),
	Checkout(200, "离店"),
	Cancel(510, "等待退款"),
	CancelBySys(511, "订单过期"),
	Cancel4Refund(512, "已退款"),
	Cancel4NonRefund(513, "用户取消"),
	CancelByPms(514, "PMS取消订单"),
	Absent(520, "未到");
	
	private int value;
	private String text;

	private HmsOtaOrserStatusEnum(int value, String text) {
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
