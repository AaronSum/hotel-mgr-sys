package com.mk.hms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mk.hms.model.HmsTFacilityModel;

/**
 * 设备设施Mapper
 * Created by qhdhiman on 2015/5/3.
 */
public interface HmsTFacilityMapper {	
	/**
	 * 获取设备设备
	 * @param binding 匹配类型
	 * @param visible 是否可用标示，T－可用，F－不可用
	 * @return 酒店设置列表
	 */
	@Select("select * from t_facility where binding = #{binding} and visible = #{visible} order by facType, facSort")
	List<HmsTFacilityModel> finds(@Param("binding") String binding, @Param("visible") String visible);
	
	/**
	 * 获取设备设备
	 * @param binding 匹配类型
	 * @param visible 是否可用标示，T－可用，F－不可用
	 * @param facType 1常见设置、2其他设置
	 * @return 酒店设置列表
	 */
	@Select("select * from t_facility where binding = #{binding} and visible = #{visible} and facType = #{facType} order by facType, facSort")
	List<HmsTFacilityModel> findsWithFacType(@Param("binding") String binding, @Param("visible") String visible,@Param("facType") int facType);
	
}
