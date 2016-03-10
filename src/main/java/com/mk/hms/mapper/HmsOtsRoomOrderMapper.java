package com.mk.hms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mk.hms.model.HmsOrderMoreInfoModel;
import com.mk.hms.model.OtaCheckinRoomOrderModel;

/**
 * hms客单mapper接口
 * @author hdy
 *
 */
public interface HmsOtsRoomOrderMapper{
	
	/**
	 * 获取hms订单信息
	 * @param hotelId 酒店id
	 * @param pageNum 页码
	 * @param pageSize 页面数据条数
	 * @return hms订单列表
	 */
	//@Select("select a.OtaOrderId, a.RoomTypeName, a.Hotelid, a.Hotelname, a.Contacts, a.ContactsPhone, a.RoomNo, v.*"
	//		+ " from b_otaroomorder a left join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
	//		+ " and a.Hotelid = b.Hotelid left join (select o.id AS id,o.Ordermethod AS ordermethod,ol.allcost AS allcost,ol.otagive AS yhje,((ol.allcost - ol.hotelgive) - ol.realotagive) AS cjje,(case o.Ordertype when 1 then ol.usercost else 0 end) AS yfje,(case o.Ordertype when 2 then ol.usercost else 0 end) AS dfje,o.Ordertype AS orderType,ol.pmssend As pmsSend,ol.sendreason As sendReason from ((b_otaorder o join p_pay p) join p_orderlog ol) where ((o.id = p.orderid) and (ol.payid = p.id))) v on v.id = a.OtaOrderId where a.Hotelid = #{Hotelid} order by a.OtaOrderId desc limit #{pageNum}, #{pageSize}")
	
	@Select("select a.OtaOrderId, a.RoomTypeName, a.Hotelid, a.Hotelname, a.Contacts, a.ContactsPhone, a.RoomNo"
			+ " from b_otaroomorder a left join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
			+ " and a.Hotelid = b.Hotelid where a.Hotelid = #{Hotelid} order by a.OtaOrderId desc limit #{pageNum}, #{pageSize}")
	List<OtaCheckinRoomOrderModel> findHmsRoomOrders(@Param("Hotelid") long hotelId, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize);
	
	/**
	 * 获取hms订单信息
	 * @param hotelId 酒店id
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param pageNum 页码
	 * @param pageSize 页面数据条数
	 * @return hms订单列表
	 */
	//@Select("select a.OtaOrderId, a.RoomTypeName, a.Hotelid, a.Hotelname, a.Contacts, a.ContactsPhone, a.RoomNo, v.*"
	//		+ " from b_otaroomorder a left join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
	//		+ " and a.Hotelid = b.Hotelid left join (select o.id AS id,o.Ordermethod AS ordermethod,ol.allcost AS allcost,ol.otagive AS yhje,((ol.allcost - ol.hotelgive) - ol.realotagive) AS cjje,(case o.Ordertype when 1 then ol.usercost else 0 end) AS yfje,(case o.Ordertype when 2 then ol.usercost else 0 end) AS dfje,o.Ordertype AS orderType,ol.pmssend As pmsSend,ol.sendreason As sendReason from ((b_otaorder o join p_pay p) join p_orderlog ol) where ((o.id = p.orderid) and (ol.payid = p.id))) v on v.id = a.OtaOrderId where a.OtaOrderId in (${OtaOrderIds}) order by a.OtaOrderId desc limit #{pageNum}, #{pageSize}")
	
	@Select("select a.OtaOrderId, a.RoomTypeName, a.Hotelid, a.Hotelname, a.Contacts, a.ContactsPhone, a.RoomNo"
			+ " from b_otaroomorder a left join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
			+ " and a.Hotelid = b.Hotelid where a.OtaOrderId in (${OtaOrderIds}) order by a.OtaOrderId desc limit #{pageNum}, #{pageSize}")
	List<OtaCheckinRoomOrderModel> findTodayHmsRoomOrders(@Param("OtaOrderIds") String OtaOrderIds, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize);
	
