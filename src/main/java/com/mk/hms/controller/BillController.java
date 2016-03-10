package com.mk.hms.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.mk.hms.enums.HmsSimpleBBillConfirmCheckEnum;
import com.mk.hms.model.BillSpecialExample;
import com.mk.hms.model.EHotel;
import com.mk.hms.service.BillSpecialService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.BillConfirmCheck;
import com.mk.hms.model.OutModel;
import com.mk.hms.service.BillService;
import com.mk.hms.service.HmsHotelMessageService;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.Page;

/**
 * 清算 控制器
 * @author hdy
 *
 */
@Controller
@RequestMapping("/bill")
public class BillController {

	@Autowired
	private BillService billService = null;
	@Autowired
	private HmsHotelMessageService hmsHotelMessageService;
	@Autowired
	private BillSpecialService billSpecialService;
	/**
	 * 根据订单时间获取订单
	 * @param yearAndMonth 订单时间
	 * @param hotelName 酒店名称
	 * @param checkStatus 入住状态
	 * @param confirmStatus 确认状态
	 * @param page 分页对象
	 * @return 订单列表
	 * @throws Exception 
	 */
	@RequestMapping("/byBilltime")
	@ResponseBody
	public Map<String, Object> queryByBilltime(String yearAndMonth, String hotelName, String checkStatus,
			String confirmStatus, Page page) throws Exception {
		return this.getBillService().queryByBilltime(yearAndMonth, hotelName, checkStatus, confirmStatus, page);
	}
	/**
	 * 获得周订单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getWeekOrders")
	@ResponseBody
	public Map<String, Object> getWeekOrders( String checkStatus, Page page) throws Exception {
		return this.getBillService().getWeekOrders(checkStatus,page);
	}
	/**
	 * 获取某个酒店所有账单(使用中)
	 * @return 账单页面数据
	 * @throws Exception 
	 */
	@RequestMapping("/byHotelid")
	@ResponseBody
	public Map<String, Object> queryBillByHotelId(String yearAndMonth, String checkStatus, Page page) throws Exception{
		return this.getBillService().queryBillByHotelId(yearAndMonth, checkStatus, page);
	}
	
	@RequestMapping("/getIsFreeze")
	@ResponseBody
	public String getIsFreeze() throws Exception{
		long hotelId = SessionUtils.getThisHotelId();
		return this.getBillService().getIsFreeze(hotelId);
	}
	/**
	 * 账单金额大于等于酒店配置的结算金额下限时，页面“提前结算”功能按钮才可正常使用，否则为disabled不可使用状态
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/isFreeze")
	@ResponseBody
	public Map<String,Object> isFreeze() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("isThreshold", this.getHmsHotelMessageService().getHotelThreshold());
		map.put("isFreeze", this.getBillService().isFreeze());
		return map;
	}
	
	/**
	 * 调用接口
	 * @throws Exception 
	 * 
	 */
	@RequestMapping("/preCalAccount")
    @ResponseBody
	public OutModel preCalAccount() throws Exception{
		return this.getBillService().preCalAccount();
	}
	
	/**
	 * 获取酒店账单概要信息
	 * @return 账单概要
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/modelByHotelid")
	@ResponseBody
	public BillConfirmCheck getBillModelByHotelId(String yearAndMonth) throws SessionTimeOutException {
		return this.getBillService().getBillModelByHotelId(yearAndMonth);
	}
	
	/**
	 * 客服确认账单金额
	 * @return 确认状态
	 * @throws ParseException 
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/pmsAmount")
	@ResponseBody
	public OutModel pmsConfirmBillAmount(long billId, long checkInfoId, String confirmAmount, String backText) throws ParseException, SessionTimeOutException {
		return this.getBillService().pmsConfirmBillAmount(billId, checkInfoId, confirmAmount, backText);
	}
	
	/**
	 * 酒店确认账单金额
	 * @return 确认状态
	 * @throws ParseException 
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/hmsAmount")
	@ResponseBody
	public OutModel hmsConfirmBillAmount(long billId) throws ParseException, SessionTimeOutException {
		return this.getBillService().hmsConfirmBillAmount(billId);
	}
	
	/**
	 * 客服审核金额操作
	 * @return 审核状态
	 * @throws ParseException 
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/pmsCheckAmount")
	@ResponseBody
	public OutModel pmsCheckBillAmount(long billId, long checkInfoId, String backText) throws ParseException, SessionTimeOutException {
		return this.getBillService().pmsCheckBillAmount(billId, checkInfoId, backText);
	}
	
	
	/**
	 * 酒店审核金额操作
	 * @return 审核状态
	 * @throws ParseException 
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/hmsCheckAmount")
	@ResponseBody
	public OutModel hmsCheckBillAmount(long billId, String checkText) throws ParseException, SessionTimeOutException {
		return this.getBillService().hmsCheckBillAmount(billId, checkText);
	}
	
	/**
	 * 获取审核详情列表
	 * @return 审核详情列表
	 */
	@RequestMapping("/checkInfo")
	@ResponseBody
	public Map<String, Object> queryBillCheckInfo(long billId, Page page) {
		return this.getBillService().queryBillCheckInfo(billId, page);
	}
	
