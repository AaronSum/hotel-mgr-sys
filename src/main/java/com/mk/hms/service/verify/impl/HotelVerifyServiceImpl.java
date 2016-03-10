package com.mk.hms.service.verify.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mk.hms.kafka.KafkaProducer;
import com.mk.hms.service.verify.HotelVerifyService;
@Service
public class HotelVerifyServiceImpl implements HotelVerifyService{

	private static final Logger logger = LoggerFactory.getLogger(HotelVerifyServiceImpl.class);
	
	@Autowired
	private KafkaProducer hotelVerifyProducerImpl;
	@Override
	public void syncData(String value) {
		logger.debug("报文："+value);
		hotelVerifyProducerImpl.sendMessage(value);
	}

}
