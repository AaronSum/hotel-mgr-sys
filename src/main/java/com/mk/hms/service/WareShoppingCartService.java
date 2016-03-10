package com.mk.hms.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.HmsShoppingHotelMapper;
import com.mk.hms.mapper.THotelMapper;
import com.mk.hms.mapper.WareShoppingMapper;
import com.mk.hms.model.HmsShoppingHotelCriteria;
import com.mk.hms.model.OutModel;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsDateUtils;
import com.mk.hms.utils.HmsRedisCacheUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.LoginUser;
import com.mk.hms.view.ShowCartData;
import com.mk.hms.view.ShowCartDetailData;

/**
 * WareShoppingCart  service
 */
@Service
@Transactional
public class WareShoppingCartService {

	private static final Logger logger = LoggerFactory.getLogger(WareShoppingCartService.class);
	
	@Autowired
	private WareShoppingMapper wareShoppingMapper = null;

	@Autowired
	private THotelMapper tHotelMapper = null;
	@Autowired
	private HmsShoppingHotelMapper hmsShoppingHotelMapper = null;
	/**
	 * 保存购物车数据到redis中
	 * @param ShowCartDetailData 购物车明细数据
	 * @return 1-保存成功 0-保存失败
	 * @throws SessionTimeOutException 
	 */
	public OutModel saveShoppingCart(ShowCartDetailData showCartDetailData) throws SessionTimeOutException {
		OutModel out = new OutModel();
		LoginUser loginUser = SessionUtils.getSessionLoginUser();
		try {
			ShowCartData showCartData = this.getRedisShoppingCartData();
			//如果redis中没有，则进行首次保存购物车数据
			if(showCartData == null){
				this.saveShoppingCartForRedisNull(showCartDetailData);
				out.setSuccess(true);
				return out;
			}
			
			//设置购物车商品基本信息
			if(!this.checkLoginUser(loginUser)){
				out.setSuccess(false);
				out.setErrorMsg("登录用户信息有误,请联系管理员!");
				return out;
			}
			
			showCartDetailData.setUserid(loginUser.getUser().getId());
			showCartDetailData.setHotelid(loginUser.getThisHotel().getId());
			showCartDetailData.setCretedate(HmsDateUtils.getDateFromeDateString(HmsDateUtils.getNowFormatDateString(HmsDateUtils.FORMAT_DATE),HmsDateUtils.FORMAT_DATE));
			showCartDetailData.setUpdatedate(HmsDateUtils.getDateFromeDateString(HmsDateUtils.getNowFormatDateString(HmsDateUtils.FORMAT_DATE),HmsDateUtils.FORMAT_DATE));

			
			//获取缓存中的购物车商品明细
			List<ShowCartDetailData> showCartDetailDatas = showCartData.getCartDetails();
			if(showCartDetailDatas == null){
				showCartDetailDatas = new ArrayList<ShowCartDetailData>();
			}else{
				boolean falg = false;//新商品是否在购物车中已经存在，如果已经存在 则修改数量即可
				for (int i = 0; i < showCartDetailDatas.size(); i++) {
					ShowCartDetailData redisCartDtail = showCartDetailDatas.get(i);
					if(redisCartDtail.getWareid().longValue() == showCartDetailData.getWareid().longValue()){
						int oldNum  = redisCartDtail.getNum() == null?0:Integer.parseInt(redisCartDtail.getNum());
						int newNum  = showCartDetailData.getNum() == null?0:Integer.parseInt(showCartDetailData.getNum());
						redisCartDtail.setNum(String.valueOf(oldNum + newNum));
						falg = true;
						break;
					}
				}
				
				//如果不存在 则将商品增加到购物车中
				if(!falg){
					showCartDetailDatas.add(showCartDetailData);
					showCartData.setCartDetails(showCartDetailDatas);
				}
			}
			this.setRedisShoppingCartData(showCartData);
			out.setSuccess(true);
		} catch (Exception e) {
			out.setSuccess(false);
			out.setErrorMsg("保存购物车数据到redis中失败!");
			e.printStackTrace();
		}
		return out;
	}
	
	
	/**
	 * 保存购物车数据到redis中（暂时不用）
	 * @param ShowCartData 购物车数据
	 */
	/*private int saveShoppingCart(ShowCartData cartData) {
		try {
			LoginUser loginUser = SessionUtils.getSessionLoginUser();
			
			if(!this.checkLoginUser(loginUser)){
				logger.debug("WareShoppingCartService 获取登录用户为null");
				return 0;
			}
			
			if(cartData.getUserid() == null){
				cartData.setUserid(loginUser.getUser().getId());
				cartData.setLoginuser(loginUser.getUser().getName());
				cartData.setHotelid(loginUser.getThisHotel().getId());
				cartData.setCretedate(HmsDateUtils.getDateFromeDateString(HmsDateUtils.getNowFormatDateString(HmsDateUtils.FORMAT_DATE),HmsDateUtils.FORMAT_DATE));
				cartData.setUpdatedate(HmsDateUtils.getDateFromeDateString(HmsDateUtils.getNowFormatDateString(HmsDateUtils.FORMAT_DATE),HmsDateUtils.FORMAT_DATE));
				
				for (int i = 0; i < cartData.getCartDetails().size(); i++) {
					ShowCartDetailData cartDetailData = cartData.getCartDetails().get(i);
					cartDetailData.setUserid(loginUser.getUser().getId());
					cartDetailData.setHotelid(loginUser.getThisHotel().getId());
					cartDetailData.setCretedate(HmsDateUtils.getDateFromeDateString(HmsDateUtils.getNowFormatDateString(HmsDateUtils.FORMAT_DATE),HmsDateUtils.FORMAT_DATE));
					cartDetailData.setUpdatedate(HmsDateUtils.getDateFromeDateString(HmsDateUtils.getNowFormatDateString(HmsDateUtils.FORMAT_DATE),HmsDateUtils.FORMAT_DATE));
				}
			}
			Cache cache = this.getRedisCache();
			String key = this.getRedisShoppingCartKey();
			cache.put(key, cartData);
		} catch (Exception e) {
			logger.debug("WareShoppingCartService 保存购物车数据到redis中异常" + e.getMessage());
			return 0;
		}
		return 1;
	}*/

	
	/**
	 * 从redis中获取购物车数据
	 * @return ShowCartData
	 */
	public OutModel findShoppingCart() {
		OutModel out = new OutModel();
		ShowCartData cartData = new ShowCartData();
		try {
			cartData = this.getRedisShoppingCartData();
			
			if(cartData == null ){
				logger.debug("WareShoppingCartService 获取购物车数据异常");
				out.setSuccess(false);
				out.setErrorMsg("获取购物车数据异常,请联系管理员!");
				return null;
			}
			for (int i = 0; i < cartData.getCartDetails().size(); i++) {
				ShowCartDetailData cartDetailData = cartData.getCartDetails().get(i);
				cartDetailData.setWareShopping(this.getWareShoppingMapper().selectByPrimaryKey(cartDetailData.getWareid()));
			}
			out.setSuccess(true);
			out.setAttribute(cartData);
		} catch (Exception e) {
			logger.debug("WareShoppingCartService 获取购物车数据异常" + e.getMessage());
			out.setSuccess(false);
			out.setErrorMsg("获取购物车数据异常,请联系管理员!");
			return null;
		}
		return out;
	}
	
