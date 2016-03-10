package com.mk.hms.enums;

/**
 * 错误编码
 * @author admin
 *
 */
public enum BillOrderWeekCheckStatusEnum {

	/*
	`check_status` int(11) default '0'
	comment '账单状态 0：初始化 1，待确认 2，审核中 （如果觉得有账单问题提交审核）3, 审核通过 4，已确认 5，已结算',
	 */

	INIT(1, "初始化"),
	TO_CONFIRM(2, "待审核"),
	TO_AUDIT(3, "审拒绝"),
	AUDITED(4, "审核通过"),
	CONFIRM(5, "待结算"),
	SETTLED(6, "已结算");

	private final int code;
	private final String value;

	private BillOrderWeekCheckStatusEnum(int code, String value){
		this.code=code;
		this.value=value;
	}

	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}
	
	public static BillOrderWeekCheckStatusEnum getByCode(int code){
		for (BillOrderWeekCheckStatusEnum temp : BillOrderWeekCheckStatusEnum.values()) {
			if(temp.getCode() == code){
				return temp;
			}
		}
		
		return null;
	}
	
	
}
