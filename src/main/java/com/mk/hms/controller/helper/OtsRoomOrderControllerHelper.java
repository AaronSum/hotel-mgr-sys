package com.mk.hms.controller.helper;

import java.util.ArrayList;
import java.util.List;

import com.mk.hms.model.HmsOtaOrderModel;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsStringUtils;

/**
 * 酒店客单控制器帮助类
 * @author hdy
 *
 */
public class OtsRoomOrderControllerHelper {

	/**
	 * 获取订单编号id集合
	 * @param otsOrderList
	 * @param hmsOtsOrderService
	 * @return
	 */
	public static String getOtsOrderIds(List<HmsOtaOrderModel> otsOrderList) {
		List<Long> orderIds = new ArrayList<Long>();
		for (HmsOtaOrderModel order : otsOrderList) {
			orderIds.add(order.getId());
		}
		String ids = HmsStringUtils.join(orderIds.toArray(), ContentUtils.CHAR_COMMA);
		return ids;
	}
}
