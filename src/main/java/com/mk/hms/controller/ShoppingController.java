package com.mk.hms.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.OrderDetail;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.WareCategory;
import com.mk.hms.model.WareImage;
import com.mk.hms.model.WareShopping;
import com.mk.hms.service.HomeService;
import com.mk.hms.service.OrderShoppingService;
import com.mk.hms.service.WareCategoryService;
import com.mk.hms.service.WareImageService;
import com.mk.hms.service.WarePromotionService;
import com.mk.hms.service.WareService;
import com.mk.hms.service.WareShoppingCartService;
import com.mk.hms.utils.HmsRedisCacheUtils;
import com.mk.hms.view.Page;
import com.mk.hms.view.SaveOrderShopping;
import com.mk.hms.view.ShowCartData;
import com.mk.hms.view.ShowCartDetailData;
import com.mk.hms.view.WarePromotionView;

/**
 * 购物控制器
 * @author wangchen 
 *
 */
@Controller
@RequestMapping("/shopping")
public class ShoppingController {
	
	@Autowired
	private WareCategoryService wareCategoryService = null;
	
	@Autowired
	private WarePromotionService  warePromotionService = null;
	
	@Autowired
	private WareService wareService = null;
	
	@Autowired
	private WareImageService wareImageService = null;
	
	@Autowired
	private WareShoppingCartService wareShoppingCartService = null;
	
	@Autowired
	private OrderShoppingService orderShoppingService = null;
	
	@Autowired
	private HomeService homeService = null;
	
	/**
	 * 获取商品分类信息
	 * @return 商品分类信息
	 */
	@RequestMapping("/category")
	@ResponseBody
	public List<WareCategory> findCategoryList() {
		return this.getWareCategoryService().findCategoryList();
	}
	

	/**
	 * 获取商品信息
	 * @return 商品信息
	 */
	@RequestMapping("/wares")
	@ResponseBody
	public Map<String, Object> findWareList(long categoryId, Page page) {
		return this.getWareService().query(categoryId, page);
	}
	
	/**
	 * 获取商品列表的图片
	 * @param WareId 商品ID
	 * @param imageType 获取图片类型(1-大图片  2-中图片  3-小图片)
	 * @return List<WareImage>
	 */
	@RequestMapping("/findWareDetail")
	@ResponseBody
	public WareShopping findWareDetail(long wareId) {
		return this.getWareService().find(wareId);
	}
	
	/**
	 * 获取商品列表的图片
	 * @param WareId 商品ID
	 * @return List<WareImage>
	 */
	@RequestMapping("/findWareImages")
	@ResponseBody
	public List<WareImage> findWareImageListByWareId(long wareId) {
		return this.getWareImageService().findWareImageListByWareId(wareId);
	}

	
	/**
	 * 保存购物车数据到redis中
	 * @param ware 购买商品Id
	 * @param num  购买商品数量
	 * @return int 1-成功 0失败
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/saveShoppingCart")
	@ResponseBody
	public OutModel saveShoppingCart(long wareId,String num ) throws SessionTimeOutException {
		ShowCartDetailData showCartDetailData = new ShowCartDetailData();
		showCartDetailData.setWareid(wareId);
		showCartDetailData.setNum(num);
		return this.getWareShoppingCartService().saveShoppingCart(showCartDetailData);
	}
	
	/**
	 * 从redis中获取购物车数据
	 * @return ShowCartData
	 */
	@RequestMapping("/findShoppingCart")
	@ResponseBody
	public OutModel findShoppingCart() {
		return this.getWareShoppingCartService().findShoppingCart();
	}
	
	/**
	 * 从redis中获取购物车商品数量
	 * @return int
	 */
	@RequestMapping("/findShoppingCartWareCount")
	@ResponseBody
	public int findShoppingCartWareCount() {
		  return this.getWareShoppingCartService().findShoppingCartWareCount();
	}
	
	/**
	 * 更新购物车中商品数量
	 * @return 1-更新成功 0-更新失败
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/updateShoppingCartWareIdNum")
	@ResponseBody
	public OutModel updateShoppingCartWareIdNum(long wareId,String num) throws SessionTimeOutException {
		return this.getWareShoppingCartService().updateShoppingCartWareIdNum(wareId, num);
	}
	/**
	 * 根据商品ID删除购物车中的数据
	 * @return 1-删除成功 0-删除失败
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/deleteShoppingCartForWareId")
	@ResponseBody
	public OutModel deleteShoppingCartForWareId(long wareId) throws SessionTimeOutException {
		return this.getWareShoppingCartService().deleteShoppingCartForWareId(wareId);
	}
	
	
	/**
	 * 保存订单数据
	 * @param SaveOrderShopping 订单信息
	 * @return int 1-保存成功 0-保存失败
	 */
	@RequestMapping("/saveOrder")
	@ResponseBody
	public int saveOrder(String jsonOrder) {
		SaveOrderShopping saveOrderShopping = (SaveOrderShopping)JSON.parseObject(jsonOrder, SaveOrderShopping.class);
		return this.getOrderShoppingService().saveOrder(saveOrderShopping);
	}

