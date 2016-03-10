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
public interface BRuleHmsOtsRoomOrderMapper{
	
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
	
	@Select("SELECT t.OtaOrderId,t.RoomTypeName,t.Hotelid,t.Hotelname,t.Contacts,t.ContactsPhone,t.RoomNo,t.Begintime,t.Endtime,t.Createtime creattime,t.OrderStatus,t.spreadUser,t.Ordertype,t.rulecode,t.Invalidreason,IF(l.pmssend=200,l.allcost,NULL) as allcost,IF(l.pmssend=200,l.realotagive,NULL) as realotagive,l.qiekeIncome from (select m.OtaOrderId, m.RoomTypeName, m.Hotelid, m.Hotelname, b.Contacts, m.ContactsPhone, m.RoomNo, b.Begintime,b.Endtime,b.Createtime,b.OrderStatus,b.spreadUser,b.Invalidreason,b.Ordertype,b.rulecode"
			+ " from (select a.OtaOrderId, a.RoomTypeName, a.Hotelid, a.Hotelname, a.ContactsPhone, a.RoomNo"
			+ " from b_otaroomorder a inner join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
			+ " and a.Hotelid = b.Hotelid where a.Hotelid = #{Hotelid}) m"
			+ " inner join"
			+ " b_otaorder b on m.OtaOrderId = b.id where ordertype=#{ordertype} )t"
			+ " left join p_pay p on p.orderid=t.OtaOrderId"
			+ " left join p_orderlog l on p.id=l.payid "
			// + " and l.pmssend = 200" //200状态为已下发
			+ " order by t.OtaOrderId desc limit #{pageNum}, #{pageSize}")
	List<OtaCheckinRoomOrderModel> findHmsRoomOrders(@Param("Hotelid") long hotelId,  @Param("ordertype")int ordertype,@Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize);
	
	@Select("SELECT t.OtaOrderId,t.RoomTypeName,t.Hotelid,t.Hotelname,t.Contacts,t.ContactsPhone,t.RoomNo,t.Begintime,t.Endtime,t.Createtime creattime,t.OrderStatus,t.spreadUser,t.Ordertype,t.rulecode,t.Invalidreason,IF(l.pmssend=200,l.allcost,NULL) as allcost,IF(l.pmssend=200,l.realotagive,NULL) as realotagive,l.qiekeIncome from (select m.OtaOrderId, m.RoomTypeName, m.Hotelid, m.Hotelname, b.Contacts, m.ContactsPhone, m.RoomNo, b.Begintime,b.Endtime,b.Createtime,b.OrderStatus,b.spreadUser,b.Invalidreason,b.Ordertype,b.rulecode"
			+ " from (select a.OtaOrderId, a.RoomTypeName, a.Hotelid, a.Hotelname, a.ContactsPhone, a.RoomNo"
			+ " from b_otaroomorder a inner join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
			+ " and a.Hotelid = b.Hotelid where a.Hotelid = #{Hotelid}) m"
			+ " inner join"
			+ " b_otaorder b on m.OtaOrderId = b.id where ordertype=#{ordertype} )t"
			+ " left join p_pay p on p.orderid=t.OtaOrderId"
			+ " left join p_orderlog l on p.id=l.payid where t.OtaOrderId=#{orderId}")
	List<OtaCheckinRoomOrderModel> findHmsRoomOrderById(@Param("Hotelid") long hotelId, @Param("ordertype")int ordertype, @Param("orderId") long orderId);
	
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
	
