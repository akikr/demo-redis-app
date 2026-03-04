package io.akikr.app.utils;

import io.akikr.app.RedisTestContainer;
import io.akikr.app.config.AppRedisProperties;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataRedisTest()
@ImportTestcontainers(value = {RedisTestContainer.class})
class RedisCorrelationStoreTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private RedisCorrelationStore redisCorrelationStore;
    private String defaultCorrelationId;
    private String defaultPayload;

    private static final long defaultCorrelationStoreTtlValue = 3000;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        var appRedisProperties = new AppRedisProperties(1000, defaultCorrelationStoreTtlValue);
        redisCorrelationStore = new RedisCorrelationStore(appRedisProperties, stringRedisTemplate);
        defaultCorrelationId = UUID.randomUUID().toString();
        defaultPayload = new ObjectMapper().writeValueAsString(Map.of("data", "test data"));
    }

    @Test
    @DisplayName("Push 1 response for the given key successfully")
    void pushResponse_successfully() {
        // Arrange & Act
        redisCorrelationStore.pushResponse(defaultCorrelationId, defaultPayload);

        var response = stringRedisTemplate.opsForList().leftPop(defaultCorrelationId);

        //Assertions
        assertThat(response).isNotNull();
        assertThat(response).isNotEmpty();
        assertThat(response).isEqualTo(defaultPayload);
    }

    @Test
    @DisplayName("Wait for response for the given key:1001 is successfully")
    void waitForResponse_isSuccessfully() {
        // Arrange
        stringRedisTemplate.opsForList().rightPush(defaultCorrelationId, defaultPayload);

        // Act
        var response = redisCorrelationStore.waitForResponse(defaultCorrelationId);

        //Assertions
        assertThat(response).isNotNull();
        assertThat(response).isNotEmpty();
        assertThat(response).isEqualTo(defaultPayload);
    }
}
