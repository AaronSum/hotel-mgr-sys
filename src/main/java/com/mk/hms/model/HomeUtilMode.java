package com.mk.hms.model;

import java.math.BigDecimal;

public class HomeUtilMode {
	// 订单数
	private int num;
	// 补贴金额
	private BigDecimal subsidy;
	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}
	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}
	/**
	 * @return the subsidy
	 */
	public BigDecimal getSubsidy() {
		return subsidy;
	}
	/**
	 * @param subsidy the subsidy to set
	 */
	public void setSubsidy(BigDecimal subsidy) {
		this.subsidy = subsidy;
	}
	
}
