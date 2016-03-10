package com.mk.hms.enums;

/**
 * Created by qhdhiman on 2015/5/8.
 */
public enum PricePolicyEnum {
	price(1,"price"),//固定值
	subprice(2,"subprice"),//固定下调
	subper(3,"subper");//百分比下调
	private final Integer id;
	private final String name;
	private PricePolicyEnum(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public static PricePolicyEnum findById(Integer id){
		for (PricePolicyEnum fte : PricePolicyEnum.values()) {
			if(fte.getId().equals(id)){
				return fte;
			}
		}
		return null;
	}
	
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return id.toString();
	}
}
