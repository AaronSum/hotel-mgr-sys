package com.mk.hms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mk.hms.model.HmsERoomtypeInfoModel;
import com.mk.hms.model.HmsTRoomtypeInfoModel;

/**
 * hms房型信息mapper接口
 * Created by qhdhiman on 2015/5/3.
 */
public interface HmsERoomtypeInfoMapper {
    /**
     * 获取全部房型信息
     * @return 房型信息列表
     */
    @Select("select * from e_roomtype_info")
    List<HmsERoomtypeInfoModel> finds();

    /**
     * 根据房型Id获取房型信息
     * @param roomtypeId 房型Id
     * @return 房型信息列表
     */
    @Select("select * from e_roomtype_info where roomtypeid = #{roomtypeId}")
    HmsERoomtypeInfoModel findByRoomtypeId(@Param("roomtypeId") long roomtypeId);    
    
    /**
     * 增加信息的房型信息
     * @param eRoomtypeInfo
     * @return
     */
	@Insert("insert into e_roomtype_info(roomtypeid, minarea, maxarea, pics,bedtype,bedsize) values (#{roomTypeId}, #{minArea}, #{maxArea}, #{pics}, #{bedType}, #{bedSize})")
	@Options(useGeneratedKeys = true, keyProperty = "id", flushCache=true)
	int add(HmsERoomtypeInfoModel eRoomtypeInfo);
	
	/**
	 * 更新房型信息
	 * @param eRoomtypeInfo
	 * @return
	 */
	@Update("update e_roomtype_info set minarea = #{minArea}, maxarea = #{maxArea}, pics = #{pics},bedtype = #{bedType},bedsize = #{bedSize} where roomtypeid = #{roomTypeId} ")
	int update(HmsERoomtypeInfoModel eRoomtypeInfo);
	

    /**
     * 根据房型Id获取房型信息
     * @param roomtypeId 房型Id
     * @return 房型信息列表
     */
    @Select("select * from t_roomtype_info where roomtypeid = #{roomtypeId}")
    HmsTRoomtypeInfoModel findTByRoomtypeId(@Param("roomtypeId") long roomtypeId);
    
    /**
     * 根据房型Id获取房型信息
     * @param roomtypeId 房型Id
     * @return 房型信息列表
     */
    @Select("select count(id) from t_roomtype_info where roomtypeid = #{roomtypeId}")
    int findTListByRoomtypeId(@Param("roomtypeId") long roomtypeId);
    
	/**
	 * 增加信息的房型信息
	 * @param tRoomtypeInfo
	 * @return
	 */
	@Insert("insert into t_roomtype_info(roomtypeid, minarea, maxarea, pics,bedtype,bedsize) values (#{roomTypeId}, #{minArea}, #{maxArea}, #{pics}, #{bedType}, #{bedSize})")
	@Options(useGeneratedKeys = true, keyProperty = "id", flushCache=true)
	int addT(HmsTRoomtypeInfoModel tRoomtypeInfo);
	
	/**
	 * 更新房型信息
	 * @param tRoomtypeInfo
	 * @return
	 */
	@Update("update t_roomtype_info set roomtypeid = #{roomTypeId}, minarea = #{minArea}, maxarea = #{maxArea}, pics = #{pics},bedtype = #{bedType},bedsize = #{bedSize} where id = #{id} ")
	@Options(useGeneratedKeys = true, keyProperty = "id", flushCache=true)
	int updateT(HmsTRoomtypeInfoModel tRoomtypeInfo);
	
	/**
	 * 删除房型信息
	 * @param tRoomtypeInfo
	 * @return
	 */
	@Delete("delete from t_roomtype_info where roomtypeid = #{roomTypeId}")
	int deleteT(@Param("roomTypeId") long roomTypeId);
}
