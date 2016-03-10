package com.mk.framework;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.StringUtils;

@Configuration
@PropertySource("classpath:redis.properties")
public class RedisConfiguration {

	@Value("${spring.redis.sentinel.master}")
	private String sentinelMasterName = null;

	@Value("${spring.redis.sentinel.nodes}")
	private String hostAndPorts = null;

	@Bean
	public JedisConnectionFactory connectionFactory() {
		Set<String> sentinelHostAndPorts = StringUtils.commaDelimitedListToSet(this.getHostAndPorts());
		RedisSentinelConfiguration sc = new RedisSentinelConfiguration(this.getSentinelMasterName(), sentinelHostAndPorts);

		return new JedisConnectionFactory(sc);
	}

	private String getSentinelMasterName() {
		return this.sentinelMasterName;
	}

	private String getHostAndPorts() {
		return this.hostAndPorts;
	}

}
