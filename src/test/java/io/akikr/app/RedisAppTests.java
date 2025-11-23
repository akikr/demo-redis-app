package io.akikr.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ImportTestcontainers(value = {RedisTestContainer.class})
@PropertySource(value = "classpath:application-test.properties")
class RedisAppTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        // Verify Spring application context is not null
        assertThat(applicationContext).isNotNull();

        // Verify the main application class is loaded
        assertThat(applicationContext.getBean("redisApp")).isInstanceOf(RedisApp.class);

        // Verify Redis container is running
        assertThat(RedisTestContainer.REDIS.isRunning()).isTrue();
    }
}
