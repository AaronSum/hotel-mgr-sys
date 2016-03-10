package com.mk.hms.mapper;

import java.util.List;

import com.mk.hms.model.HomeUtilMode;

public interface HomeMapper {
	
	public HomeUtilMode querySubsidy(List<Long> list);

}
