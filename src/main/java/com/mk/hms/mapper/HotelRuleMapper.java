package com.mk.hms.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.mk.hms.model.HMSHotelRuleModel;

public interface HotelRuleMapper {
	
	public List<HMSHotelRuleModel> getHMSHotelRuleList(@Param("state") Integer state, @Param("curDate") String curDate, @Param("nextDate") String nextDate);
	
	public int insertHotelRule(@Param("hotelId") Long hotelId, @Param("rulecode") Integer rulecode, @Param("isthreshold") String isthreshold, @Param("effectdate") String effectdate);
	
	int updateState(@Param("hotelId") Long hotelId, @Param("state") Integer state, @Param("preState") Integer preState);
	
	int updateThotel(@Param("hotelId") Long hotelId, @Param("rulecode") Integer rulecode, @Param("isthreshold") String isthreshold);
	
	int updateEhotel(@Param("hotelId") Long hotelId, @Param("rulecode") Integer rulecode, @Param("isthreshold") String isthreshold);
}