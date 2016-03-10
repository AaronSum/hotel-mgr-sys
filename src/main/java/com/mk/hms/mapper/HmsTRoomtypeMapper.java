package com.mk.hms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mk.hms.model.HmsTRoomtypeModel;

/**
 * hms房型mapper接口
 * Created by qhdhiman on 2015/5/2.
 */
public interface HmsTRoomtypeMapper {
    /**
     * 获取全部床型
     * @return 床型列表
     */
    @Select("select * from t_roomtype")
    List<HmsTRoomtypeModel> finds();

    /**
     * 根据酒店Id获取床型
     * @param hotelId 酒店Id
     * @return 床型列表
     */
    @Select("select * from t_roomtype where ehotelid = #{hotelId}")
    List<HmsTRoomtypeModel> findsByHotelId(@Param("hotelId") long hotelId);
    

    /**
     * 根据房型Id获取床型
     * @param 房型Id
     * @return 床型列表
     */
    @Select("select * from t_roomtype where id = #{id}")
    HmsTRoomtypeModel findById(@Param("id") long id);
    
    /**
     * 更新床的数量
     * @param id
     * @param bedNum
     * @return
     */
    @Update("update t_roomtype set bedNum=#{bedNum} where id=#{id}")
    int updateBedNum(@Param("id") long id,@Param("bedNum") int bedNum);    
    
}
