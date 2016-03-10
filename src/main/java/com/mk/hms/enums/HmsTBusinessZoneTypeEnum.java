package com.mk.hms.enums;

/**
 * 周边信息类型
 * @author hdy
 *
 */
public enum HmsTBusinessZoneTypeEnum {

	Circle(10, "商圈信息"), Landmarks(11, "地标信息"), Subway(12, "地铁沿线"), University(13, "大学周边");
	
	private int value;
	private String text;
	
	private HmsTBusinessZoneTypeEnum(int value, String text) {
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
