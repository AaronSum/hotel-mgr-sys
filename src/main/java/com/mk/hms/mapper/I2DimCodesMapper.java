package com.mk.hms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mk.hms.model.HmsHQrcodeModel;

/**
 * 二维码mapper 接口
 * @author hdy
 *
 */
public interface I2DimCodesMapper {

	/**
	 * 根据酒店id，获取二维码列表
	 * @param hotelid 酒店id
	 * @return 列表数据
	 */
	@Select("select * from  h_qrcode where hotelid = #{hotelid}")
	List<HmsHQrcodeModel> findI2DimCodes(@Param("hotelid") long hotelid);
	
	/**
	 * 根据用户id、酒店id，获取二维码
	 * @param hotelid 酒店id
	 * @param userId 用户id
	 * @return 对应二维码对象
	 */
	@Select("select * from  h_qrcode where hotelid = #{hotelid} and userid = #{userid}")
	HmsHQrcodeModel findI2DimCode(@Param("hotelid") long hotelid, @Param("userid") long userId);
}
