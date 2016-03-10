package com.mk.hms.view;

import java.util.ArrayList;
import java.util.List;

import com.mk.hms.model.OrderDetail;
import com.mk.hms.model.OrderShopping;

/**
 * 保存订单的数据(Order + OrderDetail)
 */
public class SaveOrderShopping extends OrderShopping{
	
	
    private List<OrderDetail> OrderDetails = new ArrayList<OrderDetail>();  ;

	public List<OrderDetail> getOrderDetails() {
		return OrderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		OrderDetails = orderDetails;
	}
    
}