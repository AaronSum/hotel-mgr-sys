package com.mk.hms.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.db.HmsJdbcTemplate;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.BRuleHmsOtsRoomOrderMapper;
import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.HmsOrderMoreInfoModel;
import com.mk.hms.model.HmsOtaRoomOrderModel;
import com.mk.hms.model.OtaCheckinRoomOrderModel;
import com.mk.hms.utils.HmsDateUtils;
import com.mk.hms.utils.HmsEnumsUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.Page;

/**
 * hms订单信息
 * @author hdy
 */
@Service
@Transactional
public class BRuleHmsOtsRoomOrderService{

	private static final Logger logger = LoggerFactory.getLogger(BRuleHmsOtsRoomOrderService.class);
	
	@Autowired
	private BRuleHmsOtsRoomOrderMapper hmsOtsRoomOrderMapper;
	
	/**
	 * 获取订单信息
	 * @return 订单列表
	 * @throws SessionTimeOutException 
	 */
	public List<OtaCheckinRoomOrderModel> findHmsRoomOrdersInHotel(int type, Page page) throws SessionTimeOutException {
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		EHotelWithBLOBs eHotel = SessionUtils.getThisHotel();
		List<OtaCheckinRoomOrderModel> list = hmsOtsRoomOrderMapper.findHmsRoomOrders(eHotel.getId(),type , stateIndex, dataSize);
		for (OtaCheckinRoomOrderModel order : list) {
			order.setOrderStatusName(HmsEnumsUtils.getText4HmsOtaOrserStatusEnum(order.getOrderStatus()));
		}
		return list;
	}
	/**
	 * 根据订单id查询订单信息
	 * @param type
	 * @param orderId
	 * @return
	 * @throws SessionTimeOutException
	 */
	public List<OtaCheckinRoomOrderModel> findHmsRoomOrderById(int type, long orderId) throws SessionTimeOutException {
		EHotelWithBLOBs eHotel = SessionUtils.getThisHotel();
		List<OtaCheckinRoomOrderModel> list = hmsOtsRoomOrderMapper.findHmsRoomOrderById(eHotel.getId(),type,orderId);
		for (OtaCheckinRoomOrderModel order : list) {
			order.setOrderStatusName(HmsEnumsUtils.getText4HmsOtaOrserStatusEnum(order.getOrderStatus()));
		}
		return list;
	}
	
	/**
	 * 获取今日订单信息
	 * @return 订单列表
	 */
	public List<OtaCheckinRoomOrderModel> findHmsRoomOrders(int ordertype, String orderIds, Page page) {
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		List<OtaCheckinRoomOrderModel> list = hmsOtsRoomOrderMapper.findTodayHmsRoomOrders(ordertype,orderIds, stateIndex, dataSize);
		for (OtaCheckinRoomOrderModel order : list) {
			order.setOrderStatusName(HmsEnumsUtils.getText4HmsOtaOrserStatusEnum(order.getOrderStatus()));
		}
		return list;
	}
	
	/**
	 * 获取今日入住
	 * @throws SessionTimeOutException 
	 */
	public List<OtaCheckinRoomOrderModel> findTodayCheckinHmsRoomOrders(int type, Page page) throws SessionTimeOutException {
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		String thisTodayStateTime = "";
		String thisTodayEndTime = "";
		try {
			thisTodayStateTime = HmsDateUtils.getFormatDateString(new Date(HmsDateUtils.getTodayStateTimeVal()),
					HmsDateUtils.FORMAT_DATETIME);
			thisTodayEndTime = HmsDateUtils.getFormatDateString(new Date(HmsDateUtils.getTodayEndTimeVal()),
					HmsDateUtils.FORMAT_DATETIME);
		} catch (ParseException e) {
			BRuleHmsOtsRoomOrderService.logger.error("日期格式转换异常：" + e.getMessage(), e);
		}
		EHotelWithBLOBs thisHotel = SessionUtils.getThisHotel();
		List<OtaCheckinRoomOrderModel> list = hmsOtsRoomOrderMapper.findCheckinHmsRoomOrders(type,thisHotel.getId(), thisTodayStateTime, thisTodayEndTime, stateIndex, dataSize);
		for (OtaCheckinRoomOrderModel order : list) {
			order.setOrderStatusName(HmsEnumsUtils.getText4HmsOtaOrserStatusEnum(order.getOrderStatus()));
		}
		return list;
	}
	