	/**
	 * 从redis中获取购物车商品数量
	 * @return int
	 */
	public int findShoppingCartWareCount() {
		ShowCartData cartData = null;
		try {
			cartData = this.getRedisShoppingCartData();
			if(cartData == null || cartData.getCartDetails() == null){
				return 0;
			}
		} catch (Exception e) {
			logger.debug("WareShoppingCartService 获取购物车数据异常" + e.getMessage());
			return 0;
		}
		return cartData.getCartDetails().size();
	}
	/**
	 * 根据商品ID删除购物车中的数据
	 * @param wareId 商品ID
	 * @return 1-删除成功 0-删除失败
	 * @throws SessionTimeOutException 
	 */
	public OutModel deleteShoppingCartForWareId(long wareId) throws SessionTimeOutException {
		OutModel out = new OutModel();
		ShowCartData cartData = this.getRedisShoppingCartData();
		if(cartData == null){
			logger.debug("WareShoppingCartService 该用户购物车数据异常");
			out.setSuccess(false);
			out.setErrorMsg("获取购物车数据异常,请联系管理员!");
		} 
		try {
			List<ShowCartDetailData> cartDetails = cartData.getCartDetails();
			for (int i = cartDetails.size()-1; i >= 0 ; i--) {
				ShowCartDetailData cartDetail = cartDetails.get(i);
				if(cartDetail.getWareid().longValue() == wareId){
					cartDetails.remove(i);
				}
			}
			cartData.setCartDetails(cartDetails);
			this.setRedisShoppingCartData(cartData);
			out.setSuccess(true);
		} catch (Exception e) {
			logger.debug("WareShoppingCartService 删除购物车数据异常" + e.getMessage());
			out.setSuccess(false);
			out.setErrorMsg("删除购物车数据异常,请联系管理员!");
		}
		return out;
	}
	
