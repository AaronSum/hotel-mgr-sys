package com.mk.hms.utils;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mk.hms.service.BillService;

@Component
public class SynThesholdByJob {
	
	
	private static BillService service;
	
	public static void copy(){
//		try {
////			getService().copyIsThreshold();
//		} catch (ParseException e) {
//		}
	}

	@Autowired
	public void setService(BillService service) {
		SynThesholdByJob.service = service;
	}

	private static BillService getService() {
		return service;
	}
	
}
