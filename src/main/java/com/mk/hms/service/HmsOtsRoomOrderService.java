package com.mk.hms.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.HmsOtsRoomOrderMapper;
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
public class HmsOtsRoomOrderService{

	private static final Logger logger = LoggerFactory.getLogger(HmsOtsRoomOrderService.class);
	
	@Autowired
	private HmsOtsRoomOrderMapper hmsOtsRoomOrderMapper;
	
	/**
	 * 获取订单信息
	 * @return 订单列表
	 * @throws SessionTimeOutException 
	 */
	public List<OtaCheckinRoomOrderModel> findHmsRoomOrdersInHotel(Page page) throws SessionTimeOutException {
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		EHotelWithBLOBs eHotel = SessionUtils.getThisHotel();
		List<OtaCheckinRoomOrderModel> list = hmsOtsRoomOrderMapper.findHmsRoomOrders(eHotel.getId(), stateIndex, dataSize);
		for (OtaCheckinRoomOrderModel order : list) {
			order.setOrderStatusName(HmsEnumsUtils.getText4HmsOtaOrserStatusEnum(order.getOrderStatus()));
		}
		return list;
	}
	
	/**
	 * 获取今日订单信息
	 * @return 订单列表
	 */
	public List<OtaCheckinRoomOrderModel> findHmsRoomOrders(String orderIds, Page page) {
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		List<OtaCheckinRoomOrderModel> list = hmsOtsRoomOrderMapper.findTodayHmsRoomOrders(orderIds, stateIndex, dataSize);
		for (OtaCheckinRoomOrderModel order : list) {
			order.setOrderStatusName(HmsEnumsUtils.getText4HmsOtaOrserStatusEnum(order.getOrderStatus()));
		}
		return list;
	}
	
	/**
	 * 获取今日入住
	 * @throws SessionTimeOutException 
	 */
	public List<OtaCheckinRoomOrderModel> findTodayCheckinHmsRoomOrders(Page page) throws SessionTimeOutException {
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
			HmsOtsRoomOrderService.logger.error("日期格式转换异常：" + e.getMessage(), e);
		}
		EHotelWithBLOBs thisHotel = SessionUtils.getThisHotel();
		List<OtaCheckinRoomOrderModel> list = hmsOtsRoomOrderMapper.findCheckinHmsRoomOrders(thisHotel.getId(), thisTodayStateTime, thisTodayEndTime, stateIndex, dataSize);
		for (OtaCheckinRoomOrderModel order : list) {
			order.setOrderStatusName(HmsEnumsUtils.getText4HmsOtaOrserStatusEnum(order.getOrderStatus()));
		}
		return list;
	}
	
	/**
	 * 获取预定单
	 * @throws SessionTimeOutException 
	 */
	public List<OtaCheckinRoomOrderModel> findBookHmsRoomOrders(Page page) throws SessionTimeOutException {
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		EHotelWithBLOBs thisHotel = SessionUtils.getThisHotel();
		List<OtaCheckinRoomOrderModel> list = hmsOtsRoomOrderMapper.findBookHmsRoomOrders(thisHotel.getId(), stateIndex, dataSize);
		for (OtaCheckinRoomOrderModel order : list) {
			order.setOrderStatusName(HmsEnumsUtils.getText4HmsOtaOrserStatusEnum(order.getOrderStatus()));
		}
		return list;
	}
	
	/**
	 * 获取当月入住
	 * @throws SessionTimeOutException 
	 */
	public List<OtaCheckinRoomOrderModel> findMonthCheckinHmsRoomOrders(Page page) throws SessionTimeOutException {
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
			HmsOtsRoomOrderService.logger.error("日期格式转换异常：" + e.getMessage(), e);
		}
		EHotelWithBLOBs thisHotel = SessionUtils.getThisHotel();
		List<OtaCheckinRoomOrderModel> list = hmsOtsRoomOrderMapper.findCheckinHmsRoomOrders(thisHotel.getId(), thisMonthStateTime, thisMonthEndTime, stateIndex, dataSize);
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
	public int listCount() throws SessionTimeOutException {
		EHotelWithBLOBs eHotel = SessionUtils.getThisHotel();
		return hmsOtsRoomOrderMapper.listCount(eHotel.getId()).size();
	}
	
	/**
	 * 获取今日数据总条数
	 * @return 数据总条数
	 */
	public int todayListCount(String ordeIds) {
		return hmsOtsRoomOrderMapper.todayListCount(ordeIds).size();
	}
	
	/**
	 * 获取预定单数据总条数
	 * @return 数据总条数
	 * @throws SessionTimeOutException 
	 */
	public int bookListCount() throws SessionTimeOutException {
		EHotelWithBLOBs thisHotel = SessionUtils.getThisHotel();
		return hmsOtsRoomOrderMapper.bookListCount(thisHotel.getId());
	}
	
	/**
	 * 获取入住数据总条数
	 * @return 数据总条数
	 * @throws SessionTimeOutException 
	 */
	public int checkinTodayListCount() throws SessionTimeOutException {
		String thisTodayStateTime = "";
		String thisTodayEndTime = "";
		try {
			thisTodayStateTime = HmsDateUtils.getFormatDateString(new Date(HmsDateUtils.getTodayStateTimeVal()),
					HmsDateUtils.FORMAT_DATETIME);
			thisTodayEndTime = HmsDateUtils.getFormatDateString(new Date(HmsDateUtils.getTodayEndTimeVal()),
					HmsDateUtils.FORMAT_DATETIME);
		} catch (ParseException e) {
			HmsOtsRoomOrderService.logger.error("日期格式转换异常：" + e.getMessage(), e);
		}
		EHotelWithBLOBs thisHotel = SessionUtils.getThisHotel();
		return hmsOtsRoomOrderMapper.checkinListCount(thisHotel.getId(), thisTodayStateTime, thisTodayEndTime).size();
	}
	
	/**
	 * 获取入住数据总条数
	 * @return 数据总条数
	 * @throws SessionTimeOutException 
	 */
	public int checkinMonthListCount() throws SessionTimeOutException {
		String thisMonthStateTime = "";
		String thisMonthEndTime = "";
		try {
			thisMonthStateTime = HmsDateUtils.getFormatDateString(new Date(HmsDateUtils.getThisMonthStateTimeVal()),
					HmsDateUtils.FORMAT_DATETIME);
			thisMonthEndTime = HmsDateUtils.getFormatDateString(new Date(HmsDateUtils.getThisMonthEndTimeVal()),
					HmsDateUtils.FORMAT_DATETIME);
		} catch (ParseException e) {
			HmsOtsRoomOrderService.logger.error("日期格式转换异常：" + e.getMessage(), e);
		}
		EHotelWithBLOBs thisHotel = SessionUtils.getThisHotel();
		return hmsOtsRoomOrderMapper.checkinListCount(thisHotel.getId(), thisMonthStateTime, thisMonthEndTime).size();
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
