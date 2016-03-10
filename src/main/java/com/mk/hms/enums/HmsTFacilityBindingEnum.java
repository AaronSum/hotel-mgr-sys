package com.mk.hms.enums;

/**
 * 酒店设置匹配类型
 * @author hdy
 *
 */
public enum HmsTFacilityBindingEnum {

	Hotel("00000001", "酒店设置"), Room("00000010", "房间设置");

	private String value;
	private String text;

	private HmsTFacilityBindingEnum(String value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
