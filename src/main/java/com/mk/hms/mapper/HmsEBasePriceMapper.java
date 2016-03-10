package com.mk.hms.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mk.hms.model.HmsEBasePriceModel;
import com.mk.hms.model.HmsTBasePriceModel;
import com.mk.hms.utils.ContentUtils;

/**
 * 基础房价mapper接口
 * Created by qhdhiman on 2015/5/4.
 */
public interface HmsEBasePriceMapper {
	
	/**
	 * 插入一条新数据
	 * @param eBasePriceModel
	 * @return
	 */
	@Insert("insert into e_baseprice(roomTypeId,price,subprice,subper,updateTime) values(#{roomTypeId},#{price},#{subprice},#{subper},#{updateTime})")
	@Options(useGeneratedKeys = true, keyProperty = "id", flushCache=true)
	int add(HmsEBasePriceModel eBasePriceModel);
	
	/**
	 * 根据id删除设备设置
	 * @param id
	 * @return
	 */
	@Delete("delete from e_baseprice where id = #{id}")
	int deleteById(@Param("id") long id);
	

	/**
	 * 根据房型ID获取房价
	 * @param roomTypeId
	 * @return
	 */
	@Select("select " + ContentUtils.T_BASEPRICE_FIELDS + " from t_baseprice where roomTypeId = #{roomTypeId}")
	HmsTBasePriceModel findT(@Param("roomTypeId") long roomTypeId);
	
	/**
	 * 插入一条新数据
	 * @param eBasePriceModel
	 * @return
	 */
	@Insert("insert into t_baseprice(roomTypeId,price,subprice,subper,updateTime) values(#{roomTypeId},#{price},#{subprice},#{subper},#{updateTime})")
	@Options(useGeneratedKeys = true, keyProperty = "id", flushCache=true)
	int addT(HmsTBasePriceModel eBasePriceModel);
	
	/**
	 * 根据id删除设备设置
	 * @param id
	 * @return
	 */
	@Delete("delete from t_baseprice where id = #{id}")
	int deleteTById(@Param("id") long id);
	
	/**
	 * 根据房型编号删除数据
	 * @param roomTypeId
	 * @param facId
	 * @return
	 */
	@Delete("delete from e_baseprice where roomTypeId = #{roomTypeId}")
	int delete(@Param("roomTypeId") long roomTypeId);
	
	/**
	 * 根据房型ID获取房价
	 * @param roomTypeId
	 * @return
	 */
	@Select("select " + ContentUtils.E_BASEPRICE_FIELDS + " from e_baseprice where roomTypeId = #{roomTypeId}")
	HmsEBasePriceModel find(@Param("roomTypeId") long roomTypeId);

	/**
	 * 更新一条新数据
	 * @param eBasePriceModel
	 * @return
	 */
	@Update("update e_baseprice set price=#{price},subprice=#{subprice},subper=#{subper},updateTime=#{updateTime} where roomTypeId=#{roomTypeId} ")
	@Options(useGeneratedKeys = true, keyProperty = "id", flushCache=true)
	int update(HmsEBasePriceModel eBasePriceModel);
	
	/**
	 * 更新一条新数据
	 * @param eBasePriceModel
	 * @return
	 */
	@Update("update t_baseprice set price=#{price},subprice=#{subprice},subper=#{subper},updateTime=#{updateTime} where roomTypeId=#{roomTypeId} ")
	@Options(useGeneratedKeys = true, keyProperty = "id", flushCache=true)
	int updateT(HmsTBasePriceModel eBasePriceModel);
 
}
