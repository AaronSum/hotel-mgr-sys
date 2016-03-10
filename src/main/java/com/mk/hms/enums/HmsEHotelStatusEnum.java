package com.mk.hms.enums;

/**
 *酒店状态枚举
 * @author hdy
 *
 */
public enum HmsEHotelStatusEnum {

	/**初始化状态*/
	Init(-1, "初始化"),
	/**未初次审核*/
	UNInitial(0, "未初次审核"),
	/**正在初次审核*/
//	Initial(1, "正在初次审核"),
//	/**表示店长编辑中（未经过上限）*/
//	ManagerEdite(2, "店长编辑中（未经过上限）"),
	/**提交审核中*/
	Submit(3, "提交审核中"),
	/**表示店长正在你编辑中（已进行过一次上线）*/
	ManagerEditing(4, "店长正在编辑中（已进行过一次上线）"),
	/**表示编辑中（已进行过一次上线）*/
	Editing(5, "审核中（已进行过一次上线）");
	
	private int value;
	private String text;
	
	private HmsEHotelStatusEnum(int value, String text) {
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
