package com.mk.hms.enums;

public enum HmsRuleCodeEnum {
	
	A_RULE(1001,"A规则"),
	B_RULE(1002,"B规则");
	
	private final int ruleCode;
	private final String ruleName;
	
	private HmsRuleCodeEnum(int ruleCode, String ruleName){
		this.ruleCode = ruleCode;
		this.ruleName = ruleName;
	}

	/**
	 * @return the ruleCode
	 */
	public int getRuleCode() {
		return ruleCode;
	}

	/**
	 * @return the ruleName
	 */
	public String getRuleName() {
		return ruleName;
	}
}
