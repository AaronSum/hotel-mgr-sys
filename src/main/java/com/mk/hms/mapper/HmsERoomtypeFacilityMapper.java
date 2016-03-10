package com.mk.hms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mk.hms.model.HmsERoomtypeFacilityModel;
import com.mk.hms.model.HmsTRoomtypeFacilityModel;

/**
 * 房型设施设备mapper接口
 * Created by qhdhiman on 2015/5/3.
 */
public interface HmsERoomtypeFacilityMapper {	
	/**
	 * 插入一条数据
	 * @param roomTypeId 酒店id
	 * @param facId 商圈id
	 * @return 执行成功结果条数
	 */
	@Insert("insert into e_roomtype_facility(roomtypeid, facId) values(#{roomTypeId}, #{facId})")
	int add(@Param("roomTypeId") long roomTypeId, @Param("facId") long facId);
	
	/**
	 * 根据id删除设备设置
	 * @param id
	 * @return
	 */
	@Delete("delete from e_roomtype_facility where id = #{id}")
	int deleteById(@Param("id") long id);
	
	/**
	 * 根据房型编号和设备编号删除数据
	 * @param roomTypeId
	 * @param facId
	 * @return
	 */
	@Delete("delete from e_roomtype_facility where roomtypeid = #{roomTypeId} and facId = #{facId}")
	int delete(@Param("roomTypeId") long roomTypeId,@Param("facId") long facId);
	
	/**
	 * 插入一条数据
	 * @param roomTypeId 酒店id
	 * @param facId 商圈id
	 * @return 执行成功结果条数
	 */
	@Insert("insert into t_roomtype_facility (roomtypeid, facId) values(#{roomTypeId}, #{facId})")
	@Options(useGeneratedKeys = true, keyProperty = "id", flushCache=true)
	int addT(HmsTRoomtypeFacilityModel tRoomtypeFacilityModel);
	
	/**
	 * 根据id删除设备设置
	 * @param id
	 * @return
	 */
	@Delete("delete from t_roomtype_facility where id = #{id}")
	int deleteTById(@Param("id") long id);
	
	/**
	 * 根据房型编号和设备编号删除数据
	 * @param roomTypeId
	 * @param facId
	 * @return
	 */
	@Delete("delete from t_roomtype_facility where roomtypeid = #{roomTypeId} and facId = #{facId}")
	int deleteT(@Param("roomTypeId") long roomTypeId,@Param("facId") long facId);
	

	
	/**
	 * 根据房型编号删除数据
	 * @param roomTypeId
	 * @return
	 */
	@Delete("delete from t_roomtype_facility where roomtypeid = #{roomTypeId}")
	int deleteTByRoomtypeId(@Param("roomTypeId") long roomTypeId);
	
	/**
	 * 根据房型ID获取所有数据
	 * @param roomTypeId
	 * @return
	 */
	@Select("select * from e_roomtype_facility where roomtypeid = #{roomTypeId}")
    List<HmsERoomtypeFacilityModel> findsByRoomtypeId(@Param("roomTypeId") long roomTypeId);
	
	/**
	 * 房型编码和设备编码获取一条数据
	 * @param roomTypeId 房型编码
	 * @param facId 设备编码
	 * @return
	 */
	@Select("select * from e_roomtype_facility where roomtypeid = #{roomTypeId} and facId = #{facId}")
    HmsERoomtypeFacilityModel findOne(@Param("roomTypeId") long roomTypeId,@Param("facId") long facId); 
}
