package com.mk.hms.service;

import com.mk.hms.enums.BillFeedbackTypeEnum;
import com.mk.hms.enums.BillOrderWeekCheckStatusEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.BillOrderWeekMapper;
import com.mk.hms.mapper.FeedbackMapper;
import com.mk.hms.model.BillFeedback;
import com.mk.hms.model.BillOrderWeek;
import com.mk.hms.model.EHotel;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BillOrderWeekService {

	@Autowired
	private BillOrderWeekMapper billOrderWeekMapper;

	@Autowired
	private FeedbackService feedbackService;

	public BillOrderWeek queryById(Long id) {
		return this.billOrderWeekMapper.queryById(id);
	}

	public List<BillOrderWeek> query(Page page, Integer paramStatus){
		EHotel thisHotel = null;
		try {
			thisHotel = SessionUtils.getThisHotel();
		} catch (SessionTimeOutException e) {
			e.printStackTrace();
			return new ArrayList<BillOrderWeek>();
		}

		long hotelId = thisHotel.getId();

		//
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("hotelId",hotelId);
		param.put("limitStart", page.getStartIndex());
		param.put("limitSize", page.getPageSize());
		param.put("status",paramStatus);

		List<BillOrderWeek> billOrderWeekList =  this.billOrderWeekMapper.query(param);
		for (BillOrderWeek bill : billOrderWeekList) {

			//账单状态
			Integer status = bill.getCheckStatus();

			//结算状态
			if (status == BillOrderWeekCheckStatusEnum.SETTLED.getCode()) {
				bill.setCheckStatusName(BillOrderWeekCheckStatusEnum.SETTLED.getValue());
				bill.setSettleStatusName(BillOrderWeekCheckStatusEnum.SETTLED.getValue());
			} else {
				bill.setCheckStatusName(BillOrderWeekCheckStatusEnum.CONFIRM.getValue());
				bill.setSettleStatusName(BillOrderWeekCheckStatusEnum.CONFIRM.getValue());
			}

			//切客补贴金额
			BigDecimal prepaymentDiscount = bill.getPrepaymentDiscount();
			BigDecimal toPayDiscount = bill.getToPayDiscount();
			BigDecimal qieKeMoney = BigDecimal.ZERO;

			if(null != prepaymentDiscount) {
				qieKeMoney = qieKeMoney.add(prepaymentDiscount);
			}
			if (null != toPayDiscount) {
				qieKeMoney = qieKeMoney.add(toPayDiscount);
			}
			bill.setQieKeMoney(qieKeMoney);

			//申诉状态
			List<BillFeedback> notSettledList =
					this.feedbackService.queryNotSettled(bill.getId(), BillFeedbackTypeEnum.ALL_IN.getCode());

			if (notSettledList.isEmpty()) {
				bill.setFeedbackIng(false);
			} else {
				bill.setFeedbackIng(true);
			}
		}

		return billOrderWeekList;
	}

	public Integer count(Integer paramStatus){
		EHotel thisHotel = null;
		try {
			thisHotel = SessionUtils.getThisHotel();
		} catch (SessionTimeOutException e) {
			e.printStackTrace();
			return 0;
		}
		long hotelId = thisHotel.getId();

		//
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("hotelId",hotelId);
		param.put("status", paramStatus);

		Integer count = this.billOrderWeekMapper.count(param);
		return count;
	}
}
