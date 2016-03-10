package com.mk.hms.enums;

/**
 *
 * @author admin
 *
 */
public enum BillFeedbackStatusEnum {

	/*
	`status` int(11) DEFAULT NULL COMMENT '状态\r\n1-已提交,\r\n2-处理中,\r\n3-待结算,\r\n4-处理完成',
	 */

	SUBMIT(1, "已提交"),
	DOING(2, "处理中"),
	TO_SETTLE(3, "待结算"),
	SETTLED(4, "处理完成");

	private final int code;
	private final String value;

	private BillFeedbackStatusEnum(int code, String value){
		this.code=code;
		this.value=value;
	}

	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}
	
	public static BillFeedbackStatusEnum getByCode(int code){
		for (BillFeedbackStatusEnum temp : BillFeedbackStatusEnum.values()) {
			if(temp.getCode() == code){
				return temp;
			}
		}
		
		return null;
	}
	
	
}
