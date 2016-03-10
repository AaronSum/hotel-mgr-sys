package com.mk.hms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.OrderDetailMapper;
import com.mk.hms.mapper.OrderShoppingMapper;
import com.mk.hms.model.OrderDetail;
import com.mk.hms.model.OrderDetailCriteria;
import com.mk.hms.model.OrderShopping;
import com.mk.hms.model.OrderShoppingCriteria;
import com.mk.hms.utils.OrderGenerator;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.LoginUser;
import com.mk.hms.view.Page;
import com.mk.hms.view.SaveOrderShopping;

/**
 * WareShoppingCart  service
 */
@Service
@Transactional
public class OrderShoppingService {

	private static final Logger logger = LoggerFactory.getLogger(OrderShoppingService.class);
	
	@Autowired
	private OrderShoppingMapper orderShoppingMapper = null;
	
	@Autowired
	private OrderDetailMapper orderDetailMapper = null;
	
	@Autowired
	private WareShoppingCartService wareShoppingCartService = null;
	
	/**
	 * 保存订单数据
	 * @param SaveOrderShopping 订单信息
	 * @return int 1-保存成功 0-保存失败
	 */
	public int saveOrder(SaveOrderShopping saveOrderShopping) {
		try {
			saveOrderShopping.setOrderid(this.generatorOrderId()); 
			this.getOrderShoppingMapper().insert(saveOrderShopping);
			
			for (OrderDetail orderDetail:saveOrderShopping.getOrderDetails()) {
				orderDetail.setOrderid(saveOrderShopping.getOrderid()); 
				this.getOrderDetailMapper().insert(orderDetail);
				//删除购物车中的数据
				this.getWareShoppingCartService().deleteShoppingCartForWareId(orderDetail.getWareid());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("OrderShoppingService 保存订单数据异常" + e.getMessage());
			return 0;
		}
		return 1;
	}
	
	/**
	 * 获取订单数据
	 * @param Page 分页信息
	 * @return ShowCartData
	 * @throws SessionTimeOutException 
	 */
	public Map<String, Object> findOrder(Page page) throws SessionTimeOutException {
		Map<String, Object> res = new HashMap<String, Object>();
		LoginUser loginUser = SessionUtils.getSessionLoginUser();
		try {
			
			if(!checkLoginUser(loginUser)){
				logger.debug("WareShoppingCartService 获取登录用户为null");
				return null;
			}
			
			OrderShoppingCriteria orderShoppingCriteria = new OrderShoppingCriteria();
			orderShoppingCriteria.createCriteria().andUseridEqualTo(loginUser.getUser().getId())
			                                      .andHotelidEqualTo(loginUser.getThisHotel().getId());
			int total = page.getTotal();
			 
			//total
			if (total <= 0 ) {
				total = this.getOrderShoppingMapper().countByExample(orderShoppingCriteria);
			}
			//page
			orderShoppingCriteria.setLimitStart(page.getStartIndex());
			orderShoppingCriteria.setLimitEnd(page.getPageSize());
			//order
			orderShoppingCriteria.setOrderByClause("cretedate DESC");
			
			//界面展示的数据对象
			List<SaveOrderShopping> saveOrderShoppings = new ArrayList<SaveOrderShopping>();
			BeanCopier beanCopier = BeanCopier.create(OrderShopping.class, SaveOrderShopping.class, false);
			//数据库查询出来的订单数据
			List<OrderShopping> orderShoppings = this.getOrderShoppingMapper().selectByExample(orderShoppingCriteria);
			for (int i = 0; i < orderShoppings.size(); i++) {
				SaveOrderShopping saveOrderShopping = new SaveOrderShopping();
				OrderShopping orderShopping = orderShoppings.get(i);
				//将 数据库查询出来的订单数据 orderShopping  转化成    界面展示的数据对象saveOrderShopping
				beanCopier.copy(orderShopping, saveOrderShopping, null);
				
				List<OrderDetail> orderDetails = this.findOrderDetailByOrderId(saveOrderShopping.getOrderid());
				//增加订单数据明细数据
				saveOrderShopping.setOrderDetails(orderDetails);
				saveOrderShoppings.add(saveOrderShopping);
			}
			res.put("total", total);
			res.put("rows", saveOrderShoppings);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 获取订单数据
	 * @param orderId 订单号
	 * @return OrderShopping
	 */
	public OrderShopping findOrderByOrderId(String orderId) {
		OrderShoppingCriteria orderShoppingCriteria = new OrderShoppingCriteria();
		orderShoppingCriteria.createCriteria().andOrderidEqualTo(orderId);
		List<OrderShopping> orderShoppings = this.getOrderShoppingMapper().selectByExample(orderShoppingCriteria);
		if(orderShoppings == null ){
			return null;
		}
		return orderShoppings.get(0);
	}
	
	/**
	 * 根据订单ID删除订单数据（该功能用逻辑删除）
	 * @param orderId 订单号
	 * @return 非0-删除成功 0-删除失败
	 */
	public int deleteOrderByOrderId(String orderId) {
		OrderShopping orderShopping = this.findOrderByOrderId(orderId);
		if(orderShopping == null){
			logger.debug("OrderShoppingService.deleteOrderByOrderId 根据订单ID删除订单数据异常");
			return 0;
		}
		orderShopping.setIstype(new Integer(30));//订单状态更新为取消
		return this.getOrderShoppingMapper().updateByPrimaryKey(orderShopping);
	}
	
	/**
	 * 根据订单ID查询订单明细数据(分页)
	 * @param orderId 订单号
	 * @return List<OrderDetail>
	 */
	public Map<String, Object> findOrderDetailByOrderId(String orderId,Page page) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			OrderDetailCriteria OrderDetailCriteria = new OrderDetailCriteria();
			OrderDetailCriteria.createCriteria().andOrderidEqualTo(orderId);
			int total = page.getTotal();
			 
			//total
			if (total <= 0 ) {
				total = this.getOrderDetailMapper().countByExample(OrderDetailCriteria);
			}
			//page
			OrderDetailCriteria.setLimitStart(page.getStartIndex());
			OrderDetailCriteria.setLimitEnd(page.getPageSize());
			
			res.put("total", total);
			res.put("rows", this.getOrderDetailMapper().selectByExample(OrderDetailCriteria));
		} catch (Exception e) {
			logger.debug("OrderShoppingService.findOrderDetailByOrderId 根据订单ID查询订单明细数据");
			return null;
		}
		return res;
	}
	
	/**
	 * 根据订单ID查询订单明细数据
	 * @param orderId 订单号
	 * @return List<OrderDetail>
	 */
	public List<OrderDetail> findOrderDetailByOrderId(String orderId) {
		OrderDetailCriteria OrderDetailCriteria = new OrderDetailCriteria();
		OrderDetailCriteria.createCriteria().andOrderidEqualTo(orderId);
		return this.getOrderDetailMapper().selectByExample(OrderDetailCriteria);
	}
	
	/**
	 * 生成订单ID（暂时先用这个方法不支持高并发）
	 * @param orderId 订单号(年月日时分秒毫秒 + userid + hotelid)
	 * @return long
	 * @throws SessionTimeOutException 
	 */
	private synchronized String generatorOrderId() throws SessionTimeOutException {
		StringBuffer orderId = new StringBuffer();
		LoginUser loginUser = SessionUtils.getSessionLoginUser();
		
		if(!checkLoginUser(loginUser)){
			logger.debug("WareShoppingCartService 获取登录用户为null");
			throw new RuntimeException("WareShoppingCartService 获取登录用户为null");
		}
		//orderId.append(HmsDateUtils.getNowFormatDateString("YYYYMMddHHmmssSSS"));
		orderId.append(loginUser.getUser().getId().toString());
		orderId.append(loginUser.getThisHotel().getId());
		orderId.append(OrderGenerator.newOrderId());
		
		return orderId.toString();
	}
	
	/**
	 * 校验登录的用户信息
	 * @return
	 */
	private boolean checkLoginUser(LoginUser loginUser) {
		
		if(loginUser == null){
			logger.debug("WareShoppingCartService 获取登录用户为null");
			return false;
		}
		
		if(loginUser.getUser() == null){
			logger.debug("WareShoppingCartService 获取用户为null");
			return false;
		}
		
		if(loginUser.getThisHotel() == null){
			logger.debug("WareShoppingCartService 获取用户酒店信息为null");
			return false;
		}
		return true;
	}
	
	public OrderShoppingMapper getOrderShoppingMapper() {
		return orderShoppingMapper;
	}

	public OrderDetailMapper getOrderDetailMapper() {
		return orderDetailMapper;
	}

	public WareShoppingCartService getWareShoppingCartService() {
		return wareShoppingCartService;
	}
	
}
