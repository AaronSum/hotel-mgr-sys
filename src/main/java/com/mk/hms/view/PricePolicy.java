package com.mk.hms.view;

import java.math.BigDecimal;

import com.mk.hms.enums.PricePolicyEnum;

public class PricePolicy {
	private PricePolicyEnum typeEnum;
	private BigDecimal value;

	public PricePolicyEnum getTypeEnum() {
		return typeEnum;
	}

	public void setTypeEnum(PricePolicyEnum typeEnum) {
		this.typeEnum = typeEnum;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (typeEnum.equals(PricePolicyEnum.price)) {
			result = 3;
		} else if (typeEnum.equals(PricePolicyEnum.subprice)) {
			result = 5;
		} else if (typeEnum.equals(PricePolicyEnum.subper)) {
			result = 7;
		}
		result = prime * result + ((typeEnum == null) ? 1 : result);
		result = prime
				* result
				+ ((value == null) ? 0 : value.multiply(new BigDecimal(1000))
						.toBigInteger().intValue());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PricePolicy other = (PricePolicy) obj;
		if (typeEnum != other.typeEnum)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
