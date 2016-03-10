package com.mk.hms.enums;

/**
 * hms结算审核状态枚举
 * 
 * @author cwwc
 *
 */
public enum HmsSimpleBBillConfirmCheckEnum {
	
	Init(0, "新创建"), Confirming(1, "待确认"), Disagreed(2, "有异议"), Checking(3, "待审核"), Checked(5, "已审核"), CheckFailed(4, "审核退回"), Confirmed(6, "已确认"), Finished(7, "已结算"); //需求变更后的简化版
//	初始化(0)——账期内数据初始化的状态，等待结算员审核；
//	待确认(1)——账期内对账单等待酒店确认时的状态；
//	有异议（2）——酒店对对账单金额提出异议的状态；
//	待审核(3)——结算员酒店对对账单金额提出异议的状态；
//	审核退回(4) --结算经理退回结算员的状态
//	已审核(5)——结算经理审核确认后状态；
//	已确认(6)——酒店账期内确认对账单后的状态；
//	已结算(7)——眯客财务确认后的状态；
//	已拆分或调整(8)——账单金额调整或拆分完毕；
//	拆分或调整审核退回(9)——结算经理审核调整或拆分金额退回；
//	已拆分或调整(10)——拆分或调整审核通过状态；
	
	private int value;
	private String text;

	private HmsSimpleBBillConfirmCheckEnum(int value, String text) {
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

	public static HmsSimpleBBillConfirmCheckEnum getEnumByCode(Integer code){
		for(HmsSimpleBBillConfirmCheckEnum hmsSimpleBBillConfirmCheckEnum : HmsSimpleBBillConfirmCheckEnum.values()){
			if(hmsSimpleBBillConfirmCheckEnum.getValue() == code){
				return hmsSimpleBBillConfirmCheckEnum;
			}
		}
		return null;
	}
}
