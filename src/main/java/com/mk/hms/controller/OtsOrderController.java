package com.mk.hms.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.HmsOtaOrderModel;
import com.mk.hms.model.HmsOtaRoomOrderModel;
import com.mk.hms.model.HmsUMemberModel;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.User;
import com.mk.hms.service.HmsOtsOrderService;
import com.mk.hms.service.HmsUMemberService;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsFileUtils;
import com.mk.hms.utils.HttpClientUtils;
import com.mk.hms.utils.SessionUtils;

/**
 * 订单控制器
 * @author hdy
 *
 */
@Controller
@RequestMapping("/otsorder")
public class OtsOrderController {
	
	private static final Logger logger = LoggerFactory.getLogger(OtsOrderController.class);
	
	@Autowired
	private HmsOtsOrderService hmsOtsOrderService;
	
	@Autowired
	private HmsUMemberService hmsUMemberService;
	
	/**
	 * 根据分页查询酒店全部订单信息
	 * @return 订单列表
	 */
	/*@RequestMapping(params = "findOtsOrdersInHotel")
	@ResponseBody
	public Map<String, Object> findOtsOrdersInHotel() {
		Map<String, Object> map = RequestUtils.getParameters();
		int pageNum = (Integer) map.get("pageNum");
		int pageSize = (Integer) map.get("pageSize");
		Map<String, Object> out = new HashMap<String, Object>();
		out.put("rows", hmsOtsOrderService.findHmsOtsOrdersInHotel(pageNum, pageSize));
		out.put("total", hmsOtsOrderService.findHmsOrdersInHotelCount());
		return out;
	}*/
	
	/**
	 * 获取当天订单信息
	 * @return 订单对象
	 */
	/*@RequestMapping(params = "findHmsOtsOrdersToday")
	@ResponseBody
	public Map<String, Object> findHmsOtsOrdersToday() {
		Map<String, Object> map = RequestUtils.getParameters();
		int pageNum = (Integer) map.get("pageNum");
		int pageSize = (Integer) map.get("pageSize");
		Map<String, Object> out = new HashMap<String, Object>();
		out.put("rows", hmsOtsOrderService.findHmsOtsOrdersToday(pageNum, pageSize));
		out.put("total", hmsOtsOrderService.findTodayHmsOrdersCount());
		return out;
	}*/
	
	/**
	 * 获取今日入住订单列表
	 * @return 客单列表
	 */
	/*@RequestMapping(params = "findCheckinTodayOrders")
	@ResponseBody
	public Map<String, Object> findCheckinTodayOrders() {
		Map<String, Object> map = RequestUtils.getParameters();
		int pageNum = (Integer)map.get("pageNum");
		int pageSize = (Integer)map.get("pageSize");
		Map<String, Object> out = new HashMap<String, Object>();
		out.put("rows", hmsOtsOrderService.findCheckinHmsOrders(pageNum, pageSize));
		out.put("total", hmsOtsOrderService.findCheckinHmsOrdersCount());
		return out;
	}*/
	
	/**
	 * 获取当月入住订单列表
	 * @return 客单列表
	 */
	/*@RequestMapping(params = "findCheckinMonthOrders")
	@ResponseBody
	public Map<String, Object> findCheckinMonthOrderList() {
		Map<String, Object> map = RequestUtils.getParameters();
		int pageNum = (Integer)map.get("pageNum");
		int pageSize = (Integer)map.get("pageSize");
		Map<String, Object> out = new HashMap<String, Object>();
		out.put("rows", hmsOtsOrderService.findHmsOrdersCheckinMonth(pageNum, pageSize));
		out.put("total", hmsOtsOrderService.findHmsOrdersCheckinMonthCount());
		return out;
	}*/
	
	/**
	 * 获取无效订单列表
	 * @return 客单列表
	 */
	/*@RequestMapping(params = "findInvalidOrders")
	@ResponseBody
	public Map<String, Object> findInvalidOrders() {
		Map<String, Object> map = RequestUtils.getParameters();
		int pageNum = (Integer)map.get("pageNum");
		int pageSize = (Integer)map.get("pageSize");
		Map<String, Object> out = new HashMap<String, Object>();
		out.put("rows", hmsOtsOrderService.findHmsOrdersInvalid(pageNum, pageSize));
		out.put("total", hmsOtsOrderService.findHmsOrdersInvalidCount());
		return out;
	}*/
	