	/**
	 * 获取今天入住客单信息列表
	 * @param Hotelid 酒店id
	 * @param pageNum 页码
	 * @param pageSize 页面数据条数
	 * @return 订单列表
	 */
	//@Select("select a.OtaOrderId, a.RoomTypeName, a.Hotelid, a.Hotelname, a.Contacts, a.ContactsPhone, a.RoomNo, v.*"
	//		+ " from b_otaroomorder a inner join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
	//		+ " and a.Hotelid = b.Hotelid left join (select o.id AS id,o.Ordermethod AS ordermethod,ol.allcost AS allcost,ol.otagive AS yhje,((ol.allcost - ol.hotelgive) - ol.realotagive) AS cjje,(case o.Ordertype when 1 then ol.usercost else 0 end) AS yfje,(case o.Ordertype when 2 then ol.usercost else 0 end) AS dfje,o.Ordertype AS orderType,ol.pmssend As pmsSend,ol.sendreason As sendReason from ((b_otaorder o join p_pay p) join p_orderlog ol) where ((o.id = p.orderid) and (ol.payid = p.id))) v on v.id = a.OtaOrderId where a.Hotelid = ${Hotelid} and b.checkintime >= #{beginTime}"
	//		+ " and b.checkintime <= #{endTime} and b.Status in ('IN', 'OK', 'PM') order by a.OtaOrderId desc limit #{pageNum}, #{pageSize}")
	
	@Select("select a.OtaOrderId, a.RoomTypeName, a.Hotelid, a.Hotelname, a.Contacts, a.ContactsPhone, a.RoomNo"
			+ " from b_otaroomorder a inner join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
			+ " and a.Hotelid = b.Hotelid where a.Hotelid = ${Hotelid} and b.checkintime >= #{beginTime}"
			+ " and b.checkintime <= #{endTime} and b.Status in ('IN', 'OK', 'PM') order by a.OtaOrderId desc limit #{pageNum}, #{pageSize}")
	List<OtaCheckinRoomOrderModel> findCheckinHmsRoomOrders(@Param("Hotelid") long Hotelid, 
			@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize);
	
	/**
	 * 获取预定单客单信息列表
	 * @param Hotelid 酒店id
	 * @param pageNum 页码
	 * @param pageSize 页面数据条数
	 * @return 订单列表
	 */
	//@Select("select a.OtaOrderId, a.RoomTypeName, a.Hotelid, a.Hotelname, a.Contacts, a.ContactsPhone, a.RoomNo, v.*"
	//		+ " from b_otaroomorder a inner join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
	//		+ " LEFT JOIN b_otaorder t on a.OtaOrderId = t.id"
	//		+ " and a.Hotelid = b.Hotelid left join (select o.id AS id,o.Ordermethod AS ordermethod,ol.allcost AS allcost,ol.otagive AS yhje,((ol.allcost - ol.hotelgive) - ol.realotagive) AS cjje,(case o.Ordertype when 1 then ol.usercost else 0 end) AS yfje,(case o.Ordertype when 2 then ol.usercost else 0 end) AS dfje,o.Ordertype AS orderType,ol.pmssend As pmsSend,ol.sendreason As sendReason from ((b_otaorder o join p_pay p) join p_orderlog ol) where ((o.id = p.orderid) and (ol.payid = p.id))) v on v.id = a.OtaOrderId where a.Hotelid = ${Hotelid}"
	//		+ " and t.OrderType = 1 and t.Paystatus = 120 order by a.OtaOrderId desc limit #{pageNum}, #{pageSize}")
	
	@Select("select a.OtaOrderId, a.RoomTypeName, a.Hotelid, a.Hotelname, a.Contacts, a.ContactsPhone, a.RoomNo"
			+ " from b_otaroomorder a inner join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
			+ " LEFT JOIN b_otaorder t on a.OtaOrderId = t.id"
			+ " and a.Hotelid = b.Hotelid where a.Hotelid = ${Hotelid}"
			+ " and t.OrderType = 1 and t.Paystatus = 120 order by a.OtaOrderId desc limit #{pageNum}, #{pageSize}")
	List<OtaCheckinRoomOrderModel> findBookHmsRoomOrders(@Param("Hotelid") long Hotelid, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize);
	
