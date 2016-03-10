package com.mk.hms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mk.hms.model.HmsOtaOrderModel;
import com.mk.hms.model.HmsOtaRoomOrderModel;

/**
 * hms订单mapper接口
 * @author hdy
 *
 */
public interface HmsOtsOrderMapper{
	
	/**
	 * 获取hms订单信息
	 * @param pageNum 页码
	 * @param pageSize 页面数据条数
	 * @return hms订单列表
	 */
	@Select("select * from b_otaorder limit #{pageNum}, #{pageSize}")
	List<HmsOtaOrderModel> findHmsOrders(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
	
	/**
	 * 根据订单id， 获取订单信息
	 * @param ids
	 * @return
	 */
	@Select("select * from b_otaorder where id in (${ids})")
	List<HmsOtaOrderModel> findHmsOrdersByOrderIds(@Param("ids") String ids);
	/**
	 * 获取hms订单信息
	 * @param pageNum 页码
	 * @param pageSize 页面数据条数
	 * @return hms订单列表
	 */
	@Select("select * from b_otaorder where HotelId = #{hotelId} limit #{pageNum}, #{pageSize}")
	List<HmsOtaOrderModel> findHmsOrdersInHotel(@Param("hotelId") long hotelId, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
	
	/**
	 * 获取hms订单信息
	 * @param hotelId 酒店id
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param pageNum 页码
	 * @param pageSize 页面数据条数
	 * @return hms订单列表
	 */
	@Select("select * from b_otaorder where Hotelid = #{Hotelid} and Createtime >= #{Begintime} and Createtime <="
			+ " #{endTime}")
	List<HmsOtaOrderModel> findTodayHmsOrders(@Param("Hotelid") long hotelId, @Param("Begintime") String beginTime, 
			@Param("endTime") String endTime);
	
	/**
	 * 获取今天入住客单信息列表
	 * @param hotelId 酒店id
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param orderStatus 订单类型
	 * @return 订单列表
	 */
	@Select("select * from b_otaorder where Hotelid = #{Hotelid} and OrderStatus = #{OrderStatus}")
	List<HmsOtaOrderModel> findHmsOrdersByStatus(@Param("Hotelid") long hotelId, @Param("OrderStatus") int orderStatus);
	
	/**
	 * 获取hms订单信息
	 * @param hotelId 酒店主见
	 * @return hms订单条数
	 */
	@Select("select id from b_otaorder where HotelId = #{hotelId}")
	List<Long> findHmsOrdersCount(@Param("hotelId") long hotelId);
	
	/**
	 * 通过id获取订单信息
	 * @param id 主键
	 * @return 订单信息
	 */
	@Select("select * from b_otaorder where id = #{id}")
	HmsOtaOrderModel findOtsOrderByid(@Param("id") long id);
	
	/**
	 * 通过id获取订单客单信息
	 * @param id 主键
	 * @return 订单信息
	 */
	@Select("select * from b_otaroomorder where OtaOrderId = #{otaOrderId}")
	HmsOtaRoomOrderModel findOtsRoomOrderByOrderId(@Param("otaOrderId") long otaOrderId);
	
	/**
	 * 获取前台切客优惠券
	 * @param promotion 前台优惠券id
	 * @param otaorderid 订单id
	 * @return 数据列表
	 */
	@Select("select id from b_promotion_price where promotion = #{promotion} and otaorderid = #{otaorderid}")
	List<Long> findPromotionBoOtsOrderId(@Param("promotion") long promotion, @Param("otaorderid") long otaorderid);
}
