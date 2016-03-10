package com.mk.hms.enums;

/**
 *酒店与pms对接状态枚举
 * @author hdy
 *
 */
public enum HmsEHotelPmsStatusEnum {

	
	/**初始化未对接*/
	Init(0, "初始化未对接"),
	/**已匹配到，同步中*/
	Match(1, "已匹配到，同步中"),
	/**已成功同步了房型、房价信息*/
	Synchronous(2, "已成功同步了房型、房价信息");
	
	private int value;
	private String text;
	
	private HmsEHotelPmsStatusEnum(int value, String text) {
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