	/**
	 * 更新购物车中商品数量
	 * @param wareId 商品ID
	 * @param num  购买商品数量
	 * @return 1-更新成功 0-更新失败
	 * @throws SessionTimeOutException 
	 */
	public OutModel updateShoppingCartWareIdNum(long wareId,String num) throws SessionTimeOutException {
		OutModel out = new OutModel();
		ShowCartData cartData = this.getRedisShoppingCartData();
		if(cartData == null){
			logger.debug("WareShoppingCartService 该用户购物车数据异常");
			out.setSuccess(false);
			out.setErrorMsg("获取购物车数据异常,请联系管理员!");
		} 
		try {
			List<ShowCartDetailData> cartDetails = cartData.getCartDetails();
			for (int i = 0; i < cartDetails.size(); i++) {
				ShowCartDetailData cartDetail = cartDetails.get(i);
				if(cartDetail.getWareid() == wareId){
					cartDetail.setNum(num);
				}
			}
			cartData.setCartDetails(cartDetails);
			this.setRedisShoppingCartData(cartData);
			out.setSuccess(true);
		} catch (Exception e) {
			logger.debug("WareShoppingCartService 更新购物车数据异常" + e.getMessage());
			out.setSuccess(false);
			out.setErrorMsg(" 更新购物车数据异常,请联系管理员!");
		}
		return out;
	}
	
	/**
	 * 获取菜单权限
	 * @param menuName 菜单名称
	 * @param hotelId  酒店ID
	 * @return
	 */
	public OutModel getShoppingCartMenu(String menuName, String hotelId) {
		boolean isExistHotel = false;
		HmsShoppingHotelCriteria hshc = new HmsShoppingHotelCriteria();
		hshc.createCriteria().andHotelidEqualTo(hotelId).andMenunameEqualTo(menuName);
		int count = this.getHmsShoppingHotelMapper().countByExample(hshc);
		if(count > 0){
			isExistHotel = true;
		}
		
		OutModel out = new OutModel();
		out.setSuccess(isExistHotel);
		return out;
	}
	
