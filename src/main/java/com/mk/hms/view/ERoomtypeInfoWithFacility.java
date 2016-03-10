package com.mk.hms.view;

import java.util.List;

import com.mk.hms.model.ERoomtypeFacility;
import com.mk.hms.model.ERoomtypeInfo;

/**
 * 房型信息扩展累
 * @author hdy
 *
 */
public class ERoomtypeInfoWithFacility extends ERoomtypeInfo {

	private List<ERoomtypeFacility> facilities; // 设备设施

	public List<ERoomtypeFacility> getFacilities() {
		return facilities;
	}

	public void setFacilities(List<ERoomtypeFacility> facilities) {
		this.facilities = facilities;
	}

}
