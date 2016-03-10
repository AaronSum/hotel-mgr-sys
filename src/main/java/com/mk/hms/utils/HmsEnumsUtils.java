package com.mk.hms.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.EnumUtils;

import com.mk.hms.enums.HmsOtaOrserStatusEnum;

/**
 * 枚举使用工具类
 * @author hdy
 *
 */
public class HmsEnumsUtils extends EnumUtils{

	/**
	 * 获取订单状态对应文本说明
	 * @param code 状态编码
	 * @return 状态文本说明
	 */
	public static String getText4HmsOtaOrserStatusEnum(int code) {
		String text = "";
		for (HmsOtaOrserStatusEnum e : HmsOtaOrserStatusEnum.values()) {
			if (e.getValue() == code) {
				return e.getText();
			}
		}
		if (StringUtils.isNotBlank(text)) {
			text = "--";
		}
		return text;
	}
}
