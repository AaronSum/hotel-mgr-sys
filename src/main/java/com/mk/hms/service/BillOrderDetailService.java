package com.mk.hms.service;

import com.mk.hms.enums.BillOrderWeekCheckStatusEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.BillOrderDetailMapper;
import com.mk.hms.mapper.BillOrderWeekMapper;
import com.mk.hms.mapper.OtaOrderMapper;
import com.mk.hms.model.BillOrderDetail;
import com.mk.hms.model.BillOrderWeek;
import com.mk.hms.model.EHotel;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class BillOrderDetailService {

	@Autowired
	private BillOrderDetailMapper billOrderDetailMapper;
	@Autowired
	private OtaOrderMapper otaOrderMapper;

	public List<BillOrderDetail> queryByBillOrderWeekId(Page page, Long billOrderWeekId) {

		//
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("limitStart", page.getStartIndex());
		param.put("limitSize", page.getPageSize());
		param.put("billOrderWeekId", billOrderWeekId);

		List<BillOrderDetail> list = billOrderDetailMapper.queryByBillOrderWeekId(param);

		for (BillOrderDetail detail : list) {
			//checkoutTime
			Date checkoutTime = otaOrderMapper.queryCheckoutTime(detail.getOrderId());
			detail.setCheckoutTime(checkoutTime);
		}

		return list;
	}

	public Integer countByBillOrderWeekId(Long billOrderWeekId) {

		return billOrderDetailMapper.countByBillOrderWeekId(billOrderWeekId);
	}
}
