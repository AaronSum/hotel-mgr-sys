package com.mk.hms.view;

import com.mk.hms.model.EHotelWithBLOBs;

/**
 * 开业时间、装修时间使用 字符串
 * @author hdy
 *
 */
public class EHotelWithStringDate extends EHotelWithBLOBs {

	private String fmtOpenTime;
	private String fmtRepairTime;

	public String getFmtOpenTime() {
		return fmtOpenTime;
	}

	public void setFmtOpenTime(String fmtOpenTime) {
		this.fmtOpenTime = fmtOpenTime;
	}

	public String getFmtRepairTime() {
		return fmtRepairTime;
	}

	public void setFmtRepairTime(String fmtRepairTime) {
		this.fmtRepairTime = fmtRepairTime;
	}

}
