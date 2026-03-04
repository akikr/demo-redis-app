package io.akikr.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.redis-cache")
public record AppRedisProperties(
        long defaultTtlValue,
        long correlationStoreTtlValue) {
}