	/**
	 * 获取账单详细订单列表
	 * @return 订单详情列表
	 * @throws Exception 
	 */
	@RequestMapping("/billInfo")
	@ResponseBody
	public Map<String, Object> queryBillInfo(long billId, Page page) throws Exception {
		return this.getBillService().queryBillInfo(billId, page);
	}
	
	/*******************需求变更后的简化版********************/
	
	/**
	 * 获取酒店账单概要信息
	 * @return 账单概要
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/simpleByHotel")
	@ResponseBody
	public BillConfirmCheck simpleQueryBillByHotel(String yearAndMonth) throws SessionTimeOutException {
		return this.getBillService().simpleQueryBillByHotel(yearAndMonth);
	}
	
	/**
	 * 获取审核详情列表(使用中)
	 * @return 审核详情列表
	 */
	@RequestMapping("/simpleCheckInfo")
	@ResponseBody
	public Map<String, Object> simpleQueryBillCheckInfo(long billId, Page page) {
		return this.getBillService().simpleQueryBillCheckInfo(billId, page);
	}
	
	/**
	 * 获取账单详细订单列表(使用中)
	 * @return 订单详情列表
	 * @throws Exception 
	 */
	@RequestMapping("/simpleBillInfo")
	@ResponseBody
	public Map<String, Object> simpleQueryBillInfo(long billId, String method, Page page) throws Exception {
		return this.getBillService().simpleQueryBillInfo(billId, method, page);
	}
	
	/**
	 * 酒店确认账单金额
	 * @return 确认状态
	 * @throws ParseException 
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/simpleConfirmAmount")
	@ResponseBody
	public OutModel simpleHmsConfirmBillAmount(long billId) throws ParseException, SessionTimeOutException {
		return this.getBillService().simpleHmsConfirmBillAmount(billId);
	}
	
	/**
	 * 酒店审核金额操作
	 * @return 审核状态
	 * @throws ParseException 
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/simpleCheckAmount")
	@ResponseBody
	public OutModel simpleHmsCheckBillAmount(long billId, String checkText, String changeCost) throws ParseException, SessionTimeOutException {
		return this.getBillService().simpleHmsCheckBillAmount(billId, checkText, changeCost);
	}


	@RequestMapping(value = "/getSpecialBills",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> getSpecialBills(Integer checkStatus, Page page) throws Exception {
		BillSpecialExample example = new BillSpecialExample();
		BillSpecialExample.Criteria criteria =  example.createCriteria();
		//已确认状态
		criteria.andCheckstatusGreaterThanOrEqualTo(HmsSimpleBBillConfirmCheckEnum.Confirmed.getValue());

		//1 未付款 2已付款
		if(null == checkStatus){
			//
		}else if(1 == checkStatus.intValue()
				|| 2 ==checkStatus.intValue() ){
			criteria.andFinancestatusEqualTo(checkStatus);
		}

		if(page.getPageSize() == 0){
			page.setPageSize(15);
		}
		EHotel thisHotel = SessionUtils.getThisHotel();
		if (null == thisHotel) {
			throw new Exception("当前酒店登录超时");
		}
		criteria.andHotelidEqualTo(thisHotel.getId());
		return billSpecialService.getBillSpecial(example, page);
	}

	/**
	 * 获取账单详细订单列表(使用中)
	 * @return 订单详情列表
	 * @throws Exception
	 */
	@RequestMapping("/specialBillInfo")
	@ResponseBody
	public Map<String, Object> getBillSpecialDetail(long billId, Page page) throws Exception {
		if(page.getPageSize() == 0){
			page.setPageSize(15);
		}
		return billSpecialService.getBillSpecialDetail(billId, page);
	}

	/**
	 * 酒店审核金额操作
	 * @return 审核状态
	 * @throws java.text.ParseException
	 * @throws com.mk.hms.exception.SessionTimeOutException
	 */
	@RequestMapping("/specialCheckAmount")
	@ResponseBody
	public OutModel specialCheckAmount(long billId, String checkText, String changeCost) throws ParseException, SessionTimeOutException {
		return billSpecialService.specialCheckAmount(billId, checkText, changeCost);
	}

	/**
	 * 酒店确认账单金额
	 * @return 确认状态
	 * @throws ParseException
	 * @throws SessionTimeOutException
	 */
	@RequestMapping("/specialConfirmAmount")
	@ResponseBody
	public OutModel specialConfirmAmount(long billId) throws ParseException, SessionTimeOutException {
		return billSpecialService.specialConfirmAmount(billId);
	}
	
	private BillService getBillService() {
		return billService;
	}

	private HmsHotelMessageService getHmsHotelMessageService() {
		return hmsHotelMessageService;
	}


	
}
