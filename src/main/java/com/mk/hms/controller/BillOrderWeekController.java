package com.mk.hms.controller;

import com.mk.hms.mapper.BillOrderDetailMapper;
import com.mk.hms.model.BillOrderDetail;
import com.mk.hms.model.BillOrderWeek;
import com.mk.hms.service.BillOrderDetailService;
import com.mk.hms.service.BillOrderWeekService;
import com.mk.hms.service.BillService;
import com.mk.hms.view.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/billOrderWeek")
public class BillOrderWeekController {

	@Autowired
	private BillOrderWeekService billOrderWeekService;

	@Autowired
	private BillOrderDetailService billOrderDetailService;

	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(Page page,Integer status) throws Exception {
		List<BillOrderWeek> rows =  billOrderWeekService.query(page,status);
		Integer total = billOrderWeekService.count(status);

		Map<String, Object> outMap = new HashMap<String, Object>();
		outMap.put("rows", rows);
		outMap.put("total", total);

		return outMap;
	}



	@RequestMapping("/queryDetail")
	@ResponseBody
	public Map<String, Object> queryDetail(Page page,Long billOrderWeekId) throws Exception {
		List<BillOrderDetail> rows =  billOrderDetailService.queryByBillOrderWeekId(page,billOrderWeekId);
		Integer total = billOrderDetailService.countByBillOrderWeekId(billOrderWeekId);

		Map<String, Object> outMap = new HashMap<String, Object>();
		outMap.put("rows", rows);
		outMap.put("total", total);

		return outMap;
	}
}
