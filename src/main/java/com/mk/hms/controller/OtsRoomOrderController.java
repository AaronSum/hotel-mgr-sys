package com.mk.hms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.hms.controller.helper.OtsRoomOrderControllerHelper;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.HmsOrderMoreInfoModel;
import com.mk.hms.model.HmsOtaOrderModel;
import com.mk.hms.model.OtaCheckinRoomOrderModel;
import com.mk.hms.service.HmsOtsOrderService;
import com.mk.hms.service.HmsOtsRoomOrderService;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsEnumsUtils;
import com.mk.hms.utils.HmsStringUtils;
import com.mk.hms.view.Page;

/**
 * hms 客单控制器
 * @author hdy
 *
 */
@Controller
@RequestMapping("/otsroomorder")
public class OtsRoomOrderController {

	@Autowired
	private HmsOtsRoomOrderService hmsOtsRoomOrderService;
	
	@Autowired
	private HmsOtsOrderService hmsOtsOrderService;
	
	/**
	 * 根据分页查询客单信息
	 * @return 客单列表
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/findOtsRoomOrders")
	@ResponseBody
	public Map<String, Object> findOtsRoomOrderList(Page page) throws SessionTimeOutException {
		Map<String, Object> out = new HashMap<String, Object>();
		// 获取订单信息
		List<OtaCheckinRoomOrderModel> checkinOrderList = hmsOtsRoomOrderService.findHmsRoomOrdersInHotel(page);
		List<Long> checkinOrderIds = new ArrayList<Long>();
		for (OtaCheckinRoomOrderModel order : checkinOrderList) {
			checkinOrderIds.add(order.getOtaOrderId());
		}
		String ids = HmsStringUtils.join(checkinOrderIds.toArray(), ContentUtils.CHAR_COMMA);
		if (HmsStringUtils.isBlank(ids)) {
			out.put("rows", null);
			out.put("total", 0);
		} else {
			List<HmsOtaOrderModel> orderList = hmsOtsOrderService.getOtaOrdersByOrders(ids);
			for (OtaCheckinRoomOrderModel checkInOrder : checkinOrderList) {
				for (HmsOtaOrderModel otaOrder : orderList) {
					if (checkInOrder.getOtaOrderId() == otaOrder.getId()) {
						checkInOrder.setBegintime(otaOrder.getBegintime());
						checkInOrder.setEndtime(otaOrder.getEndtime());
						checkInOrder.setCreattime(otaOrder.getCreatetime());
						checkInOrder.setOrderStatus(otaOrder.getOrderStatus());
						checkInOrder.setOrderStatusName(HmsEnumsUtils.getText4HmsOtaOrserStatusEnum(otaOrder.getOrderStatus()));
						checkInOrder.setSpreadUser(otaOrder.getSpreadUser());
						break;
					}
				}
			}
			out.put("rows", checkinOrderList);
			out.put("total", hmsOtsRoomOrderService.listCount());
		}
		return out;
	}
	
	/**
	 * 获取今日订单列表
	 * @return 客单列表
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/findTodayOtsRoomOrders")
	@ResponseBody
	public Map<String, Object> findTodayOtsRoomOrderList(Page page) throws SessionTimeOutException {
		Map<String, Object> out = new HashMap<String, Object>();
		List<HmsOtaOrderModel> orderList = hmsOtsOrderService.findHmsOtsOrdersToday();
		String ids = OtsRoomOrderControllerHelper.getOtsOrderIds(orderList);
		if (HmsStringUtils.isBlank(ids)) {
			out.put("rows", null);
			out.put("total", 0);
		} else {
			List<OtaCheckinRoomOrderModel> checkinList = hmsOtsRoomOrderService.findHmsRoomOrders(ids, page);
			List<Long> checkinOrderIds = new ArrayList<Long>();
			for (OtaCheckinRoomOrderModel order : checkinList) {
				checkinOrderIds.add(order.getOtaOrderId());
			}
			String checkinIds = HmsStringUtils.join(checkinOrderIds.toArray(), ContentUtils.CHAR_COMMA);
			if (HmsStringUtils.isBlank(checkinIds)) {
				out.put("rows", null);
				out.put("total", 0);
			} else {
				for (OtaCheckinRoomOrderModel checkInOrder : checkinList) {
					for (HmsOtaOrderModel otaOrder : orderList) {
						if (checkInOrder.getOtaOrderId() == otaOrder.getId()) {
							checkInOrder.setBegintime(otaOrder.getBegintime());
							checkInOrder.setEndtime(otaOrder.getEndtime());
							checkInOrder.setCreattime(otaOrder.getCreatetime());
							checkInOrder.setOrderStatus(otaOrder.getOrderStatus());
							checkInOrder.setOrderStatusName(HmsEnumsUtils.getText4HmsOtaOrserStatusEnum(otaOrder.getOrderStatus()));
							checkInOrder.setSpreadUser(otaOrder.getSpreadUser());
							break;
						}
					}
				}
				out.put("rows", checkinList);
				out.put("total", hmsOtsRoomOrderService.todayListCount(ids));
			}
		}
		return out;
	}
	
	/**
	 * 获取今日入住订单列表
	 * @return 客单列表
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/findCheckinTodayOtsRoomOrders")
	@ResponseBody
	public Map<String, Object> findCheckinTodayOtsRoomOrderList(Page page) throws SessionTimeOutException {
		Map<String, Object> out = new HashMap<String, Object>();
		List<OtaCheckinRoomOrderModel> checkinList = hmsOtsRoomOrderService.findTodayCheckinHmsRoomOrders(page);
		List<Long> checkinOrderIds = new ArrayList<Long>();
		for (OtaCheckinRoomOrderModel order : checkinList) {
			checkinOrderIds.add(order.getOtaOrderId());
		}
		String checkinIds = HmsStringUtils.join(checkinOrderIds.toArray(), ContentUtils.CHAR_COMMA);
		if (HmsStringUtils.isBlank(checkinIds)) {
			out.put("rows", null);
			out.put("total", 0);
		} else {
			List<HmsOtaOrderModel> otsOrderList = hmsOtsOrderService.getOtaOrdersByOrders(checkinIds);
			for (OtaCheckinRoomOrderModel checkInOrder : checkinList) {
				for (HmsOtaOrderModel otaOrder : otsOrderList) {
					if (checkInOrder.getOtaOrderId() == otaOrder.getId()) {
						checkInOrder.setBegintime(otaOrder.getBegintime());
						checkInOrder.setEndtime(otaOrder.getEndtime());
						checkInOrder.setCreattime(otaOrder.getCreatetime());
						checkInOrder.setOrderStatus(otaOrder.getOrderStatus());
						checkInOrder.setOrderStatusName(HmsEnumsUtils.getText4HmsOtaOrserStatusEnum(otaOrder.getOrderStatus()));
						checkInOrder.setSpreadUser(otaOrder.getSpreadUser());
						break;
					}
				}
			}
			out.put("rows", checkinList);
			out.put("total", hmsOtsRoomOrderService.checkinTodayListCount());
		}
		return out;
	}
	
	/**
	 * 获取预定订单列表
	 * @return 客单列表
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/findBookOtsRoomOrderList")
	@ResponseBody
	public Map<String, Object> findBookOtsRoomOrderList(Page page) throws SessionTimeOutException {
		Map<String, Object> out = new HashMap<String, Object>();
		List<OtaCheckinRoomOrderModel> bookList = hmsOtsRoomOrderService.findBookHmsRoomOrders(page);
		List<Long> bookOrderIds = new ArrayList<Long>();
		for (OtaCheckinRoomOrderModel order : bookList) {
			bookOrderIds.add(order.getOtaOrderId());
		}
		String bookIds = HmsStringUtils.join(bookOrderIds.toArray(), ContentUtils.CHAR_COMMA);
		if (HmsStringUtils.isBlank(bookIds)) {
			out.put("rows", null);
			out.put("total", 0);
		} else {
			List<HmsOtaOrderModel> otsOrderList = hmsOtsOrderService.getOtaOrdersByOrders(bookIds);
			for (OtaCheckinRoomOrderModel checkInOrder : bookList) {
				for (HmsOtaOrderModel otaOrder : otsOrderList) {
					if (checkInOrder.getOtaOrderId() == otaOrder.getId()) {
						checkInOrder.setBegintime(otaOrder.getBegintime());
						checkInOrder.setEndtime(otaOrder.getEndtime());
						checkInOrder.setCreattime(otaOrder.getCreatetime());
						checkInOrder.setOrderStatus(otaOrder.getOrderStatus());
						checkInOrder.setOrderStatusName(HmsEnumsUtils.getText4HmsOtaOrserStatusEnum(otaOrder.getOrderStatus()));
						checkInOrder.setSpreadUser(otaOrder.getSpreadUser());
						break;
					}
				}
			}
			out.put("rows", bookList);
			out.put("total", hmsOtsRoomOrderService.bookListCount());
		}
		return out;
	}
	
	/**
	 * 获取当月入住订单列表
	 * @return 客单列表
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/findCheckinMonthOtsRoomOrders")
	@ResponseBody
	public Map<String, Object> findCheckinMonthOtsRoomOrderList(Page page) throws SessionTimeOutException {
		Map<String, Object> out = new HashMap<String, Object>();
		List<OtaCheckinRoomOrderModel> checkinList = hmsOtsRoomOrderService.findMonthCheckinHmsRoomOrders(page);
		List<Long> checkinOrderIds = new ArrayList<Long>();
		for (OtaCheckinRoomOrderModel order : checkinList) {
			checkinOrderIds.add(order.getOtaOrderId());
		}
		String checkinIds = HmsStringUtils.join(checkinOrderIds.toArray(), ContentUtils.CHAR_COMMA);
		if (HmsStringUtils.isBlank(checkinIds)) {
			out.put("rows", null);
			out.put("total", 0);
		} else {
			List<HmsOtaOrderModel> otsOrderList = hmsOtsOrderService.getOtaOrdersByOrders(checkinIds);
			for (OtaCheckinRoomOrderModel checkInOrder : checkinList) {
				for (HmsOtaOrderModel otaOrder : otsOrderList) {
					if (checkInOrder.getOtaOrderId() == otaOrder.getId()) {
						checkInOrder.setBegintime(otaOrder.getBegintime());
						checkInOrder.setEndtime(otaOrder.getEndtime());
						checkInOrder.setCreattime(otaOrder.getCreatetime());
						checkInOrder.setOrderStatus(otaOrder.getOrderStatus());
						checkInOrder.setOrderStatusName(HmsEnumsUtils.getText4HmsOtaOrserStatusEnum(otaOrder.getOrderStatus()));
						checkInOrder.setSpreadUser(otaOrder.getSpreadUser());
						break;
					}
				}
			}
			out.put("rows", checkinList);
			out.put("total", hmsOtsRoomOrderService.checkinMonthListCount());
		}
		return out;
	}
	
	/**
	 * 获取无效订单列表
	 * @return 客单列表
	 */
	@RequestMapping("/findInvalidOtsRoomOrders")
	@ResponseBody
	public Map<String, Object> findInvalidOtsRoomOrderList(Page page) {
		Map<String, Object> out = new HashMap<String, Object>();
		out.put("rows", hmsOtsRoomOrderService.findHmsRoomOrdersInvalidByPage(page));
		out.put("total", hmsOtsRoomOrderService.listCountInvalid());
		return out;
	}

	/**
	 * 获取订单更多信息
	 * @return 客单Ids
	 */
	@RequestMapping("/findsOrderMoreInfo")
	@ResponseBody
	public List<HmsOrderMoreInfoModel> findsOrderMoreInfo(String orderIds){
		if(orderIds.length()<=0){
			return null;
		}
		return hmsOtsRoomOrderService.findsOrderMoreInfo(orderIds);
	}
	/**
	 * 获取单个订单更多信息
	 * @return 客单Id
	 */
	@RequestMapping("/findOrderMoreInfo")
	@ResponseBody
	public HmsOrderMoreInfoModel findOrderMoreInfo(long orderId){
		if(orderId<=0){
			return null;
		}
		return hmsOtsRoomOrderService.findOrderMoreInfo(orderId);
	}
}