	/**
	 * 获取预定单
	 * @throws SessionTimeOutException 
	 */
	public List<OtaCheckinRoomOrderModel> findBookHmsRoomOrders(int type, Page page) throws SessionTimeOutException {
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		EHotelWithBLOBs thisHotel = SessionUtils.getThisHotel();
		List<OtaCheckinRoomOrderModel> list = hmsOtsRoomOrderMapper.findBookHmsRoomOrders(type,thisHotel.getId(), stateIndex, dataSize);
		for (OtaCheckinRoomOrderModel order : list) {
			order.setOrderStatusName(HmsEnumsUtils.getText4HmsOtaOrserStatusEnum(order.getOrderStatus()));
		}
		return list;
	}
	
	/**
	 * 获取当月入住
	 * @throws SessionTimeOutException 
	 */
	public List<OtaCheckinRoomOrderModel> findMonthCheckinHmsRoomOrders(int type, Page page) throws SessionTimeOutException {
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		String thisMonthStateTime = "";
		String thisMonthEndTime = "";
		try {
			thisMonthStateTime = HmsDateUtils.getFormatDateString(new Date(HmsDateUtils.getThisMonthStateTimeVal()),
					HmsDateUtils.FORMAT_DATETIME);
			thisMonthEndTime = HmsDateUtils.getFormatDateString(new Date(HmsDateUtils.getThisMonthEndTimeVal()),
					HmsDateUtils.FORMAT_DATETIME);
		} catch (ParseException e) {
			BRuleHmsOtsRoomOrderService.logger.error("日期格式转换异常：" + e.getMessage(), e);
		}
		EHotelWithBLOBs thisHotel = SessionUtils.getThisHotel();
		List<OtaCheckinRoomOrderModel> list = hmsOtsRoomOrderMapper.findCheckinHmsRoomOrders(type,thisHotel.getId(), thisMonthStateTime, thisMonthEndTime, stateIndex, dataSize);
		for (OtaCheckinRoomOrderModel order : list) {
			order.setOrderStatusName(HmsEnumsUtils.getText4HmsOtaOrserStatusEnum(order.getOrderStatus()));
		}
		return list;
	}
	
	/**
	 * 获取无效订单信息
	 * @return 订单列表
	 */
	public List<HmsOtaRoomOrderModel> findHmsRoomOrdersInvalidByPage(Page page) {
		/*int stateIndex = (pageNum - 1) * pageSize;
		int dataSize = pageSize;
		HmsLoginUserModel loginUser = SessionUtils.getSessionLoginUser();
		String sql = "select * from b_otaroomorder where Hotelid = " + loginUser.getThisHotel().getId() 
				+ " limit " + stateIndex + ", " + dataSize
				+ " where OrderStatus ＝ 无效";
		return hmsOtsRoomOrderMapper.findHmsRoomOrders(sql);*/
		//TODO
		return null;
	}
	
