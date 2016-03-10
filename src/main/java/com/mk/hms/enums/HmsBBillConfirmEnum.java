package com.mk.hms.enums;

/**
 * hms结算确认状态枚举
 * @author hdy
 *
 */
public enum HmsBBillConfirmEnum {

	Unconfirm(1, "未确认"), Confirmed(2, "已确认"), SysConfirm(3, "系统确认");

	private int value;
	private String text;

	private HmsBBillConfirmEnum(int value, String text) {
		this.value = value;
		this.text = text;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