	/**
	 * 获取当前酒店客单总条数
	 * @param hotelId 酒店id
	 * @return 客店列表
	 */
	@Select("select a.Hotelid from b_otaroomorder a left join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
			+ " and a.Hotelid = b.Hotelid where a.Hotelid = #{Hotelid}")
	List<Long> listCount(@Param("Hotelid") long hotelId);
	
	/**
	 * 获取当前酒店客单总条数
	 * @param hotelId 酒店id
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @return 客店列表
	 */
	@Select("select a.Hotelid from b_otaroomorder a left join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
			+ " and a.Hotelid = b.Hotelid where a.OtaOrderId in (${OtaOrderIds})")
	List<Long> todayListCount(@Param("OtaOrderIds") String OtaOrderIds);
	
	/**
	 * 获取当前酒店客单总条数
	 * @param OtaOrderIds 订单ids
	 * @return 客店列表
	 */
	@Select("select a.Hotelid from b_otaroomorder a inner join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
			+ " and a.Hotelid = b.Hotelid where a.Hotelid = ${Hotelid} and b.checkintime >= #{beginTime}"
			+ " and b.checkintime <= #{endTime} and b.Status in ('IN', 'OK', 'PM')")
	List<Long> checkinListCount(@Param("Hotelid") long Hotelid, 
			@Param("beginTime") String beginTime, @Param("endTime") String endTime);
	
	/**
	 * 获取当前酒店预订单客单总条数
	 * @param Hotelid 酒店主键
	 * @return 客店列表
	 */
	@Select("select count(a.OtaOrderId)"
			+ " from b_otaroomorder a inner join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
			+ " LEFT JOIN b_otaorder t on a.OtaOrderId = t.id"
			+ " and a.Hotelid = b.Hotelid where a.Hotelid = ${Hotelid}"
			+ " and t.OrderType = 1 and t.Paystatus = 120")
	int bookListCount(@Param("Hotelid") long Hotelid);
	
	/**
	 * 获取订单的乐住币下发情况
	 * @param orderIds
	 * @return
	 */
	@Select("select o.id AS id,o.Ordermethod AS ordermethod,ol.allcost AS allcost,ol.otagive AS yhje,((ol.allcost - ol.hotelgive) - ol.realotagive) AS cjje,(case o.Ordertype when 1 then ol.usercost else 0 end) AS yfje,(case o.Ordertype when 2 then ol.usercost else 0 end) AS dfje,o.Ordertype AS orderType,ol.pmssend As pmsSend,ol.sendreason As sendReason from ((b_otaorder o join p_pay p) join p_orderlog ol) where ((o.id = p.orderid) and (ol.payid = p.id)) and o.id in ( ${orderIds} )")
	List<HmsOrderMoreInfoModel> findsOrderMoreInfo(@Param("orderIds") String orderIds);
	
	/**
	 * 获取单条订单的乐住币下发情况
	 * @param orderId
	 * @return
	 */
	@Select("select o.id AS id,o.Ordermethod AS ordermethod,ol.allcost AS allcost,ol.otagive AS yhje,((ol.allcost - ol.hotelgive) - ol.realotagive) AS cjje,(case o.Ordertype when 1 then ol.usercost else 0 end) AS yfje,(case o.Ordertype when 2 then ol.usercost else 0 end) AS dfje,o.Ordertype AS orderType,ol.pmssend As pmsSend,ol.sendreason As sendReason from ((b_otaorder o join p_pay p) join p_orderlog ol) where ((o.id = p.orderid) and (ol.payid = p.id)) and o.id = #{orderId} ")
	HmsOrderMoreInfoModel findOrderMoreInfo(@Param("orderId") long orderId);
}