	/**
	 * 通过分页查看订单
	 * @return 订单列表
	 */
	/*@RequestMapping(params = "findOtsOrders")
	@ResponseBody
	public List<HmsOtaOrderModel> findOtsOrdersByPage() {
		Map<String, Object> map = RequestUtils.getParameters();
		int pageNum = (Integer)map.get("pageNum");
		int pageSize = (Integer)map.get("pageSize");
		return hmsOtsOrderService.findHmsOtsOrders(pageNum, pageSize);
	}*/
	
	/**
	 * 前台切客
	 * @param request 请求
	 * @return 前台议价状态
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/bargainOrder")
	@ResponseBody
	public OutModel bargainOrderByWaiter(long otaorderid, double changeprice, HttpServletRequest request) throws SessionTimeOutException {
		OutModel out = new OutModel(false);
		// 获取订单
		HmsOtaOrderModel order = hmsOtsOrderService.findOtsOrderById(otaorderid);
		User user = SessionUtils.getSessionLoginUser().getUser();
		if (user.getId() != order.getSpreadUser()) {
			out.setErrorMsg("此订单不属于您的切客订单");
			return out;
		}
		// 是否有切客优惠券
		boolean isCheckOtsOrder = hmsOtsOrderService.isExitPromotionInOtsOrder(ContentUtils.CHECK_PROMOTION_KEY, order.getId());
		if (!isCheckOtsOrder) {
			out.setErrorMsg("此订单没有前台切客优惠券");
			return out;
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("otaorderid", otaorderid + "");
		paramMap.put("changeprice", changeprice + "");
		JSONObject obj = null;
		OtsOrderController.logger.info("开始调用前台切客接口");
		try {
			obj = HttpClientUtils.post(HmsFileUtils.getSysContentItem(ContentUtils.WAITER_BARGAIN_ADDRESS), paramMap);
			if (null == obj) {
				OtsOrderController.logger.error("调用前台切客接口异常");
				out.setErrorMsg("调用前台切客接口异常");
				return out;
			}
			if (!obj.getBoolean("success")) {
				OtsOrderController.logger.error(obj.getString("errmsg") + "错误码：" + obj.getString("errcode"));
				out.setErrorMsg("调用前台切客接口错误：" + obj.getString("errmsg") + "错误码，" + obj.getString("errcode"));
				return out;
			}
		} catch (IOException e) {
			OtsOrderController.logger.error("调用前台切客接口异常：" + e.getMessage(), e);
		}
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * 获取ots订单客单信息
	 * @return 订单信息
	 */
	@RequestMapping("/findOtsOrderById")
	@ResponseBody
	public  OutModel findOtsOrderById(long otaorderid) {
		OutModel out = new OutModel(false);
		// 获取订单
		HmsOtaOrderModel order = hmsOtsOrderService.findOtsOrderById(otaorderid);
		if (null ==  order) {
			out.setErrorMsg("获取订单信息失败");
			return out;
		}
		HmsOtaRoomOrderModel roomOrder = hmsOtsOrderService.findOtsRoomOrderById(otaorderid);
		if (null == roomOrder) {
			out.setErrorMsg("获取订单数据失败");
			return out;
		}
		HmsUMemberModel member = hmsUMemberService.findUMemberById(roomOrder.getMid());
		if (null == member) {
			out.setErrorMsg("获取会员数据失败");
			return out;
		}
		JSONObject obj = new JSONObject();
		obj.put("otaOrderId", order.getId());
		obj.put("roomTypeName", roomOrder.getRoomTypeName());
		obj.put("roomNo", roomOrder.getRoomNo());
		obj.put("daynumber", order.getDaynumber());
		obj.put("totalPrice", order.getTotalPrice());
		obj.put("memberName", member.getLoginName());
		out.setAttribute(obj);
		out.setSuccess(true);
		return out;
	}
}
