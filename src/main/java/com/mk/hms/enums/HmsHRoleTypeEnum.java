package com.mk.hms.enums;

/**
 * 角色分类枚举
 * @author hdy
 *
 */
public enum HmsHRoleTypeEnum {
	
	Founder(1, "创始人"), Waiter(2, "前台"), admin(3, "管理员");
	
	private int value;
	private String text;
	
	private HmsHRoleTypeEnum(int value, String text) {
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
