package com.mk.hms.kafka.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mk.hms.kafka.KafkaProducer;
import com.mk.hms.mapper.KafkaLogMapper;
import com.mk.hms.model.KafkaLog;
import com.mk.kafka.client.stereotype.MkMessageService;
import com.mk.kafka.client.stereotype.MkTopicProducer;

@MkMessageService
public class HotelVerifyProducerImpl implements KafkaProducer{

	private static final Logger logger = LoggerFactory.getLogger(HotelVerifyProducerImpl.class);
	
	@Autowired
	private KafkaLogMapper kafkaLogMapper;
	
	@Override
	@MkTopicProducer(topic = "hotelverify")
	public boolean sendMessage(String value) {
		logger.info("酒店审核申请发送成功");
		KafkaLog kafkaLog = new KafkaLog();
		kafkaLog.setJsondata(value);
		kafkaLog.setOrther1("HMSProducer");
		kafkaLogMapper.insertLog(kafkaLog);
		return true;
	}
}
