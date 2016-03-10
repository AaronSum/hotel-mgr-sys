package com.mk.hms.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.mk.hms.db.HmsJdbcTemplate;
import com.mk.hms.enums.HmsBBillConfirmCheckEnum;
import com.mk.hms.enums.HmsBBillConfirmEnum;
import com.mk.hms.enums.HmsSimpleBBillConfirmCheckEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.BillCheckInfoMapper;
import com.mk.hms.mapper.BillConfirmCheckMapper;
import com.mk.hms.mapper.BillOrdersMapper;
import com.mk.hms.mapper.ExtBillConfirmCheckMapper;
import com.mk.hms.mapper.ExtBillOrdersMapper;
import com.mk.hms.mapper.HmsHotelMessageMapper;
import com.mk.hms.mapper.HotelBillSplitMapper;
import com.mk.hms.mapper.HotelRuleMapper;
import com.mk.hms.mapper.THotelMapper;
import com.mk.hms.mapper.WeekclearingMapper;
import com.mk.hms.model.BillCheckInfo;
import com.mk.hms.model.BillCheckInfoCriteria;
import com.mk.hms.model.BillConfirmCheck;
import com.mk.hms.model.BillConfirmCheckCriteria;
import com.mk.hms.model.BillConfirmCheckCriteria.Criteria;
import com.mk.hms.model.BillOrdersCriteria;
import com.mk.hms.model.EHotel;
import com.mk.hms.model.ExtBillConfirmCheck;
import com.mk.hms.model.ExtBillConfirmCheckCriteria;
import com.mk.hms.model.ExtBillOrders;
import com.mk.hms.model.ExtBillOrdersCriteria;
import com.mk.hms.model.HmsTHotelModel;
import com.mk.hms.model.HotelBillSplitCriteria;
import com.mk.hms.model.MUser;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.THotel;
import com.mk.hms.model.THotelCriteria;
import com.mk.hms.model.User;
import com.mk.hms.model.Weekclearing;
import com.mk.hms.model.WeekclearingCriteria;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsDateUtils;
import com.mk.hms.utils.HmsFileUtils;
import com.mk.hms.utils.HmsStringUtils;
import com.mk.hms.utils.HttpClientUtils;
import com.mk.hms.utils.Param;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.LoginUser;
import com.mk.hms.view.Page;

import net.sf.json.JSONObject;

/**
 * 清算 service
 * @author hdy
 *
 */
@Service
@Transactional
public class BillService {

	@Autowired
	private THotelMapper tHotelMapper = null;
	
	@Autowired
	private BillConfirmCheckMapper billConfirmCheckMapper = null;
	
	@Autowired
	private ExtBillConfirmCheckMapper extBillConfirmCheckMapper = null;
	
	@Autowired
	private BillCheckInfoMapper billCheckInfoMapper = null;
	
	@Autowired
	private BillOrdersMapper billOrdersMapper = null;
	
	@Autowired
	private ExtBillOrdersMapper extBillOrdersMapper = null;
	
	@Autowired
	private HotelBillSplitMapper hotelBillSplitMapper = null;
	@Autowired
	private HmsHotelMessageMapper hmsHotelMessageMapper = null;
	
	@Autowired
	private HotelRuleMapper hotelRuleMapper = null;
	
	@Autowired
	private WeekclearingMapper weekclearingMapper = null;
	private static final Logger logger = LoggerFactory
			.getLogger(BillService.class);
	
	/**
	 * 根据订单时间获取订单
	 * @param yearAndMonth 订单时间
	 * @param hotelName 酒店名称
	 * @param checkStatus 入住状态
	 * @param confirmStatus 确认状态
	 * @param page 分页对象
	 * @return 订单列表
	 */
	public Map<String, Object> queryByBilltime(String yearAndMonth, String hotelName, String checkStatus,
			String confirmStatus, Page page) throws Exception {
		// 是否登录
		MUser mUser = SessionUtils.getSessionPmsUser();
		if (null == mUser) {
			throw new Exception("请登录");
		}
		BillConfirmCheckCriteria billConfirmCheckCriteria = new BillConfirmCheckCriteria();
		// 如果没有日期，则默认查看上个月酒店结算列表
		if (HmsStringUtils.isBlank(yearAndMonth)) {
			yearAndMonth = HmsDateUtils.getPreviousMonth();
		}
		Criteria criteria = billConfirmCheckCriteria.createCriteria();
		criteria.andBilltimeEqualTo(yearAndMonth);
		// 如果就点名不为空，则用like匹配
		if (HmsStringUtils.isNotBlank(hotelName)) {
			// 获取酒店id列表
			List<THotel> hotelList = this.getByHotelName(hotelName);
			List<Long> hotelIds = new ArrayList<Long>();
			for (THotel t : hotelList) {
				hotelIds.add(t.getId());
			}
			if (hotelIds.size() > 0) {
				criteria.andHotelidIn(hotelIds);
			}
		}
		// 存在审核状态查询
		if (HmsStringUtils.isNotBlank(checkStatus)) {
			criteria.andCheckstatusEqualTo(NumberUtils.toInt(checkStatus));
		}
		// 存在审核状态查询
		if (HmsStringUtils.isNotBlank(confirmStatus)) {
			criteria.andConfirmstatusEqualTo(NumberUtils.toInt(confirmStatus));
		}
		return this.queryBill(billConfirmCheckCriteria, page);
	}

	/**
	 * 获取某个酒店所有账单(使用中)
	 * @return 账单列表
	 * @throws Exception 异常
	 */
	public Map<String, Object> queryBillByHotelId(String yearAndMonth, String checkStatus, Page page) throws Exception {
		EHotel thisHotel = SessionUtils.getThisHotel();
		if (null == thisHotel) {
			throw new Exception("当前酒店登录超时");
		}
		ExtBillConfirmCheckCriteria extCriteria = new ExtBillConfirmCheckCriteria();
		ExtBillConfirmCheckCriteria.Criteria criteria = extCriteria.createCriteria();
		extCriteria.setOrderByClause("id desc");
		criteria.andHotelidEqualTo(thisHotel.getId());
		// 存在账期
		if (HmsStringUtils.isNotBlank(yearAndMonth)) {
			criteria.andBilltimeEqualTo(yearAndMonth);
		}
		// 存在审核状态查询
		if (HmsStringUtils.isNotBlank(checkStatus)) {
			criteria.andCheckstatusEqualTo(NumberUtils.toInt(checkStatus));
		} else {
//			criteria.andCheckstatusGreaterThan(0);
//			criteria.andCheckstatusLessThan(8);
			List<Integer> list = new ArrayList<Integer>();
//			list.add(0);
			list.add(1);
			list.add(2);
			list.add(3);
			list.add(4);
			list.add(6);
//			list.add(10);
			list.add(12);
			criteria.andCheckstatusIn(list);
		}
//		return this.queryBill(billConfirmCheckCriteria, page);
		return this.queryExtBill(extCriteria, page, thisHotel.getId());
	}
	
