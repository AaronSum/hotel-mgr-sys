package com.mk.hms.controller;

import com.alibaba.fastjson.JSONObject;
import com.mk.hms.enums.HmsSimpleBBillConfirmCheckEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.*;
import com.mk.hms.service.BillService;
import com.mk.hms.service.BillSpecialService;
import com.mk.hms.service.FeedbackService;
import com.mk.hms.service.HmsHotelMessageService;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@RequestMapping("/create")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> create(BillFeedback billFeedback) {

		Long fromBill = billFeedback.getFromBill();
		Integer type = billFeedback.getType();
		//
		List<BillFeedback> notSettledList =  this.feedbackService.queryNotSettled(fromBill, type);
		if (!notSettledList.isEmpty()) {
			Map<String, Object> outMap = new HashMap<String, Object>();
			outMap.put("success", false);
			outMap.put("message", "申诉处理中");
			return new ResponseEntity<Map<String, Object>>(outMap, HttpStatus.OK);
		}


		int result = this.feedbackService.save(billFeedback);

		Map<String, Object> outMap = new HashMap<String, Object>();
		outMap.put("success", true);
		outMap.put("result", result);

		return new ResponseEntity<Map<String, Object>>(outMap, HttpStatus.OK);
	}

	@RequestMapping("/query")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> query(Page page, Integer type,Integer status) {

		List<BillFeedback> rows = this.feedbackService.query(page,type,status);
		Integer total = this.feedbackService.count(type,status);
		Map<String, Object> outMap = new HashMap<String, Object>();
		outMap.put("total", total);
		outMap.put("rows", rows);

		return new ResponseEntity<Map<String, Object>>(outMap, HttpStatus.OK);
	}
}
