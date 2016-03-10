package com.mk.hms.enums;

/**
 *
 * @author admin
 *
 */
public enum BillFeedbackTypeEnum {

	SPECIAL(1, "特价账单"),
	ALL_IN(2, "综合账单"),;

	private final int code;
	private final String value;

	private BillFeedbackTypeEnum(int code, String value){
		this.code=code;
		this.value=value;
	}

	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}
	
	public static BillFeedbackTypeEnum getByCode(int code){
		for (BillFeedbackTypeEnum temp : BillFeedbackTypeEnum.values()) {
			if(temp.getCode() == code){
				return temp;
			}
		}
		
		return null;
	}
	
	
}