	/**
	 * 获取概要信息
	 * @return 概要对象
	 * @throws SessionTimeOutException 
	 */
	public BillConfirmCheck getBillModelByHotelId(String yearAndMonth) throws SessionTimeOutException {
		EHotel thisHotel = SessionUtils.getThisHotel();
		BillConfirmCheckCriteria billConfirmCheckCriteria = new BillConfirmCheckCriteria();
		billConfirmCheckCriteria.createCriteria().andHotelidEqualTo(thisHotel.getId()).andBilltimeEqualTo(yearAndMonth);
		List<BillConfirmCheck> bills = this.getBillConfirmCheckMapper().selectByExample(billConfirmCheckCriteria);
		return bills.size() > 0 ? bills.get(0) : null;
	}
	/**
	 * 获得周订单
	 * @throws Exception 
	 */
	public Map<String, Object> getWeekOrders(String checkStatus, Page page) throws Exception{
		EHotel thisHotel = SessionUtils.getThisHotel();
		if (null == thisHotel) {
			throw new Exception("当前酒店登录超时");
		}
		WeekclearingCriteria example = new WeekclearingCriteria();
		WeekclearingCriteria.Criteria c = example.createCriteria();
		c.andHotelidEqualTo(thisHotel.getId());
		//只取已确认的
		if (HmsStringUtils.isNotBlank(checkStatus)) {//如果有，取某一个状态的单子
			c.andCheckstatusEqualTo(NumberUtils.toInt(checkStatus));
		}
//		else{//没有，取所有的单子
//			List<Integer> values = new ArrayList<Integer>();
//			values.add(1);
//			values.add(2);
//			values.add(3);
//			values.add(6);
//			values.add(12);
//			c.andCheckstatusIn(values);
//		}
		return getWeekOrders(example,page);
	}
	/**
	 * 分页取周订单
	 * @param example
	 * @param page
	 * @return
	 */
	public Map<String, Object> getWeekOrders(WeekclearingCriteria example,Page page){
		Map<String, Object> outMap = new HashMap<String, Object>();
		int count = this.getWeekclearingMapper().countByExample(example);
		outMap.put("total",count);
		example.setLimitStart(page.getStartIndex());
		example.setLimitEnd(page.getPageSize());
		
		List<Weekclearing> list = this.getWeekclearingMapper().selectByExample(example);
		outMap.put("rows",list);
		return outMap;
	}
	/**
	 * pms确认金额
	 * @return
	 * @throws ParseException 
	 * @throws SessionTimeOutException 
	 */
	public OutModel pmsConfirmBillAmount(long billId, long checkInfoId, String confirmAmount, String backText) throws ParseException, SessionTimeOutException {
		BillConfirmCheck checkBill = this.getBillConfirmCheckMapper().selectByPrimaryKey(billId);
		OutModel out = new OutModel(false);
		// 已经确认
		if (checkBill.getCheckstatus() == HmsBBillConfirmCheckEnum.Checked.getValue()
				|| checkBill.getConfirmstatus() >= HmsBBillConfirmEnum.Confirmed.getValue()) {
			out.setErrorMsg("该账单已确认");
			return out;
		}
		// 处理确认操作
		if (!confirmAmountIsLegitimacy(billId, confirmAmount)) {
			out.setErrorMsg("审核金额不合法，上下浮动为10%");
			return out;
		}
		// 是否在审核时间段内
		if (!checkBillIsLegitimacy(checkBill)) {
			out.setErrorMsg("审核订单不为上个月或者当前时间不在审核时间段内");
			return out;
		}
		// 确认信息
		// 添加确认金额
		BillConfirmCheck modifyBill = new BillConfirmCheck();
		modifyBill.setChangecost(NumberUtils.createBigDecimal(confirmAmount));
		modifyBill.setId(billId);
		this.getBillConfirmCheckMapper().updateByPrimaryKey(modifyBill);
		// 添加审核明细
		setPmsCheckInfo(billId, checkInfoId, backText, true);
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * hms 确定订单信息
	 * @return 订单信息
	 * @throws ParseException 异常
	 * @throws SessionTimeOutException 
	 */
	public OutModel hmsConfirmBillAmount(long billId) throws ParseException, SessionTimeOutException {
		BillConfirmCheck checkBill = this.getBillConfirmCheckMapper().selectByPrimaryKey(billId);
		OutModel out = new OutModel(false);
		// 已经确认
		if (checkBill.getCheckstatus() == HmsBBillConfirmCheckEnum.Checked.getValue()
				|| checkBill.getConfirmstatus() >= HmsBBillConfirmEnum.Confirmed.getValue()) {
			out.setErrorMsg("该账单已确认");
			return out;
		}
		// 是否在审核时间段内
		if (!checkBillIsLegitimacy(checkBill)) {
			out.setErrorMsg("审核订单不为上个月或者当前时间不在审核时间段内");
			return out;
		}
		// 确认信息
		if (checkBill.getChangecost().doubleValue() == 0) {
			BillConfirmCheck bill = new BillConfirmCheck();
			bill.setId(billId);
			bill.setChangecost(NumberUtils.createBigDecimal(checkBill.getServicecost() + ""));
			// 添加确认金额
			this.getBillConfirmCheckMapper().updateByPrimaryKey(bill);
		}
		// 添加审核明细
		setHmsCheckInfo("已确认", checkBill, true);
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * 客服审核金额操作
	 * @return 审核状态
	 * @throws ParseException  异常
	 * @throws SessionTimeOutException 
	 */
	public OutModel pmsCheckBillAmount(long billId, long checkInfoId, String backText) throws ParseException, SessionTimeOutException {
		// 返回对象
		OutModel out = new OutModel(false);
		BillConfirmCheck checkBill = this.getBillConfirmCheckMapper().selectByPrimaryKey(billId);
		if(!checkBillIsLegitimacy(checkBill)) {
			out.setSuccess(false);
			out.setErrorMsg("审核订单不为上个月或者当前时间不在审核时间段内");
			return out;
		}
		// 已经确认或已反馈给酒店
		if (checkBill.getCheckstatus() != HmsBBillConfirmCheckEnum.Checking.getValue()) {
			out.setErrorMsg("该账单已确认或已反馈给酒店");
			return out;
		}
		// 是否为空
		if (HmsStringUtils.isBlank(backText)) {
			out.setErrorMsg("该账单反馈内容不可为空");
			return out;
		}
		setPmsCheckInfo(billId, checkInfoId, backText, false);
		return out;
	}
	
	/**
	 * 酒店审核金额操作
	 * @return 审核状态
	 * @throws ParseException  异常
	 * @throws SessionTimeOutException 
	 */
	public OutModel hmsCheckBillAmount(long billId, String checkText) throws ParseException, SessionTimeOutException {
		// 返回对象
		OutModel out = new OutModel(false);
		BillConfirmCheck checkBill = this.getBillConfirmCheckMapper().selectByPrimaryKey(billId);
		if(!checkBillIsLegitimacy(checkBill)) {
			out.setSuccess(false);
			out.setErrorMsg("审核订单不为上个月或者当前时间不在审核时间段内");
			return out;
		}
		// 已经确认或已反馈给酒店
		if (checkBill.getCheckstatus() != HmsBBillConfirmCheckEnum.UnChecked.getValue() ||
				checkBill.getCheckstatus() != HmsBBillConfirmCheckEnum.Init.getValue()) {
			out.setErrorMsg("该账单已确认或已发起审核");
			return out;
		}
		// 是否为空
		if (HmsStringUtils.isBlank(checkText)) {
			out.setErrorMsg("该账单反馈内容不可为空");
			return out;
		}
		setHmsCheckInfo(checkText, checkBill, false);
		return out;
	}
	
	/**
	 * 获取审核详细
	 * @return 审核详情
	 */
	public Map<String, Object> queryBillCheckInfo(long billId, Page page) {
		// 获取参数
		Map<String, Object> outMap = new HashMap<String, Object>();
		BillCheckInfoCriteria billCheckInfoCriteria = new BillCheckInfoCriteria();
		billCheckInfoCriteria.setOrderByClause("checktime desc");
		billCheckInfoCriteria.createCriteria().andBillcheckidEqualTo(billId);
		outMap.put("total", this.getBillCheckInfoMapper().countByExample(billCheckInfoCriteria));
		billCheckInfoCriteria.setLimitStart(page.getStartIndex());
		billCheckInfoCriteria.setLimitEnd(page.getPageSize());
		outMap.put("rows", this.getBillCheckInfoMapper().selectByExample(billCheckInfoCriteria));
		return outMap;
	}
	
	/**
	 * 获取审核详细列表
	 * @return 审核详细列表
	 * @throws Exception 
	 */
	public Map<String, Object> queryBillInfo(long billId, Page page) throws Exception {
		BillConfirmCheck checkBill = this.getBillConfirmCheckMapper().selectByPrimaryKey(billId);
		if (null == checkBill) {
			throw new Exception("账单信息为空");
		}
		Map<String, Object> outMap = new HashMap<String, Object>();
		String billTime = checkBill.getBilltime() + "01";
		String startTime = formatDate(billTime, false) + " 00:00:00";
		String endTime = formatDate(billTime, true) + " 23:59:59";
		BillOrdersCriteria billOrdersCriteria = new BillOrdersCriteria();
		billOrdersCriteria.createCriteria().andHotelIdEqualTo(checkBill.getHotelid())
			.andEndTimeBetween(HmsDateUtils.getDateFromeDateString(startTime, HmsDateUtils.FORMAT_DATETIME),
					HmsDateUtils.getDateFromeDateString(endTime, HmsDateUtils.FORMAT_DATETIME));
		outMap.put("total", this.getBillOrdersMapper().countByExample(billOrdersCriteria));
		billOrdersCriteria.setLimitStart(page.getStartIndex());
		billOrdersCriteria.setLimitEnd(page.getPageSize());
		outMap.put("rows", this.getBillOrdersMapper().selectByExample(billOrdersCriteria));
		return outMap;
	}
	
	/*******************需求变更后的简化版********************/
	
	/**
	 * 获取概要信息
	 * @return 概要对象
	 * @throws SessionTimeOutException 
	 */
	public BillConfirmCheck simpleQueryBillByHotel(String yearAndMonth) throws SessionTimeOutException {
		EHotel thisHotel = SessionUtils.getThisHotel();
		BillConfirmCheckCriteria billConfirmCheckCriteria = new BillConfirmCheckCriteria();
		billConfirmCheckCriteria.createCriteria().andCheckstatusGreaterThan(0).andHotelidEqualTo(thisHotel.getId()).andBilltimeEqualTo(yearAndMonth);
		List<BillConfirmCheck> list = this.getBillConfirmCheckMapper().selectByExample(billConfirmCheckCriteria);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 获取审核详细(使用中)
	 * @return 审核详情
	 */
	public Map<String, Object> simpleQueryBillCheckInfo(long billId, Page page) {
		Map<String, Object> outMap = new HashMap<String, Object>();
		BillCheckInfoCriteria billCheckInfoCriteria = new BillCheckInfoCriteria();
		billCheckInfoCriteria.setOrderByClause("checktime desc");
		billCheckInfoCriteria.createCriteria().andBillcheckidEqualTo(billId);
		int total = page.getTotal();
		if (total == 0) {
			total = this.getBillCheckInfoMapper().countByExample(billCheckInfoCriteria);
		}
		outMap.put("total", total);
		billCheckInfoCriteria.setLimitStart(page.getStartIndex());
		billCheckInfoCriteria.setLimitEnd(page.getPageSize());
		outMap.put("rows", this.getBillCheckInfoMapper().selectByExample(billCheckInfoCriteria));
		return outMap;
	}
	
	/**
	 * 获取账期详细列表(使用中)
	 * @return 审核详细列表
	 * @throws Exception 
	 */
	public Map<String, Object> simpleQueryBillInfo(long billId, String method, Page page) throws Exception {
//		BillConfirmCheck checkBill = this.getBillConfirmCheckMapper().selectByPrimaryKey(billId);
//		if (null == checkBill || checkBill.getCheckstatus() == HmsSimpleBBillConfirmCheckEnum.Init.getValue()) {
//			throw new Exception("账单信息为空");
//		}
//		Map<String, Object> outMap = new HashMap<String, Object>();
//		int total = page.getTotal();
//		if (method.equals("valid")) {
//			String billTime = checkBill.getBilltime() + "01";
//			String startTime = formatDate(billTime, false) + " 00:00:00";
//			String endTime = formatDate(billTime, true) + " 23:59:59";
//			BillOrdersCriteria billOrdersCriteria = new BillOrdersCriteria();
//			billOrdersCriteria.createCriteria().andHotelIdEqualTo(checkBill.getHotelid())
//				.andBeginTimeBetween(HmsDateUtils.getDateFromeDateString(startTime, HmsDateUtils.FORMAT_DATETIME),
//						HmsDateUtils.getDateFromeDateString(endTime, HmsDateUtils.FORMAT_DATETIME))
//				.andInvalidreasonIsNull();
//			if (total == 0) {
//				total = this.getBillOrdersMapper().countByExample(billOrdersCriteria);
//			}
//			outMap.put("total", total);
//			billOrdersCriteria.setLimitStart(page.getStartIndex());
//			billOrdersCriteria.setLimitEnd(page.getPageSize());
//			outMap.put("rows", this.getBillOrdersMapper().selectByExample(billOrdersCriteria));
//		} else if (method.equals("invalid")) {
//			String billTime = checkBill.getBilltime() + "01";
//			String startTime = formatDate(billTime, false) + " 00:00:00";
//			String endTime = formatDate(billTime, true) + " 23:59:59";
//			BillOrdersCriteria billOrdersCriteria = new BillOrdersCriteria();
//			billOrdersCriteria.createCriteria().andHotelIdEqualTo(checkBill.getHotelid())
//				.andBeginTimeBetween(HmsDateUtils.getDateFromeDateString(startTime, HmsDateUtils.FORMAT_DATETIME),
//						HmsDateUtils.getDateFromeDateString(endTime, HmsDateUtils.FORMAT_DATETIME))
//				.andInvalidreasonIsNotNull();
//			if (total == 0) {
//				total = this.getBillOrdersMapper().countByExample(billOrdersCriteria);
//			}
//			outMap.put("total", total);
//			billOrdersCriteria.setLimitStart(page.getStartIndex());
//			billOrdersCriteria.setLimitEnd(page.getPageSize());
//			outMap.put("rows", this.getBillOrdersMapper().selectByExample(billOrdersCriteria));
//		} else if (method.equals("collect")) {
//			HotelBillSplitCriteria hotelBillSplitCriteria = new HotelBillSplitCriteria();
//			hotelBillSplitCriteria.createCriteria().andBillIdEqualTo(billId);
//			if (total == 0) {
//				total = this.getHotelBillSplitMapper().countByExample(hotelBillSplitCriteria);
//			}
//			outMap.put("total", total);
//			hotelBillSplitCriteria.setLimitStart(page.getStartIndex());
//			hotelBillSplitCriteria.setLimitEnd(page.getPageSize());
//			outMap.put("rows", this.getHotelBillSplitMapper().selectByExample(hotelBillSplitCriteria));
//		}
//		return outMap;
		ExtBillConfirmCheck checkBill = this.getExtBillConfirmCheckMapper().selectByPrimaryKey(billId);
		if (null == checkBill || checkBill.getCheckstatus() == HmsSimpleBBillConfirmCheckEnum.Init.getValue()) {
			throw new Exception("账单信息为空");
		}
		Map<String, Object> outMap = new HashMap<String, Object>();
		int total = page.getTotal();
		if (method.equals("valid")) {
			String billTime = checkBill.getBilltime() + "01";
			String startTime = formatDate(billTime, false) + " 00:00:00";
			String endTime = formatDate(billTime, true) + " 23:59:59";
			ExtBillOrdersCriteria billOrdersCriteria = new ExtBillOrdersCriteria();
			billOrdersCriteria.setOrderByClause("o.id");
			billOrdersCriteria.createCriteria().andHotelIdEqualTo(checkBill.getHotelid())
				.andBeginTimeBetween(HmsDateUtils.getDateFromeDateString(startTime, HmsDateUtils.FORMAT_DATETIME),
						HmsDateUtils.getDateFromeDateString(endTime, HmsDateUtils.FORMAT_DATETIME))
				.andPidEqualTo(billId);//.andInvalidreasonIsNull()
			if (total == 0) {
				total = this.getExtBillOrdersMapper().countByExample(billOrdersCriteria);
			}
			outMap.put("total", total);
			billOrdersCriteria.setLimitStart(page.getStartIndex());
			billOrdersCriteria.setLimitEnd(page.getPageSize());
			outMap.put("rows", this.getExtBillOrdersMapper().selectByExample(billOrdersCriteria));
		} else if (method.equals("invalid")) {
			String billTime = checkBill.getBilltime() + "01";
			String startTime = formatDate(billTime, false) + " 00:00:00";
			String endTime = formatDate(billTime, true) + " 23:59:59";
			BillOrdersCriteria billOrdersCriteria = new BillOrdersCriteria();
			billOrdersCriteria.createCriteria().andHotelIdEqualTo(checkBill.getHotelid())
				.andBeginTimeBetween(HmsDateUtils.getDateFromeDateString(startTime, HmsDateUtils.FORMAT_DATETIME),
						HmsDateUtils.getDateFromeDateString(endTime, HmsDateUtils.FORMAT_DATETIME))
				.andInvalidreasonIsNotNull();
			if (total == 0) {
				total = this.getBillOrdersMapper().countByExample(billOrdersCriteria);
			}
			outMap.put("total", total);
			billOrdersCriteria.setLimitStart(page.getStartIndex());
			billOrdersCriteria.setLimitEnd(page.getPageSize());
			outMap.put("rows", this.getBillOrdersMapper().selectByExample(billOrdersCriteria));
		} else if (method.equals("collect")) {
			HotelBillSplitCriteria hotelBillSplitCriteria = new HotelBillSplitCriteria();
			hotelBillSplitCriteria.createCriteria().andBillIdEqualTo(billId);
			if (total == 0) {
				total = this.getHotelBillSplitMapper().countByExample(hotelBillSplitCriteria);
			}
			outMap.put("total", total);
			hotelBillSplitCriteria.setLimitStart(page.getStartIndex());
			hotelBillSplitCriteria.setLimitEnd(page.getPageSize());
			outMap.put("rows", this.getHotelBillSplitMapper().selectByExample(hotelBillSplitCriteria));
		}
		return outMap;
	}
	
	/**
	 * hms 确定订单信息
	 * @return 订单信息
	 * @throws ParseException 异常
	 * @throws SessionTimeOutException 
	 */
	public OutModel simpleHmsConfirmBillAmount(long billId) throws ParseException, SessionTimeOutException {
		BillConfirmCheck checkBill = this.getBillConfirmCheckMapper().selectByPrimaryKey(billId);
		OutModel out = new OutModel(false);
		// 不是【待确认】
		if (checkBill.getCheckstatus() != HmsSimpleBBillConfirmCheckEnum.Confirming.getValue()) {
			out.setSuccess(false);
			out.setErrorMsg("该账单当前状态不是【待确认】");
			return out;
		}
		// 添加审核明细
		simpleSetHmsCheckInfo("已确认", checkBill, true);
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * 添加hms操作审核纪录
	 * @param checkText 审核内容
	 * @param checkBill 账单
	 * @param isConfirm 是否确认
	 * @throws SessionTimeOutException 
	 */
	private void simpleSetHmsCheckInfo(String checkText, BillConfirmCheck checkBill, boolean isConfirm) throws SessionTimeOutException {
		// 获取用户信息
		LoginUser loginser = SessionUtils.getSessionLoginUser();
		if (null != loginser) {
			BillCheckInfo billCheckInfo = new BillCheckInfo();
			User user = loginser.getUser();
			BillConfirmCheck billConfirmCheck = new BillConfirmCheck();
			// 重置审核状态
			if (isConfirm) {
				billConfirmCheck.setId(checkBill.getId());
				billConfirmCheck.setCheckstatus(HmsSimpleBBillConfirmCheckEnum.Confirmed.getValue());
				billConfirmCheck.setConfirmuserid(user.getId());
				billConfirmCheck.setConfirmusername(user.getLoginname());
				this.getBillConfirmCheckMapper().updateByPrimaryKeySelective(billConfirmCheck);
				
				billCheckInfo.setCheckstatus(HmsSimpleBBillConfirmCheckEnum.Confirmed.getValue());
				billCheckInfo.setCheckstatustext(HmsSimpleBBillConfirmCheckEnum.Confirmed.getText());
			} else {
				billConfirmCheck.setId(checkBill.getId());
				billConfirmCheck.setCheckstatus(HmsSimpleBBillConfirmCheckEnum.Disagreed.getValue());
				billConfirmCheck.setCheckuserid(user.getId());
				billConfirmCheck.setCheckusername(user.getLoginname());
				this.getBillConfirmCheckMapper().updateByPrimaryKeySelective(billConfirmCheck);
				
				billCheckInfo.setCheckstatus(HmsSimpleBBillConfirmCheckEnum.Disagreed.getValue());
				billCheckInfo.setCheckstatustext(HmsSimpleBBillConfirmCheckEnum.Disagreed.getText());
			}
			// 写入审核日志纪录
			billCheckInfo.setBillcheckid(checkBill.getId());
			billCheckInfo.setChecktime(new Date());
			billCheckInfo.setCheckuserid(user.getId());
			billCheckInfo.setCheckusername(user.getLoginname());
			billCheckInfo.setCheckmemo(checkText);
			this.getBillCheckInfoMapper().insert(billCheckInfo);
		}
	}
	/**
	 * 添加hms操作审核纪录
	 * @param checkText 审核内容
	 * @param checkBill 账单
	 * @param isConfirm 是否确认
	 * @throws SessionTimeOutException 
	 */
	private void simpleSetHmsCheckInfo(String checkText, ExtBillConfirmCheck checkBill, boolean isConfirm) throws SessionTimeOutException {
		// 获取用户信息
		LoginUser loginser = SessionUtils.getSessionLoginUser();
		if (null != loginser) {
			BillCheckInfo billCheckInfo = new BillCheckInfo();
			User user = loginser.getUser();
			ExtBillConfirmCheck billConfirmCheck = new ExtBillConfirmCheck();
			// 重置审核状态
			if (isConfirm) {
				billConfirmCheck.setId(checkBill.getId());
				billConfirmCheck.setCheckstatus(HmsSimpleBBillConfirmCheckEnum.Confirmed.getValue());
				billConfirmCheck.setConfirmuserid(user.getId());
				billConfirmCheck.setConfirmusername(user.getLoginname());
				this.getExtBillConfirmCheckMapper().updateByPrimaryKeySelective(billConfirmCheck);
				
				billCheckInfo.setCheckstatus(HmsSimpleBBillConfirmCheckEnum.Confirmed.getValue());
				billCheckInfo.setCheckstatustext(HmsSimpleBBillConfirmCheckEnum.Confirmed.getText());
			} else {
				billConfirmCheck.setId(checkBill.getId());
				billConfirmCheck.setCheckstatus(HmsSimpleBBillConfirmCheckEnum.Disagreed.getValue());
				billConfirmCheck.setCheckuserid(user.getId());
				billConfirmCheck.setCheckusername(user.getLoginname());
				this.getExtBillConfirmCheckMapper().updateByPrimaryKeySelective(billConfirmCheck);
				
				billCheckInfo.setCheckstatus(HmsSimpleBBillConfirmCheckEnum.Disagreed.getValue());
				billCheckInfo.setCheckstatustext(HmsSimpleBBillConfirmCheckEnum.Disagreed.getText());
			}
			// 写入审核日志纪录
			billCheckInfo.setBillcheckid(checkBill.getId());
			billCheckInfo.setChecktime(new Date());
			billCheckInfo.setCheckuserid(user.getId());
			billCheckInfo.setCheckusername(user.getLoginname());
			billCheckInfo.setCheckmemo(checkText);
			this.getBillCheckInfoMapper().insert(billCheckInfo);
		}
	}
	/**
	 * 酒店审核金额操作
	 * @return 审核状态
	 * @throws ParseException  异常
	 * @throws SessionTimeOutException 
	 */
	public OutModel simpleHmsCheckBillAmount(long billId, String checkText, String changeCost) throws ParseException, SessionTimeOutException {
		// 返回对象
		OutModel out = new OutModel(false);
		ExtBillConfirmCheck checkBill = this.getExtBillConfirmCheckMapper().selectByPrimaryKey(billId);
		// 不是【待确认】
		if (checkBill.getCheckstatus() != HmsSimpleBBillConfirmCheckEnum.Confirming.getValue()) {
			out.setErrorMsg("该账单当前状态不是【待确认】");
			return out;
		}
		// 是否为空
		if (HmsStringUtils.isBlank(checkText)) {
			out.setErrorMsg("该账单反馈内容不可为空");
			return out;
		}
		// 检查调整金额是否为空
		if (changeCost == null) {
			out.setErrorMsg("调整金额不可为空");
			return out;
		}
		// 添加确认金额
//		checkBill.setChangecost(NumberUtils.createBigDecimal(changeCost));
		checkBill.setObjectionChangeCost(NumberUtils.createBigDecimal(changeCost));
		this.getExtBillConfirmCheckMapper().updateByPrimaryKeySelective(checkBill);

//		checkBill.setChangecost(NumberUtils.createBigDecimal(changeCost));
//		添加hms操作审核纪录
		simpleSetHmsCheckInfo(checkText, checkBill, false);
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * 获取格式化时间
	 * @param formatDate 需要被格式化日期
	 * @param isLastDay 是否获取这个月最后一天日期，false获取的是这个月第一天
	 * @return 日期字符串
	 * @throws ParseException 异常
	 */
	private String formatDate(String formatDate, boolean isLastDay) throws ParseException {
		String dateString = HmsDateUtils.getFormatDateString(HmsDateUtils.getDateFromeDateString(formatDate, "yyyyMMdd"),
				HmsDateUtils.FORMAT_DATE);
		if (!isLastDay) {
			dateString = HmsDateUtils.getMonthFirstDay(dateString);
		} else {
			dateString = HmsDateUtils.getMonthLastDay(dateString);
		}
		return dateString;
	}
	
	/**
	 * 添加hms操作审核纪录
	 * @param checkText 审核内容
	 * @param checkBill 账单
	 * @param isConfirm 是否确认
	 * @throws SessionTimeOutException 
	 */
	private void setHmsCheckInfo(String checkText, BillConfirmCheck checkBill, boolean isConfirm) throws SessionTimeOutException {
		// 获取用户信息
		LoginUser loginser = SessionUtils.getSessionLoginUser();
		if (null != loginser) {
			BillCheckInfo hmsBBillCheckInfoModel = new BillCheckInfo();
			User user = loginser.getUser();
			// 重置审核状态
			if (isConfirm) {
				BillConfirmCheck bill = new BillConfirmCheck();
				bill.setConfirmstatus(HmsBBillConfirmEnum.Confirmed.getValue());
				bill.setId( checkBill.getId());
				bill.setConfirmuserid(user.getId());
				bill.setConfirmusername(user.getLoginname());
				this.getBillConfirmCheckMapper().updateByPrimaryKey(bill);
				
				hmsBBillCheckInfoModel.setCheckstatus(checkBill.getCheckstatus());
				hmsBBillCheckInfoModel.setCheckstatustext(getCheckStatusText(checkBill.getCheckstatus()));
			} else {
				BillConfirmCheck bill = new BillConfirmCheck();
				bill.setCheckstatus(HmsBBillConfirmCheckEnum.Checking.getValue());
				bill.setId(checkBill.getId());
				bill.setCheckuserid(user.getId());
				bill.setCheckusername(user.getLoginname());
				this.getBillConfirmCheckMapper().updateByPrimaryKey(bill);
				
				hmsBBillCheckInfoModel.setCheckstatus(HmsBBillConfirmCheckEnum.Checking.getValue());
				hmsBBillCheckInfoModel.setCheckstatustext(HmsBBillConfirmCheckEnum.Checking.getText());
			}
			// 写入审核日志纪录
			hmsBBillCheckInfoModel.setBillcheckid(checkBill.getId());
			hmsBBillCheckInfoModel.setChecktime(new Date());
			hmsBBillCheckInfoModel.setCheckuserid(user.getId());
			hmsBBillCheckInfoModel.setCheckusername(user.getLoginname());
			hmsBBillCheckInfoModel.setCheckmemo(checkText);
			this.getBillCheckInfoMapper().insert(hmsBBillCheckInfoModel);
		}
	}
	
	/**
	 * 获取状态文本内容
	 * @param status 审核状态码
	 * @return  审核状态码文本值
	 */
	private String getCheckStatusText(int status) {
		for (HmsBBillConfirmCheckEnum e: HmsBBillConfirmCheckEnum.values()) {
			if (e.getValue() == status) {
				return e.getText();
			}
		}
		return null;
	}
	
	/**
	 * 添加pms操作审核纪录
	 * @param billId 账单主键
	 * @param checkInfoId 审核详细
	 * @param checkText 审核内容
	 * @param isConfirm 是否确认操作
	 * @throws SessionTimeOutException 
	 */
	private void setPmsCheckInfo(long billId, long checkInfoId, String checkText, boolean isConfirm) throws SessionTimeOutException {
		// 获取用户信息
		MUser mUser = SessionUtils.getSessionPmsUser();
		if (null != mUser) {
			BillCheckInfo checkInfo = this.getBillCheckInfoMapper().selectByPrimaryKey(checkInfoId);
			if (null != checkInfo) {
				// 重置审核状态
				if (isConfirm) {
					BillConfirmCheck bill = new BillConfirmCheck();
					bill.setId(billId);
					bill.setCheckstatus(HmsBBillConfirmCheckEnum.Checked.getValue());
					this.getBillConfirmCheckMapper().updateByPrimaryKey(bill);
					checkInfo.setCheckstatus(HmsBBillConfirmCheckEnum.Checked.getValue());
					checkInfo.setCheckstatustext(HmsBBillConfirmCheckEnum.Checked.getText());
				} else {
					BillConfirmCheck bill = new BillConfirmCheck();
					bill.setId(billId);
					bill.setCheckstatus(HmsBBillConfirmCheckEnum.UnChecked.getValue());
					this.getBillConfirmCheckMapper().updateByPrimaryKey(bill);
					checkInfo.setCheckstatus(HmsBBillConfirmCheckEnum.UnChecked.getValue());
					checkInfo.setCheckstatustext(HmsBBillConfirmCheckEnum.UnChecked.getText());
				}
				// 写入审核日志纪录
				checkInfo.setBacktime(new Date());
				checkInfo.setBackuserid(mUser.getId());
				checkInfo.setBackusername(mUser.getLoginname());
				checkInfo.setBackmemo(checkText);
				this.getBillCheckInfoMapper().updateByPrimaryKeySelective(checkInfo);
			}
		}
	}
	
	/**
	 * 检测审核账单是否合法（账单时间为上月账单；当前时间为本月1号和5号之间）
	 * @param checkBill 账单对象
	 * @return 是否合法
	 * @throws ParseException 异常
	 */
	private boolean checkBillIsLegitimacy(BillConfirmCheck checkBill) throws ParseException {
		if (null != checkBill) {
			// 账单是否为上月
			if (checkBill.getBilltime().equals(HmsDateUtils.getPreviousMonth())) {
				long startTimeVal = HmsDateUtils.getThisMonthStateTimeVal();
				// 当月6号凌晨
				long endTimeVal = HmsDateUtils.getThisMonthOneDayTimeVal(6);
				long nowTimeVal = new Date().getTime();
				if (nowTimeVal >= startTimeVal && nowTimeVal < endTimeVal) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 确认金额是否合法
	 * @param billId 账单id
	 * @param confirmAmountStr 确定金额
	 * @return 是否合法
	 */
	private boolean confirmAmountIsLegitimacy(long billId, String confirmAmountStr) {
		if (null == confirmAmountStr) {
			return false;
		}
		if (!NumberUtils.isNumber(confirmAmountStr)) {
			return false;
		}
		BillConfirmCheck checkBill = this.getBillConfirmCheckMapper().selectByPrimaryKey(billId);
		if (null != checkBill) {
			double confirmAmount = NumberUtils.toDouble(confirmAmountStr);
			double amount = checkBill.getBillcost().doubleValue();
			double leftConfirmAmount = amount * (-0.1);
			double rightConfirmAmount = amount * (0.1);
			if (confirmAmount >= leftConfirmAmount && confirmAmount <= rightConfirmAmount) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取订单列表
	 * @param yearAndMonth
	 * @param hotelName
	 * @param checkStatus
	 * @param confirmStatus
	 * @param page
	 * @return
	 */
	private Map<String, Object> queryBill(BillConfirmCheckCriteria billConfirmCheckCriteria, Page page) {
		Map<String, Object> outMap = new HashMap<String, Object>();
		
		outMap.put("total", this.getBillConfirmCheckMapper().countByExample(billConfirmCheckCriteria));
		billConfirmCheckCriteria.setLimitStart(page.getStartIndex());
		billConfirmCheckCriteria.setLimitEnd(page.getPageSize());
		outMap.put("rows", this.getBillConfirmCheckMapper().selectByExample(billConfirmCheckCriteria));
		return outMap;
	}
	/**
	 * 获取订单列表
	 * @param yearAndMonth
	 * @param hotelName
	 * @param checkStatus
	 * @param confirmStatus
	 * @param page
	 * @return
	 */
	private Map<String, Object> queryExtBill(ExtBillConfirmCheckCriteria billConfirmCheckCriteria, Page page, Long hotelId) throws Exception{
		Map<String, Object> outMap = new HashMap<String, Object>();
		outMap.put("total", this.getExtBillConfirmCheckMapper().countByExample(billConfirmCheckCriteria).size());
		billConfirmCheckCriteria.setLimitStart(page.getStartIndex());
		billConfirmCheckCriteria.setLimitEnd(page.getPageSize());
		List<ExtBillConfirmCheck> list = this.getExtBillConfirmCheckMapper().selectByExample(billConfirmCheckCriteria);
		for(ExtBillConfirmCheck billConfirm : list){
			ExtBillOrders record = new ExtBillOrders();
			
			String billTime = billConfirm.getBilltime() + "01";
			String startTime = formatDate(billTime, false) + " 00:00:00";
			String endTime = formatDate(billTime, true) + " 23:59:59";
			// ExtBillOrdersCriteria billOrdersCriteria = new ExtBillOrdersCriteria();			
			record.setBeginTime(HmsDateUtils.getDateFromeDateString(startTime, HmsDateUtils.FORMAT_DATETIME));
			record.setEndTime(HmsDateUtils.getDateFromeDateString(endTime, HmsDateUtils.FORMAT_DATETIME));
			
			record.setHotelId(hotelId);
			record.setOrderType(1L);
			billConfirm.setPreNum(this.getExtBillOrdersMapper().selectOrderNumByType(record));
			record.setOrderType(2L);
			billConfirm.setToNum(this.getExtBillOrdersMapper().selectOrderNumByType(record));
		}
		outMap.put("rows", list);
		return outMap;
//		int limitstart = page.getStartIndex();
//		int limitend = page.getPageSize();
//		Map<String, Object> params = RequestUtils.getParameters();
//		StringBuffer sqlWhere = new StringBuffer("hotelid = ").append(thisHotel.getId());
//		// 存在账期
//		if (HmsStringUtils.isNotBlank(yearAndMonth)) {
//			sqlWhere.append(" and billtime = '").append(yearAndMonth).append("'");
//		}
//		// 存在审核状态查询
//		if (HmsStringUtils.isNotBlank(checkStatus)) {
//			sqlWhere.append(" and checkstatus = ").append(checkStatus);
//		} else {
//			sqlWhere.append(" and checkstatus > 0 and checkstatus < 8");
//		}
//		outMap.put("rows", hmsClearingBillMapper.findBillByWhere(sqlWhere.toString(), stateIndex, pageSize));
//		if (total == 0) {
//			total = hmsClearingBillMapper.findBillCountByWhere(sqlWhere.toString()).size();
//		}
//		outMap.put("total", total);
//		return outMap;
	}
	/**
	 * 获得是否冻结状态
	 * @param hotelid
	 * @return
	 * @throws Exception
	 */
	public String getIsFreeze(long hotelid) throws Exception{
		return this.getExtBillOrdersMapper().getIsFreeze(hotelid);
	}
	/**
	 * 统计账单金额，从月初第一天到跑批前一天
	 * @param begintime
	 * @param endtime
	 * @param hotelid
	 * @return
	 * @throws Exception
	 */
	public Float getBillCosts(Date begintime,Date endtime,long hotelid) throws Exception{
		return this.getExtBillOrdersMapper().getBillCosts(begintime,endtime,hotelid);
	}
	/**
	 * 在公共配置表sy_config中查找是否配置有skey字段值与酒店对应的城市ID相等的记录
	 * @param disid
	 * @return
	 */
	public BigDecimal getCityCodeValue(long disid){
		//用disid取市id
		Integer cityId = this.getHmsHotelMessageMapper().getCityId(disid);
		String cityCode = null;
		BigDecimal configValue = BigDecimal.ZERO;
		//城市id
		if(cityId!=null){
			//查询citycode
			cityCode = this.getHmsHotelMessageMapper().getCityCode(Long.valueOf(cityId.intValue()));
			if(cityCode!=null && !cityCode.equals("")){
				//查询configvalue
				configValue = this.getHmsHotelMessageMapper().getConfigValue(cityCode);	
			}
		}
		return configValue == null ? BigDecimal.ZERO : configValue;
	}
	
	public List<Map<Object,Object>> getValidHotels(){
		List<HmsTHotelModel> list = this.getHmsHotelMessageMapper().getValidHotel();
		final List<Map<Object,Object>> values = new ArrayList<Map<Object,Object>>();
		Map<Object,Object> map = null;
		
		if(list!=null && list.size()>0){
			for(HmsTHotelModel model:list){
				map = new HashMap<Object,Object>();
				map.put(model.getId(), model.getIsThreshold());
				values.add(map);
			}
		}
		return values;
	}
	public int[] synData(final String sql,final List<Param> list){
		JdbcTemplate jdbc = HmsJdbcTemplate.getJdbcTemplate();
//		String date = HmsDateUtils.getFormatDateString(new Date(), "");
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Param rule = list.get(i);
				ps.setInt(1, rule.getRulecode());
				ps.setString(2, rule.getIsthreshold());
				ps.setLong(3, rule.getHotelid());
			}
			public int getBatchSize() {
				return list.size();
			}
		};
		return jdbc.batchUpdate(sql,setter);
	}
	public boolean isFreeze() throws Exception{
		EHotel thisHotel = SessionUtils.getThisHotel();
		long hotelid = thisHotel.getId();
		int disid = thisHotel.getDisId();
		Date begintime = getBeginDate(hotelid);
		Date endtime = new Date();//HmsDateUtils.getThisMonthEndTimeVal()
		//查询酒店结账金额
		Float billCosts = getBillCosts(begintime,endtime,hotelid);
		//金额判断
		if(billCosts == null){
			logger.info("结账金额为空");
			return false;
		}else{
			//绝对值
			billCosts = Math.abs(billCosts);
			if(billCosts.compareTo(new Float(0.0))<=0){
				logger.info("结账金额无效");
				return false;
			}
		}
		
		int roomNum = thisHotel.getRoomNum();
		BigDecimal svalue = BigDecimal.ZERO;
		try{
			//获取配置
			svalue = getCityCodeValue(disid);
		}catch(Exception e){
			logger.error("查询disid:"+disid+"配置金额异常",e);
		}
		//阈值配置判断
		if(svalue.compareTo(BigDecimal.ZERO)<=0){
			logger.info("阈值配置为空或为0");
			return false;
		}
		//结账金额和阈值配置比较
		BigDecimal thresholdVaule = svalue.multiply(new BigDecimal(roomNum));
		BigDecimal billCostsABS = new BigDecimal(Math.abs(billCosts));
		logger.info("阈值金额："+thresholdVaule);
		logger.info("当前订单金额："+billCostsABS);
		//billCostsABS 大于等于阈值返回true
		if(billCostsABS.compareTo(thresholdVaule)>=0){
			logger.info("当前订单金额大于等于阈值金额");
			return true;
		}else{
			logger.info("当前订单金额小于阈值金额");
			return false;
		}
	}
	/**
	 * 获取当前酒店最后结账日期
	 * @param hotelId 酒店id
	 * @return Date
	 */
	public Date getBeginDate(long hotelId) throws Exception{
		//当前年月
		int yearAndMonth = Integer.valueOf(HmsDateUtils.getYearMonth().replace("-", ""));
		//读取数据库最后日期
		Date date = this.getHmsHotelMessageMapper().getEndTime(hotelId, yearAndMonth);
		//时间为空重新生成月初时间
		if(date == null){
			//为了对应b_bill_period的结构，取的都是上一个日期的最后时间
			date = new Date(HmsDateUtils.getPrevMonthStateTimeVal());
		}
		return date;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception 
	 */
	public OutModel preCalAccount() throws Exception{
		OutModel out = new OutModel(false);
		/*判断当前系统时间是否在0~6点之间，此 时间为账单处理时间，禁止进行提前结算申请*/
		if(isBetweenZoreAndSix()){
			out.setSuccess(false);
			out.setErrorCode(1);
			return out;
		}
		long hotelid = SessionUtils.getThisHotelId();
		Date date = new Date();
		String address = HmsFileUtils
				.getSysContentItem(ContentUtils.GEN_BILLCONFIRMCHECKS);
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("hotelid", hotelid + "");
		paramsMap.put("theMonthDay",HmsDateUtils.formatDate(date));
		JSONObject obj = HttpClientUtils.post(address, paramsMap);
		if (null == obj) {
			BillService.logger.error("调用提前结算接口异常");
			out.setErrorMsg("调用提前结算接口异常");
			return out;
		}else{
			if(!obj.getString("sucess").equals("true")) {
				BillService.logger.error("调用提前结算接口错误："
						+ obj.getString("errmsg") + "，错误代码："
						+ obj.getString("errcode"));
				out.setErrorMsg("调用提前结算接口错误：" + obj.getString("errmsg")
						+ "，错误代码：" + obj.getString("errcode"));
				return out;
			}else{
				out.setSuccess(true);
			}
		}
		//记录日志
//		HmsHOperateLogModel log = new HmsHOperateLogModel("", "preCalAccount", "用提前结算接口");
//		hmsHOperateLogService.addLog(log);
		
		return out;
	}
	/**
	 * 修改账单状态
	 * @param id
	 * @return
	 */
	public int updateCheckStatus(long id){
		return this.getExtBillConfirmCheckMapper().updateCheckStatus(id);
	}
	/**
	 * 根据酒店名称，获取酒店列表
	 * @param hotelName 酒店名称
	 * @return 酒店列表
	 */
	private List<THotel> getByHotelName(String hotelName) {
		THotelCriteria tHotelCriteria = new THotelCriteria();
		tHotelCriteria.createCriteria().andHotelNameLike(hotelName);
		return this.gettHotelMapper().selectByExample(tHotelCriteria);
	}
	
	private THotelMapper gettHotelMapper() {
		return tHotelMapper;
	}

	private BillConfirmCheckMapper getBillConfirmCheckMapper() {
		return billConfirmCheckMapper;
	}

	private BillCheckInfoMapper getBillCheckInfoMapper() {
		return billCheckInfoMapper;
	}

	private BillOrdersMapper getBillOrdersMapper() {
		return billOrdersMapper;
	}

	private HotelBillSplitMapper getHotelBillSplitMapper() {
		return hotelBillSplitMapper;
	}

	private ExtBillConfirmCheckMapper getExtBillConfirmCheckMapper() {
		return extBillConfirmCheckMapper;
	}

	private ExtBillOrdersMapper getExtBillOrdersMapper() {
		return extBillOrdersMapper;
	}

	public HmsHotelMessageMapper getHmsHotelMessageMapper() {
		return hmsHotelMessageMapper;
	}

	public void setHmsHotelMessageMapper(HmsHotelMessageMapper hmsHotelMessageMapper) {
		this.hmsHotelMessageMapper = hmsHotelMessageMapper;
	}

	private HotelRuleMapper getHotelRuleMapper() {
		return hotelRuleMapper;
	}

	private WeekclearingMapper getWeekclearingMapper() {
		return weekclearingMapper;
	}

	public void setHotelRuleMapper(HotelRuleMapper hotelRuleMapper) {
		this.hotelRuleMapper = hotelRuleMapper;
	}
	/**
	 * 判断当前时间是否账单处理时间内
	 * @return boolean
	 */
	public boolean isBetweenZoreAndSix() throws IOException{
		//获取当前时间
		Date date = new Date();
		long now = date.getTime();
		//获取当天日期
		String currentDay = HmsDateUtils.formatDate(date);
		//获取当天开始时间
		String startTime = HmsFileUtils.getSysContentItem(ContentUtils.BILL_START_TIME);
		Date startDate = HmsDateUtils.getDateFromString(currentDay+" "+startTime);
		long start = startDate.getTime();
		//获取当天结束时间
		String endTime = HmsFileUtils.getSysContentItem(ContentUtils.BILL_END_TIME);
		Date endDate = HmsDateUtils.getDateFromString(currentDay+" "+endTime);
		long end = endDate.getTime();
		//判断当前时间是否在其实和结束时间内
		if(start <= now && now <= end){
			return true;
		}
		return false;
	}
	
}
