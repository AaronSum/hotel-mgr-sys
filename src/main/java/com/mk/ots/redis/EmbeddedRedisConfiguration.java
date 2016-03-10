package com.mk.ots.redis;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class EmbeddedRedisConfiguration {

}