	/**
	 * 保存购物车数据到redis中,当redis没有当前用户的购物车数据的时候调用的
	 * @param ShowCartDetailData 购物车明细数据
	 * @throws SessionTimeOutException 
	 */
	private void saveShoppingCartForRedisNull(ShowCartDetailData cartDetailData) throws SessionTimeOutException {
		ShowCartData cartData = new ShowCartData();
		LoginUser loginUser = SessionUtils.getSessionLoginUser();
		try {
			
			if(!this.checkLoginUser(loginUser)){
				logger.debug("WareShoppingCartService 获取登录用户为null");
				throw new RuntimeException("获取用户信息失败");
			}
			
			cartData.setUserid(loginUser.getUser().getId());
			cartData.setLoginuser(loginUser.getUser().getLoginname());
			cartData.setHotelid(loginUser.getThisHotel().getId());
			cartData.setCretedate(HmsDateUtils.getDateFromeDateString(HmsDateUtils.getNowFormatDateString(HmsDateUtils.FORMAT_DATE),HmsDateUtils.FORMAT_DATE));
			cartData.setUpdatedate(HmsDateUtils.getDateFromeDateString(HmsDateUtils.getNowFormatDateString(HmsDateUtils.FORMAT_DATE),HmsDateUtils.FORMAT_DATE));
			
			
			cartDetailData.setUserid(loginUser.getUser().getId());
			cartDetailData.setHotelid(loginUser.getThisHotel().getId());
			cartDetailData.setCretedate(HmsDateUtils.getDateFromeDateString(HmsDateUtils.getNowFormatDateString(HmsDateUtils.FORMAT_DATE),HmsDateUtils.FORMAT_DATE));
			cartDetailData.setUpdatedate(HmsDateUtils.getDateFromeDateString(HmsDateUtils.getNowFormatDateString(HmsDateUtils.FORMAT_DATE),HmsDateUtils.FORMAT_DATE));
			
			List<ShowCartDetailData> cartDetailDatas = new ArrayList<ShowCartDetailData>();
			cartDetailDatas.add(cartDetailData);
			
			cartData.setCartDetails(cartDetailDatas);
			
			Cache cache = this.getRedisCache();
			String key = this.getRedisShoppingCartKey();
			cache.put(key, cartData);
		} catch (Exception e) {
			logger.debug("WareShoppingCartService 保存购物车数据到redis中异常" + e.getMessage());
			throw new RuntimeException("WareShoppingCartService 保存购物车数据到redis中异常" + e.getMessage());
		}
	}
	/**
	 * 获取当前用户购物车中的数据
	 * @return
	 * @throws SessionTimeOutException 
	 */
	private ShowCartData getRedisShoppingCartData() throws SessionTimeOutException{
		Cache cache = this.getRedisCache();
		String key = this.getRedisShoppingCartKey();
		if(key == null){
			throw new RuntimeException("获取redis购物车中的key失败");
		}
		ShowCartData showCartData  = (ShowCartData)cache.get(key, ShowCartData.class);
		return showCartData;
	}
	
	/**
	 * 保存当前用户购物车中的数据
	 * @return
	 * @throws SessionTimeOutException 
	 */
	private void setRedisShoppingCartData(ShowCartData showCartData) throws SessionTimeOutException{
		Cache cache = this.getRedisCache();
		String key = this.getRedisShoppingCartKey();
		if(showCartData.getCartDetails() == null || showCartData.getCartDetails().size() == 0){
			cache.put(key, null);
		}else{
			cache.put(key, showCartData);
		}
	}
	
	/**
	 * 获取redis缓存
	 * @return
	 */
	private Cache getRedisCache(){
		return HmsRedisCacheUtils.getRedisCache("HmsShoppingCartData");
	}
	
	/**
	 * 获取redis中购物车的key
	 * 根据  (登录用户ID + 酒店ID + "HmsShoppingCartData") 作为key,放在redis中
	 * @return 
	 * @throws SessionTimeOutException 
	 */
	private String getRedisShoppingCartKey() throws SessionTimeOutException{
		LoginUser loginUser = SessionUtils.getSessionLoginUser();
		
		if(!this.checkLoginUser(loginUser)){
			logger.debug("WareShoppingCartService 获取登录用户为null");
			return null;
		}
		String key = ContentUtils.HMS_SHOPPING_CART_DATA + loginUser.getThisHotel().getId() + "_" + loginUser.getUser().getId();
		return key;
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
	
	
	public WareShoppingMapper getWareShoppingMapper() {
		return wareShoppingMapper;
	}

	public THotelMapper gettHotelMapper() {
		return tHotelMapper;
	}


	public HmsShoppingHotelMapper getHmsShoppingHotelMapper() {
		return hmsShoppingHotelMapper;
	}


 
	
}
