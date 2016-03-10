package com.mk.hms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mk.hms.model.HmsSaleUserModel;

/**
 * 销售用户mapper
 * @author qhdhiman
 *
 */
public interface HmsSaleUserMapper {

	/**
	 * 获取单个酒店的销售用户信息
	 * @param hotelId
	 * @return
	 */
//	@Select("select u.USER_CODE userCode,u.USER_NAME userName,u.USER_MOBILE userMobile,m.HOTEL_ID hotelId from sy_org_user u LEFT JOIN sms_hotel_sales_mapping m ON u.USER_CODE = m.USER_CODE where m.HOTEL_ID = #{hotelId} AND m.START_DATE <= now() AND now()<= m.END_DATE")
//	HmsSaleUserModel find(@Param("hotelId") String hotelId);

	/**
	 * 获取一组酒店的销售用户信息
	 * @param hotelIds ","分割的的字符串
	 * @return
	 */
	@Select("select u.USER_CODE userCode,u.USER_NAME userName,u.USER_MOBILE userMobile,m.HOTEL_ID hotelId from sy_org_user u LEFT JOIN sms_hotel_sales_mapping m ON u.USER_CODE = m.USER_CODE where m.HOTEL_ID in (${hotelIds}) AND (m.START_DATE <= now() AND now()<= m.END_DATE)")
	List<HmsSaleUserModel> finds(@Param("hotelIds") String hotelIds);
}
