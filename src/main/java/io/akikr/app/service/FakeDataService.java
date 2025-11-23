package io.akikr.app.service;

import io.akikr.app.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class FakeDataService {

    private static final Logger log = LoggerFactory.getLogger(FakeDataService.class);

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
}
