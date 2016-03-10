package com.mk.hms.enums;

/**
 * 酒店审核log，审核类型枚举
 * @author hdy
 *
 */
public enum HmsTHotelOperateLogCheckTypeEnum {

	Init(1, "初次审核"), Update(2, "更新审核"), Online(3, "上线"), Offline(4, "下线"), Rates(5, "修改房价");
	
	private int value;
	private String text;
	
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

	private HmsTHotelOperateLogCheckTypeEnum(int value, String text) {
		this.value = value;
		this.text = text;
	}
}
