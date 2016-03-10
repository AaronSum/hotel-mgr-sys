package com.mk.hms.enums;

/**
 * hms结算审核状态枚举
 * 
 * @author hdy
 *
 */
public enum HmsBBillConfirmCheckEnum {

	Init(1, "未审核"), Checking(2, "审核中"), UnChecked(3, "未通过"), Checked(4, "已审核");

	private int value;
	private String text;

	private HmsBBillConfirmCheckEnum(int value, String text) {
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
