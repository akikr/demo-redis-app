package io.akikr.app.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisCorrelationStore {

    private static final Logger log = LoggerFactory.getLogger(RedisCorrelationStore.class);

    @Value("${app.redis-cache.correlation-store.ttl:10}")
    private long correlationStoreTtl;

    private final StringRedisTemplate stringRedisTemplate;

    public RedisCorrelationStore(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void pushResponse(String correlationId, String jsonPayload){
        log.info("Pushing data for correlationId:[{}] with jsonPayload:[{}]", correlationId, jsonPayload);
        stringRedisTemplate.opsForList().rightPush(correlationId, jsonPayload);
        stringRedisTemplate.expire(correlationId, Duration.ofSeconds(correlationStoreTtl));
    }

    public String waitForResponse(String correlationId){
        log.info("Waiting for data for correlationId:[{}]", correlationId);
        return stringRedisTemplate.opsForList().leftPop(correlationId, Duration.ofSeconds(correlationStoreTtl));
    }
}
