package com.mk.hms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mk.hms.model.HMSHotelRuleModel;


/**
 * h_hotel_rule
 * */
public interface HmsHotelRuleMapper {

	@Select("SELECT * FROM h_hotel_rule WHERE effectdate > #{curDate} AND effectdate < #{nextDate} AND state = 0")
	public List<HMSHotelRuleModel> getHMSHotelRuleList(@Param("curDate") String curDate, @Param("nextDate") String nextDate);
	
	
	@Insert("INSERT INTO h_hotel_rule(hotelid, rulecode, isthreshold, effectdate, state, createtime) "
			+ "values(#{hotelId}, #{rulecode}, #{isthreshold}, #{effectdate}, 0, NOW())")
	public int insertHotelRule(@Param("hotelId") Long hotelId, @Param("rulecode") Integer rulecode, @Param("isthreshold") String isthreshold, @Param("effectdate") String effectdate);
	
	@Update("UPDATE h_hotel_rule SET state = #{state} WHERE hotelid = #{hotelId} AND state = #{preState}  AND effectdate IS NOT NULL")
	int updateState(@Param("hotelId") Long hotelId, @Param("state") Integer state, @Param("preState") Integer preState);
	
	@Update("UPDATE t_hotel SET rulecode= #{rulecode},isthreshold= #{isthreshold} WHERE  id= #{hotelId}")
	int updateThotel(@Param("hotelId") Long hotelId, @Param("rulecode") Integer rulecode, @Param("isthreshold") String isthreshold);
	
	@Update("UPDATE e_hotel SET rulecode= #{rulecode},isthreshold= #{isthreshold} WHERE  id= #{hotelId}")
	int updateEhotel(@Param("hotelId") Long hotelId, @Param("rulecode") Integer rulecode, @Param("isthreshold") String isthreshold);
}
