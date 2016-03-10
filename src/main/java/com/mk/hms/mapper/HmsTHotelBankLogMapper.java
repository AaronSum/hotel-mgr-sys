package com.mk.hms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mk.hms.model.HmsTHotelBankLogModel;

/**
 * 酒店银行账号信息
 * @author qhdhiman
 *
 */
public interface HmsTHotelBankLogMapper {

	/**
	 * 获取某个酒店的所有操作记录
	 * @param hotelId
	 * @return
	 */
	@Select("select * from t_hotel_bank_log where hotelId = #{hotelId}")
	List<HmsTHotelBankLogModel> finds(@Param("hotelId") long hotelId);
	
	/**
	 * 增加一条新的酒店操作记录
	 * @param hotelBankModel
	 * @return
	 */
	@Insert("insert into t_hotel_bank_log (hotelId,userCode,userName,oldVal,newVal,createTime) values (#{hotelId},#{userCode},#{userName},#{oldVal},#{newVal},#{createTime})")
	@Options(useGeneratedKeys = true, keyProperty = "id", flushCache=true)
	int insert(HmsTHotelBankLogModel hotelBankLogModel);
}
