package io.akikr.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.akikr.app.model.User;
import io.akikr.app.utils.RedisCorrelationStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class FakeDataService {

    private static final Logger log = LoggerFactory.getLogger(FakeDataService.class);

    private final RedisCorrelationStore redisCorrelationStore;
    private final ObjectMapper objectMapper;

    public FakeDataService(RedisCorrelationStore redisCorrelationStore, ObjectMapper objectMapper) {
        this.redisCorrelationStore = redisCorrelationStore;
        this.objectMapper = objectMapper;
    }

    public List<User> getUsers(int count) {
        log.info("Fetching {} users from fake data service", count);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            log.error("Interrupted while waiting for users to become available, due to {}", e.getMessage(), e);
        }
        return IntStream.range(0, count)
                .mapToObj(i -> new User(i, "user" + i, "user" + i + "@example.com"))
                .collect(Collectors.toList());
    }

    public User searchUser(String correlationId) {
        log.info("Fetching user from fake data service fro correlationId:[{}]", correlationId);
        try {
            String userData = redisCorrelationStore.waitForResponse(correlationId);
            if(userData != null) {
                User user = objectMapper.readValue(userData, User.class);
                log.debug("Found user:[{}]", user);
                return user;
            }
        } catch (Exception e) {
            log.error("Error while fetching user from fake data service, due to: {}", e.getMessage(), e);
        }
        return null;
    }

    public void onSearchUser(String correlationId, User user) {
        log.info("Publishing user data for correlationId:[{}]", correlationId);
        try {
            String userData = objectMapper.writeValueAsString(user);
            log.debug("Publishing user:[{}] data for correlationId:[{}]", userData, correlationId);
            redisCorrelationStore.pushResponse(correlationId, userData);
        } catch (Exception e) {
            log.error("Error while publishing user data for correlationId:[{}], due to: {}", correlationId, e.getMessage(), e);
        }
    }
}
