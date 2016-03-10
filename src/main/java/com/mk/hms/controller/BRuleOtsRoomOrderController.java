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
import com.mk.hms.service.BRuleHmsOtsOrderService;
import com.mk.hms.service.BRuleHmsOtsRoomOrderService;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsEnumsUtils;
import com.mk.hms.utils.HmsStringUtils;
import com.mk.hms.view.Page;

/**
 * hms 客单控制器
 * @author hdy
 *
 */
//findOtsRoomOrders:全部订单， orderType,pageNum,pageSize
//findTodayOtsRoomOrders：今日订单，
//findCheckinTodayOtsRoomOrders：近日已入住订单，
//findCheckinMonthOtsRoomOrders：当月已入住订单，
//findOrderMoreInfo：订单更多数据
@Controller
@RequestMapping("/brule")
public class BRuleOtsRoomOrderController {

	@Autowired
	private BRuleHmsOtsRoomOrderService hmsOtsRoomOrderService;
	
	@Autowired
	private BRuleHmsOtsOrderService hmsOtsOrderService;
	
	/**
	 * 根据分页查询客单信息
	 * @return 客单列表
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/findOtsRoomOrders")
	@ResponseBody
	public Map<String, Object> findOtsRoomOrderList(int orderType, Page page,String search) throws SessionTimeOutException {
		Map<String, Object> out = new HashMap<String, Object>();
		if(search !=null && !"".equals(search)){
			long orderId = -1;
			try{
				// long型数据转化错误，则不进行查询直接返回空结果集
				orderId = Long.parseLong(search);
			}catch(Exception e){
				out.put("rows", null);
				out.put("total", 0);
				return out;
			}
			List<OtaCheckinRoomOrderModel> checkinOrderList = this.getHmsOtsRoomOrderService().findHmsRoomOrderById(orderType, orderId);
			int size = 0;
			if(checkinOrderList != null && checkinOrderList.size()>0){
				size = checkinOrderList.size();
			}
			out.put("rows", checkinOrderList);
			out.put("total", size);
		}else{
		// 获取订单信息
		List<OtaCheckinRoomOrderModel> checkinOrderList = this.getHmsOtsRoomOrderService().findHmsRoomOrdersInHotel(orderType, page);
//		List<Long> checkinOrderIds = new ArrayList<Long>();
//		for (OtaCheckinRoomOrderModel order : checkinOrderList) {
//			checkinOrderIds.add(order.getOtaOrderId());
//		}
//		String ids = HmsStringUtils.join(checkinOrderIds.toArray(), ContentUtils.CHAR_COMMA);
//		if (HmsStringUtils.isBlank(ids)) {
//			out.put("rows", null);
//			out.put("total", 0);
//		} else {
//			List<HmsOtaOrderModel> orderList = hmsOtsOrderService.getOtaOrdersByOrders(orderType,ids);
//			List<OtaCheckinRoomOrderModel> results = new ArrayList<OtaCheckinRoomOrderModel>();
//			for (OtaCheckinRoomOrderModel checkInOrder : checkinOrderList) {
//				for (HmsOtaOrderModel otaOrder : orderList) {
//					if (checkInOrder.getOtaOrderId() == otaOrder.getId()) {
//						checkInOrder.setBegintime(otaOrder.getBegintime());
//						checkInOrder.setEndtime(otaOrder.getEndtime());
//						checkInOrder.setCreattime(otaOrder.getCreatetime());
//						checkInOrder.setOrderStatus(otaOrder.getOrderStatus());
//						checkInOrder.setOrderStatusName(HmsEnumsUtils.getText4HmsOtaOrserStatusEnum(otaOrder.getOrderStatus()));
//						checkInOrder.setSpreadUser(otaOrder.getSpreadUser());
//						results.add(checkInOrder);
//						break;
//					}
//				}
//			}
			out.put("rows", checkinOrderList);
			out.put("total", this.getHmsOtsRoomOrderService().listCount(orderType));
		}
//		}
		return out;
	}
	/**
	 * 根据id查询客单信息
	 * @return 客单
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/findHmsRoomOrderById")
	@ResponseBody
	public Map<String, Object> findHmsRoomOrderById(int orderType,long orderId,Page page) throws SessionTimeOutException {
		Map<String, Object> out = new HashMap<String, Object>();
		// 获取订单信息
		List<OtaCheckinRoomOrderModel> checkinOrderList = this.getHmsOtsRoomOrderService().findHmsRoomOrderById(orderType, orderId);
		int size = 0;
		if(checkinOrderList != null && checkinOrderList.size()>0){
			size = checkinOrderList.size();
		}
		out.put("rows", checkinOrderList);
		out.put("total", size);
		return out;
	}
	
	/**
	 * 获取今日订单列表
	 * @return 客单列表
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/findTodayOtsRoomOrders")
	@ResponseBody
	public Map<String, Object> findTodayOtsRoomOrderList(int orderType, Page page) throws SessionTimeOutException {
		Map<String, Object> out = new HashMap<String, Object>();
		List<HmsOtaOrderModel> orderList = this.getHmsOtsOrderService().findHmsOtsOrdersToday(orderType);
		String ids = OtsRoomOrderControllerHelper.getOtsOrderIds(orderList);
		if (HmsStringUtils.isBlank(ids)) {
			out.put("rows", null);
			out.put("total", 0);
		} else {
			List<OtaCheckinRoomOrderModel> checkinList = this.getHmsOtsRoomOrderService().findHmsRoomOrders(orderType, ids, page);
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
				out.put("total", this.getHmsOtsRoomOrderService().todayListCount(orderType,ids));
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
	public Map<String, Object> findCheckinTodayOtsRoomOrderList(int orderType, Page page) throws SessionTimeOutException {
		Map<String, Object> out = new HashMap<String, Object>();
		List<OtaCheckinRoomOrderModel> checkinList = this.getHmsOtsRoomOrderService().findTodayCheckinHmsRoomOrders(orderType, page);
		List<Long> checkinOrderIds = new ArrayList<Long>();
		for (OtaCheckinRoomOrderModel order : checkinList) {
			checkinOrderIds.add(order.getOtaOrderId());
		}
		String checkinIds = HmsStringUtils.join(checkinOrderIds.toArray(), ContentUtils.CHAR_COMMA);
		if (HmsStringUtils.isBlank(checkinIds)) {
			out.put("rows", null);
			out.put("total", 0);
		} else {
			List<HmsOtaOrderModel> otsOrderList = this.getHmsOtsOrderService().getOtaOrdersByOrders(orderType,checkinIds);
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
			out.put("total", this.getHmsOtsRoomOrderService().checkinTodayListCount(orderType));
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
	public Map<String, Object> findBookOtsRoomOrderList(int orderType, Page page) throws SessionTimeOutException {
		Map<String, Object> out = new HashMap<String, Object>();
		List<OtaCheckinRoomOrderModel> bookList = this.getHmsOtsRoomOrderService().findBookHmsRoomOrders(orderType, page);
		List<Long> bookOrderIds = new ArrayList<Long>();
		for (OtaCheckinRoomOrderModel order : bookList) {
			bookOrderIds.add(order.getOtaOrderId());
		}
		String bookIds = HmsStringUtils.join(bookOrderIds.toArray(), ContentUtils.CHAR_COMMA);
		if (HmsStringUtils.isBlank(bookIds)) {
			out.put("rows", null);
			out.put("total", 0);
		} else {
			List<HmsOtaOrderModel> otsOrderList = this.getHmsOtsOrderService().getOtaOrdersByOrders(orderType,bookIds);
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
			out.put("total", this.getHmsOtsRoomOrderService().bookListCount(orderType));
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
	public Map<String, Object> findCheckinMonthOtsRoomOrderList(int orderType, Page page) throws SessionTimeOutException {
		Map<String, Object> out = new HashMap<String, Object>();
		List<OtaCheckinRoomOrderModel> checkinList = this.getHmsOtsRoomOrderService().findMonthCheckinHmsRoomOrders(orderType, page);
		List<Long> checkinOrderIds = new ArrayList<Long>();
		for (OtaCheckinRoomOrderModel order : checkinList) {
			checkinOrderIds.add(order.getOtaOrderId());
		}
		String checkinIds = HmsStringUtils.join(checkinOrderIds.toArray(), ContentUtils.CHAR_COMMA);
		if (HmsStringUtils.isBlank(checkinIds)) {
			out.put("rows", null);
			out.put("total", 0);
		} else {
			List<HmsOtaOrderModel> otsOrderList = this.getHmsOtsOrderService().getOtaOrdersByOrders(orderType,checkinIds);
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
			out.put("total", this.getHmsOtsRoomOrderService().checkinMonthListCount(orderType));
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
		out.put("rows", this.getHmsOtsRoomOrderService().findHmsRoomOrdersInvalidByPage(page));
		out.put("total", this.getHmsOtsRoomOrderService().listCountInvalid());
		return out;
	}

	/**
	 * 获取订单更多信息
	 * @return 客单Ids
	 */
	@RequestMapping("/findsOrderMoreInfo")
	@ResponseBody
	public List<HmsOrderMoreInfoModel> findsOrderMoreInfo(String orderIds){
		if (HmsStringUtils.isBlank(orderIds)) {
			return null;
		}
		return this.getHmsOtsRoomOrderService().findsOrderMoreInfo(orderIds);
	}
	/**
	 * 获取单个订单更多信息
	 * @return 客单Id
	 */
	@RequestMapping("/findOrderMoreInfo")
	@ResponseBody
	public HmsOrderMoreInfoModel findOrderMoreInfo(long orderId){
		if (orderId <= 0) {
			return null;
		}
		return this.getHmsOtsRoomOrderService().findOrderMoreInfo(orderId);
	}

	private BRuleHmsOtsRoomOrderService getHmsOtsRoomOrderService() {
		return hmsOtsRoomOrderService;
	}

	private BRuleHmsOtsOrderService getHmsOtsOrderService() {
		return hmsOtsOrderService;
	}
	
}
