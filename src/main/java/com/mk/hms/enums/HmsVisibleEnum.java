package com.mk.hms.enums;

/**
 * hms 可用标示
 * @author hdy
 *
 */
public enum HmsVisibleEnum {

	T("T", "是"), F("F", "否");
	
	private String value;
	private String text;
	
	private HmsVisibleEnum(String value, String text) {
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
