package com.mk.hms.service;

import com.mk.hms.enums.BillFeedbackStatusEnum;
import com.mk.hms.enums.BillFeedbackTypeEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.BillSpecialMapper;
import com.mk.hms.mapper.FeedbackMapper;
import com.mk.hms.model.*;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.LoginUser;
import com.mk.hms.view.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class FeedbackService {

	@Autowired
	private FeedbackMapper feedbackMapper;

	@Autowired
	private BillOrderWeekService billOrderWeekService;

	@Autowired
	private BillSpecialMapper billSpecialMapper;

	public int save(BillFeedback billFeedback) {
		Integer type = billFeedback.getType();
		if (null == type) {
			return 0;
		}

		//hotel
		EHotel thisHotel = null;
		try {
			thisHotel = SessionUtils.getThisHotel();
		} catch (SessionTimeOutException e) {
			e.printStackTrace();
			return 0;
		}

		long hotelId = thisHotel.getId();
		String hotelName = thisHotel.getHotelName();

		//user
		String userName = "";
		try {
			LoginUser loginUser = SessionUtils.getSessionLoginUser();
			User user = loginUser.getUser();
			userName = user.getLoginname();
		} catch (SessionTimeOutException e) {
			e.printStackTrace();
			return 0;
		}

		//save
		billFeedback.setStatus(BillFeedbackStatusEnum.SUBMIT.getCode());
		billFeedback.setHotelId(hotelId);
		billFeedback.setHotelName(hotelName);
		billFeedback.setCreateTime(new Date());
		billFeedback.setCreateBy(userName);
		return this.feedbackMapper.save(billFeedback);
	}

	public List<BillFeedback> queryNotSettled(Long fromBill, Integer type) {
		EHotel thisHotel = null;
		try {
			thisHotel = SessionUtils.getThisHotel();
		} catch (SessionTimeOutException e) {
			e.printStackTrace();
			return new ArrayList<BillFeedback>();
		}

		long hotelId = thisHotel.getId();

		//
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("hotelId",hotelId);
		param.put("fromBill",fromBill);
		param.put("type",type);

//		String allStatus =  BillFeedbackStatusEnum.SUBMIT.getCode() + ","
//				+ BillFeedbackStatusEnum.DOING.getCode()  + ","
//				+ BillFeedbackStatusEnum.TO_SETTLE.getCode();
//		param.put("status",allStatus);

		//
		List<BillFeedback> list = this.feedbackMapper.query(param);
		for (BillFeedback feedback : list) {
			//statusName
			Integer dbStatus = feedback.getStatus();
			BillFeedbackStatusEnum statusEnum = BillFeedbackStatusEnum.getByCode(dbStatus);
			String statusName = statusEnum.getValue();

			feedback.setStatusName(statusName);

//			//billCost
//			Integer dbType = feedback.getType();
//			Long billId = feedback.getFromBill();
//			if (BillFeedbackTypeEnum.ALL_IN.getCode() == dbType.intValue()) {
//				//
//				BillOrderWeek bill = this.billOrderWeekService.queryById(billId);
//				if (null != bill) {
//					feedback.setBillCost(bill.getBillCost());
//					feedback.setBeginTime(bill.getBeginTime());
//					feedback.setEndTime(bill.getEndTime());
//				}
//
//			} else if (BillFeedbackTypeEnum.SPECIAL.getCode() == dbType.intValue()) {
//
//				BillSpecialExample example = new BillSpecialExample();
//				example.createCriteria().andIdEqualTo(billId);
//				List<BillSpecial> specialList = billSpecialMapper.selectByExample(example);
//				if (!specialList.isEmpty()) {
//					BillSpecial special = specialList.get(0);
//					feedback.setBillCost(special.getBillcost());
//					feedback.setBeginTime(special.getBegintime());
//					feedback.setEndTime(special.getEndtime());
//				}
//			}
		}
		return list;
	}

	public List<BillFeedback> query(Page page,Integer type,Integer status) {
		EHotel thisHotel = null;
		try {
			thisHotel = SessionUtils.getThisHotel();
		} catch (SessionTimeOutException e) {
			e.printStackTrace();
			return new ArrayList<BillFeedback>();
		}

		long hotelId = thisHotel.getId();

		//
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("hotelId",hotelId);
		param.put("type",type);
		param.put("limitStart", page.getStartIndex());
		param.put("limitSize", page.getPageSize());

		if (null == status) {
			//
		} else if (BillFeedbackStatusEnum.SUBMIT.getCode() == status.intValue()) {
				param.put("status",status);
		} else {
			String allStatus = BillFeedbackStatusEnum.DOING.getCode()  + ","
					+ BillFeedbackStatusEnum.TO_SETTLE.getCode()  + ","
					+ BillFeedbackStatusEnum.SETTLED.getCode();
			param.put("status",allStatus);
		}

		//
		List<BillFeedback> list = this.feedbackMapper.query(param);
		for (BillFeedback feedback : list) {
			//typeName
			Integer typeId = feedback.getType();
			if (BillFeedbackTypeEnum.SPECIAL.getCode() == typeId.intValue()) {
				feedback.setTypeName(BillFeedbackTypeEnum.SPECIAL.getValue());
			} else if(BillFeedbackTypeEnum.ALL_IN.getCode() == typeId.intValue()) {
				feedback.setTypeName(BillFeedbackTypeEnum.ALL_IN.getValue());
			}

			//statusName
			Integer dbStatus = feedback.getStatus();
			BillFeedbackStatusEnum statusEnum = BillFeedbackStatusEnum.getByCode(dbStatus);
			String statusName = statusEnum.getValue();

			feedback.setStatusName(statusName);

			//billCost
			Integer dbType = feedback.getType();
			Long billId = feedback.getFromBill();
			if (BillFeedbackTypeEnum.ALL_IN.getCode() == dbType.intValue()) {
				//
				BillOrderWeek bill = this.billOrderWeekService.queryById(billId);
				if (null != bill) {
					feedback.setBillCost(bill.getBillCost());
					feedback.setBeginTime(bill.getBeginTime());
					feedback.setEndTime(bill.getEndTime());
				}

			} else if (BillFeedbackTypeEnum.SPECIAL.getCode() == dbType.intValue()) {

				BillSpecialExample example = new BillSpecialExample();
				example.createCriteria().andIdEqualTo(billId);
				List<BillSpecial> specialList = billSpecialMapper.selectByExample(example);
				if (!specialList.isEmpty()) {
					BillSpecial special = specialList.get(0);
					feedback.setBillCost(special.getBillcost());
					feedback.setBeginTime(special.getBegintime());
					feedback.setEndTime(special.getEndtime());
				}

			}

		}
		return list;
	}

	public Integer count(Integer type,Integer status) {
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
		param.put("type",type);

		if (null == status) {
			//
		} else if (BillFeedbackStatusEnum.SUBMIT.getCode() == status.intValue()) {
			param.put("status",status);
		} else {
			String allStatus = BillFeedbackStatusEnum.DOING.getCode()  + ","
					+ BillFeedbackStatusEnum.TO_SETTLE.getCode()  + ","
					+ BillFeedbackStatusEnum.SETTLED.getCode();
			param.put("status",allStatus);
		}

		Integer count = this.feedbackMapper.count(param);
		return count;
	}
}
