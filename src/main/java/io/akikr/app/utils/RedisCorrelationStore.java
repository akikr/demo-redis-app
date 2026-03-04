package io.akikr.app.utils;

import io.akikr.app.config.AppRedisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisCorrelationStore {

    private static final Logger log = LoggerFactory.getLogger(RedisCorrelationStore.class);

    private final AppRedisProperties appRedisProperties;
    private final StringRedisTemplate stringRedisTemplate;

    public RedisCorrelationStore(AppRedisProperties appRedisProperties, StringRedisTemplate stringRedisTemplate) {
        this.appRedisProperties = appRedisProperties;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void pushResponse(String correlationId, String jsonPayload){
        log.info("Pushing data for correlationId:[{}] with jsonPayload:[{}]", correlationId, jsonPayload);
        stringRedisTemplate.opsForList().rightPush(correlationId, jsonPayload);
        stringRedisTemplate.expire(correlationId, Duration.ofMillis(appRedisProperties.correlationStoreTtlValue()));
    }

    public String waitForResponse(String correlationId){
        log.info("Waiting for data for correlationId:[{}]", correlationId);
        return stringRedisTemplate.opsForList().leftPop(correlationId, Duration.ofMillis(appRedisProperties.correlationStoreTtlValue()));
    }
}