	/**
	 * 获取数据总条数
	 * @return 数据总条数
	 * @throws SessionTimeOutException 
	 */
	public int listCount(int type) throws SessionTimeOutException {
		EHotelWithBLOBs eHotel = SessionUtils.getThisHotel();
		long id = eHotel.getId();
		JdbcTemplate jdbc = HmsJdbcTemplate.getJdbcTemplate();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT t.OtaOrderId,t.RoomTypeName,t.Hotelid,t.Hotelname,t.Contacts,t.ContactsPhone,t.RoomNo,t.Begintime,t.Endtime,t.Createtime,t.OrderStatus,t.spreadUser,l.allcost,l.realotagive from (select m.OtaOrderId, m.RoomTypeName, m.Hotelid, m.Hotelname, m.Contacts, m.ContactsPhone, m.RoomNo, b.Begintime,b.Endtime,b.Createtime,b.OrderStatus,b.spreadUser")
		.append(" from (select a.OtaOrderId, a.RoomTypeName, a.Hotelid, a.Hotelname, a.Contacts, a.ContactsPhone, a.RoomNo")
		.append( " from b_otaroomorder a inner join b_pmsroomorder b on a.PmsRoomOrderNo = b.PmsRoomOrderNo")
		.append(" and a.Hotelid = b.Hotelid where a.Hotelid = ?) m")
		.append(" inner join b_otaorder b on m.OtaOrderId = b.id where ordertype=? order by m.OtaOrderId desc )t")
		.append(" left join p_pay p on p.orderid=t.OtaOrderId")
		.append(" left join p_orderlog l on p.id=l.payid");
		List<Map<String,Object>> list = jdbc.queryForList(sb.toString(),id,type);
//		return hmsOtsRoomOrderMapper.bRuleListCount(eHotel.getId(), type);
		if(list!=null){
			return list.size();
		}else{
			return 0;
		}
	}
	
	/**
	 * 获取今日数据总条数
	 * @return 数据总条数
	 */
	public int todayListCount(int type,String ordeIds) {
		return hmsOtsRoomOrderMapper.todayListCount(type,ordeIds).size();
	}
	
	/**
	 * 获取预定单数据总条数
	 * @return 数据总条数
	 * @throws SessionTimeOutException 
	 */
	public int bookListCount(int type) throws SessionTimeOutException {
		EHotelWithBLOBs thisHotel = SessionUtils.getThisHotel();
		return hmsOtsRoomOrderMapper.bookListCount(type,thisHotel.getId());
	}
	
	/**
	 * 获取入住数据总条数
	 * @return 数据总条数
	 * @throws SessionTimeOutException 
	 */
	public int checkinTodayListCount(int type) throws SessionTimeOutException {
		String thisTodayStateTime = "";
		String thisTodayEndTime = "";
		try {
			thisTodayStateTime = HmsDateUtils.getFormatDateString(new Date(HmsDateUtils.getTodayStateTimeVal()),
					HmsDateUtils.FORMAT_DATETIME);
			thisTodayEndTime = HmsDateUtils.getFormatDateString(new Date(HmsDateUtils.getTodayEndTimeVal()),
					HmsDateUtils.FORMAT_DATETIME);
		} catch (ParseException e) {
			BRuleHmsOtsRoomOrderService.logger.error("日期格式转换异常：" + e.getMessage(), e);
		}
		EHotelWithBLOBs thisHotel = SessionUtils.getThisHotel();
		return hmsOtsRoomOrderMapper.checkinListCount(type,thisHotel.getId(), thisTodayStateTime, thisTodayEndTime);
	}
	
	/**
	 * 获取入住数据总条数
	 * @return 数据总条数
	 * @throws SessionTimeOutException 
	 */
	public int checkinMonthListCount(int type) throws SessionTimeOutException {
		String thisMonthStateTime = "";
		String thisMonthEndTime = "";
		try {
			thisMonthStateTime = HmsDateUtils.getFormatDateString(new Date(HmsDateUtils.getThisMonthStateTimeVal()),
					HmsDateUtils.FORMAT_DATETIME);
			thisMonthEndTime = HmsDateUtils.getFormatDateString(new Date(HmsDateUtils.getThisMonthEndTimeVal()),
					HmsDateUtils.FORMAT_DATETIME);
		} catch (ParseException e) {
			BRuleHmsOtsRoomOrderService.logger.error("日期格式转换异常：" + e.getMessage(), e);
		}
		EHotelWithBLOBs thisHotel = SessionUtils.getThisHotel();
		return hmsOtsRoomOrderMapper.checkinListCount(type,thisHotel.getId(), thisMonthStateTime, thisMonthEndTime);
	}
	
	/**
	 * 获取当月入住数据总条数
	 * @return 数据总条数
	 */
	public int listCountInvalid() {
		/*HmsLoginUserModel loginUser = SessionUtils.getSessionLoginUser();
		String sql = "select count(id) from b_otaroomorder where Hotelid = " + loginUser.getThisHotel().getId() 
				+ " and OrderStatus ＝ 无效";
		return hmsOtsRoomOrderMapper.listCount(sql);*/
		//TODO
		return 0;
	}
	
	/**
	 * 获取订单的乐住币下发情况
	 * @param orderIds
	 * @return
	 */
	public List<HmsOrderMoreInfoModel> findsOrderMoreInfo(String orderIds){
		return hmsOtsRoomOrderMapper.findsOrderMoreInfo(orderIds);
	}
	
	/**
	 * 获取单个订单的乐住币下发情况
	 * @param orderId
	 * @return
	 */
	public HmsOrderMoreInfoModel findOrderMoreInfo(long orderId){
		return hmsOtsRoomOrderMapper.findOrderMoreInfo(orderId);
	}
}
