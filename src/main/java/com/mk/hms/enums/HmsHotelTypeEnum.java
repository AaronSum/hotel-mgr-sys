package com.mk.hms.enums;

public enum HmsHotelTypeEnum {
//	旅馆／主题酒店／精品酒店／公寓酒店／
//	招待所／客栈
	HOTEL(1,"旅馆"),THEMEDHOTEL(2,"主题酒店"),PLAZAHOTEL(3,"精品酒店"),APARTMENTHOTEL(4,"公寓"),
	HOSTELS(5,"招待所"),INNER(6,"客栈");
	private HmsHotelTypeEnum(int value, String text) {
		this.value = value;
		this.text = text;
	}
	private int value;
	private String text;
	public int getValue() {
		return value;
	}
	public String getText() {
		return text;
	}
	
}