	@Select("SELECT t.OtaOrderId,t.RoomTypeName,t.Hotelid,t.Hotelname,t.Contacts,t.ContactsPhone,t.RoomNo,t.spreaduser,t.Ordertype,IF(l.pmssend=200,l.allcost,NULL) as allcost,IF(l.pmssend=200,l.realotagive,NULL) as realotagive,l.qiekeIncome,t.invalidreason,t.rulecode FROM (select a.OtaOrderId, a.RoomTypeName, a.Hotelid, a.Hotelname, b.Contacts, a.ContactsPhone, a.RoomNo,b.spreaduser,b.Ordertype,b.invalidreason,b.rulecode"
			+ " from b_otaroomorder a inner join b_otaorder b on a.OtaOrderId = b.id"
			+ " and a.Hotelid = b.Hotelid where a.ordertype=${ordertype} AND a.OtaOrderId in (${OtaOrderIds}) )t"
			+ " left join p_pay p on p.orderid=t.OtaOrderId"
			+ " left join p_orderlog l on p.id=l.payid"
			//+ " and l.pmssend = 200"//200状态为已下发
			+ " order by t.OtaOrderId desc  limit #{pageNum}, #{pageSize} ")
	List<OtaCheckinRoomOrderModel> findTodayHmsRoomOrders(@Param("ordertype")int ordertype,@Param("OtaOrderIds") String OtaOrderIds, @Param("pageNum") int pageNum,
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
	@Select("select o.OtaOrderId, o.RoomTypeName, o.Hotelid, o.Hotelname, o.Contacts, o.ContactsPhone, o.RoomNo, o.spreadUser, o.Ordertype,o.rulecode,o.Invalidreason,o.OrderStatus, IF(l.pmssend=200,l.allcost,NULL) as allcost,IF(l.pmssend=200,l.realotagive,NULL) as realotagive,l.qiekeIncome from "
			+ " (select a.OtaOrderId, a.RoomTypeName, a.Hotelid, a.Hotelname, bota.Contacts, a.ContactsPhone, a.RoomNo, bota.spreadUser,bota.Invalidreason, bota.OrderStatus, bota.Ordertype,bota.rulecode "
			+ " from (select id,spreadUser,Invalidreason, OrderStatus, Ordertype, Contacts,rulecode from b_otaorder where  HotelId = ${Hotelid} AND Ordertype = ${type} ) bota "
			+ " INNER JOIN b_otaroomorder a ON bota.id = a.OtaOrderId "
			+ " INNER JOIN b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
			+ " and a.Hotelid = b.Hotelid where b.checkintime >= #{beginTime}"
			+ " and b.checkintime <= #{endTime} and b.Status in ('IN', 'OK', 'PM') ) o"
			+ " left join "
			+ " p_pay p on p.orderid = o.OtaOrderId"
			+ " left join "
			+ " p_orderlog l on p.id=l.payid"
			// + " and l.pmssend = 200" //200状态为已下发
			+ " order by o.OtaOrderId desc limit #{pageNum}, #{pageSize}")
	List<OtaCheckinRoomOrderModel> findCheckinHmsRoomOrders(@Param("type") int type,@Param("Hotelid") long Hotelid, 
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
	//		+ " and t.OrderType = ${type} and t.Paystatus = 120 order by a.OtaOrderId desc limit #{pageNum}, #{pageSize}")
	
	@Select("select a.OtaOrderId, a.RoomTypeName, a.Hotelid, a.Hotelname, a.Contacts, a.ContactsPhone, a.RoomNo, t.Ordertype"
			+ " from b_otaroomorder a inner join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
			+ " LEFT JOIN b_otaorder t on a.OtaOrderId = t.id"
			+ " and a.Hotelid = b.Hotelid where t.OrderType = ${type}"
			+ " and a.Hotelid = ${Hotelid} and t.Paystatus = 120 order by a.OtaOrderId desc limit #{pageNum}, #{pageSize}")
	List<OtaCheckinRoomOrderModel> findBookHmsRoomOrders(@Param("type") int type,@Param("Hotelid") long Hotelid, @Param("pageNum") int pageNum,
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
//	@Select("select a.Hotelid from b_otaroomorder a left join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
//			+ " and a.Hotelid = b.Hotelid where a.ordertype=${type} AND a.OtaOrderId in (${OtaOrderIds})")
	@Select("SELECT t.OtaOrderId FROM (select a.OtaOrderId"
			+ " from b_otaroomorder a left join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
			+ " and a.Hotelid = b.Hotelid where a.ordertype=${type} AND a.OtaOrderId in (${OtaOrderIds}) order by a.OtaOrderId desc )t"
			+ " left join p_pay p on p.orderid=t.OtaOrderId "
			+ " left join p_orderlog l on p.id=l.payid ")
	List<Long> todayListCount(@Param("type") int type,@Param("OtaOrderIds") String OtaOrderIds);
	
	/**
	 * 获取当前酒店客单总条数
	 * @param OtaOrderIds 订单ids
	 * @return 客店列表
	 */
//	@Select("select a.Hotelid from b_otaroomorder a inner join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
//			+ " and a.Hotelid = b.Hotelid where a.ordertype = ${type} AND a.Hotelid = ${Hotelid} and b.checkintime >= #{beginTime}"
//			+ " and b.checkintime <= #{endTime} and b.Status in ('IN', 'OK', 'PM')")
//	@Select("select a.OtaOrderId from b_otaroomorder a inner join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
//			+ " and a.Hotelid = b.Hotelid where  a.ordertype = ${type} AND a.Hotelid = ${Hotelid} and b.checkintime >= #{beginTime}"
//			+ " and b.checkintime <= #{endTime} and b.Status in ('IN', 'OK', 'PM')")
	@Select("select count(bota.id) "
			+ " from (select id from b_otaorder where  HotelId = ${Hotelid} AND Ordertype = ${type} ) bota "
			+ " INNER JOIN b_otaroomorder a ON bota.id = a.OtaOrderId "
			+ " INNER JOIN b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
			+ " and a.Hotelid = b.Hotelid where b.checkintime >= #{beginTime}"
			+ " and b.checkintime <= #{endTime} and b.Status in ('IN', 'OK', 'PM')")
	int checkinListCount(@Param("type") int type,@Param("Hotelid") long Hotelid, 
			@Param("beginTime") String beginTime, @Param("endTime") String endTime);
	
	/**
	 * 获取当前酒店预订单客单总条数
	 * @param Hotelid 酒店主键
	 * @return 客店列表
	 */
	@Select("select count(a.OtaOrderId)"
			+ " from b_otaroomorder a inner join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
			+ " LEFT JOIN b_otaorder t on a.OtaOrderId = t.id"
			+ " and a.Hotelid = b.Hotelid where t.OrderType = ${type}"
			+ " and a.Hotelid = ${Hotelid} and t.Paystatus = 120")
	int bookListCount(@Param("type") int type,@Param("Hotelid") long Hotelid);
	
	/**
	 * 获取当前酒店客单总条数
	 * @param OtaOrderIds 订单ids
	 * @return 客店列表
	 */
//	@Select("select count(id) from b_otaorder where id in "+
//			"(select a.OtaOrderId from b_otaroomorder a left join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
//			+ " and a.Hotelid = b.Hotelid where a.Hotelid = #{Hotelid})"
//			+ " and OrderType = #{type}")
	@Select("SELECT t.OtaOrderId,t.RoomTypeName,t.Hotelid,t.Hotelname,t.Contacts,t.ContactsPhone,t.RoomNo,t.Begintime,t.Endtime,t.Createtime,t.OrderStatus,t.spreadUser,l.allcost,l.realotagive from (select m.OtaOrderId, m.RoomTypeName, m.Hotelid, m.Hotelname, m.Contacts, m.ContactsPhone, m.RoomNo, b.Begintime,b.Endtime,b.Createtime,b.OrderStatus,b.spreadUser"
			+ " from (select a.OtaOrderId, a.RoomTypeName, a.Hotelid, a.Hotelname, a.Contacts, a.ContactsPhone, a.RoomNo"
			+ " from b_otaroomorder a left join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo"
			+ " and a.Hotelid = b.Hotelid where a.Hotelid = #{Hotelid}) m"
			+ " inner join"
			+ " b_otaorder b on m.OtaOrderId = b.id where ordertype=#{ordertype} order by m.OtaOrderId desc )t"
			+ " left join p_pay p on p.orderid=t.OtaOrderId "
			+ " left join p_orderlog l on p.id=l.payid ")
	int bRuleListCount(@Param("Hotelid") long Hotelid, @Param("type") int type);
	
	
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
	@Select("select o.id AS id,o.Ordermethod AS ordermethod,ol.allcost AS allcost,ol.otagive AS yhje,((ol.allcost - ol.hotelgive) - ol.realotagive) AS cjje,(case o.Ordertype when 1 then ol.usercost else 0 end) AS yfje,(case o.Ordertype when 2 then ol.usercost else 0 end) AS dfje,o.Ordertype AS orderType,o.Invalidreason as invalidreason,ol.pmssend As pmsSend,ol.sendreason As sendReason from ((b_otaorder o join p_pay p) join p_orderlog ol) where ((o.id = p.orderid) and (ol.payid = p.id)) and o.id = #{orderId} ")
	HmsOrderMoreInfoModel findOrderMoreInfo(@Param("orderId") long orderId);
}
