package com.mk.hms.enums;

/**
 * hms 可用标示
 * @author hdy
 *
 */
public enum HmsTfacilityTypeEnum {

	normal(1, "普通设置"), other(2, "其他设置"), Bathroom(3, "卫浴");
	
	private int value;
	private String text;
	
	private HmsTfacilityTypeEnum(int value, String text) {
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
