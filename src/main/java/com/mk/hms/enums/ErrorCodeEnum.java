package com.mk.hms.enums;

/**
 * 错误编码
 * @author admin
 *
 */
public enum ErrorCodeEnum {

	ERROR_USER_1001(1001, "请完善用户信息"),
	ERROR_USER_1002(1002, "验证码和手机号不匹配"),
	ERROR_USER_1003(1003, "该用户已注册"),
	
	ERROR_SESSION_2001(2001, "登录超时，请重新登录");
	
	private final int value;
	private final String text;
	
	private ErrorCodeEnum(int value,String text){
		this.value=value;
		this.text=text;
	}

	public int getValue() {
		return value;
	}

	public String getText() {
		return text;
	}
	
	public static ErrorCodeEnum getByValue(int value){
		for (ErrorCodeEnum temp : ErrorCodeEnum.values()) {
			if(temp.getValue() == value){
				return temp;
			}
		}
		
		return null;
	}
	
	
}