	/**
	 * 获取订单数据
	 * @return ShowCartData
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/findOrder")
	@ResponseBody
	public Map<String, Object> findOrder(Page page) throws SessionTimeOutException {
		return this.getOrderShoppingService().findOrder(page);
	}
	
	/**
	 * 根据订单ID删除订单数据（该功能用逻辑删除）暂时不用 
	 * @return 1-删除成功 0-删除失败
	 */
	@RequestMapping("/deleteOrderByOrderId")
	@ResponseBody
	public int deleteOrderByOrderId(String orderId) {
		return this.getOrderShoppingService().deleteOrderByOrderId(orderId);
	}
	
	
	/**
	 * 根据订单ID查询订单明细数据(分页)
	 * @return List<OrderDetail>
	 */
	@RequestMapping("/findOrderDetailByOrderIdOfPage")
	@ResponseBody
	public Map<String, Object> findOrderDetailByOrderId(String orderId,Page page) {
		return this.getOrderShoppingService().findOrderDetailByOrderId(orderId, page);
	}
	
	/**
	 * 根据订单ID查询订单明细数据
	 * @return List<OrderDetail>
	 */
	@RequestMapping("/findOrderDetailByOrderId")
	@ResponseBody
	public List<OrderDetail> findOrderDetailByOrderId(String orderId) {
		return this.getOrderShoppingService().findOrderDetailByOrderId(orderId);
	}
	
	/**
	 * 获取菜单权限
	 * @param menuName 菜单名称
	 * @param hotelId  酒店ID
	 * @return
	 */
	@RequestMapping("/getShoppingCartMenu")
	@ResponseBody
	public OutModel getShoppingCartMenu(String menuName,String hotelId) {
		return this.getWareShoppingCartService().getShoppingCartMenu(menuName, hotelId);
	}
	
	/**
	 * 获取商品属性 
	 * @param wareId 商品ID
	 * @return OutModel
	 */
	@RequestMapping("/findWareAttr")
	@ResponseBody
	public OutModel findWareAttr(Long wareId) {
		return this.getWareService().findWareAttr(wareId);
	}
	
	/**
	 * 获取商品促销信息
	 * @param wareId 商品ID
	 * @return OutModel
	 */
	@RequestMapping("/findWarePromotion")
	@ResponseBody
	public OutModel findWarePromotion(Long wareId) {
		return this.getWarePromotionService().findWarePromotion(wareId);
	}
	
	/**
	 * 根据商品ID获取促销价格
	 * @param List<WarePromotionView>
	 * @return 促销价格
	 */
	@RequestMapping("/getWarePromotionData")
	@ResponseBody
	public OutModel findWarePromotion(String wareList) {
		List<WarePromotionView> list = null;
		if(!StringUtils.isBlank(wareList)){
			list = JSON.parseArray(wareList,WarePromotionView.class);
		}
		return this.getWarePromotionService().getWarePromotionData(list);
	}
	
	/**
	 * 获取redis中的数据(开发人员使用的)
	 * @return Object
	 */
	@RequestMapping("/getRedisData")
	@ResponseBody
	public ShowCartData getRedisData(String type,String redisKey) {
		/*Class<?> clazz = null;
        try{
        	clazz = Class.forName(type);
        	clazz.newInstance();
        }catch (Exception e) {
            e.printStackTrace();
        }*/
	    
		Cache cache = HmsRedisCacheUtils.getRedisCache("HmsShoppingCartData");
		ShowCartData obj = (ShowCartData)cache.get(redisKey, ShowCartData.class);
		return obj;
	}
	
	private WareCategoryService getWareCategoryService() {
		return wareCategoryService;
	}
	
	private WareService getWareService() {
		return wareService;
	}

	public WareImageService getWareImageService() {
		return wareImageService;
	}

	public WareShoppingCartService getWareShoppingCartService() {
		return wareShoppingCartService;
	}

	public OrderShoppingService getOrderShoppingService() {
		return orderShoppingService;
	}

	public WarePromotionService getWarePromotionService() {
		return warePromotionService;
	}
	
	
}
