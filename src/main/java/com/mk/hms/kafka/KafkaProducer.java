package com.mk.hms.kafka;

public interface KafkaProducer {
	public boolean sendMessage(String value);
}
