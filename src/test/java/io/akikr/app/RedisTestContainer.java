package io.akikr.app;

import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.LoggerFactory;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class RedisTestContainer {


    @Container
    @ServiceConnection
    public static final RedisContainer REDIS = new RedisContainer(DockerImageName.parse("redis:7.4.2"))
            .withReuse(true);

    static {
        REDIS.start();
        System.out.println("REDIS container started");
        REDIS.followOutput(new Slf4jLogConsumer(LoggerFactory.getLogger(RedisTestContainer.class)));
        Runtime.getRuntime().addShutdownHook(new Thread(REDIS::stop));
    }

    @BeforeAll
    static void setUpKafka() {
        if (REDIS.isRunning())
            System.out.println("REDIS container running !!");
    }
}
