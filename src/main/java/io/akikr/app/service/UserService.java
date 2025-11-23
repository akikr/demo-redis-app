package io.akikr.app.service;

import io.akikr.app.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final FakeDataService fakeDataService;

    @Autowired
    public UserService(FakeDataService fakeDataService) {
        this.fakeDataService = fakeDataService;
    }

    @Cacheable(value = "usersCache", key = "#count")
    public List<User> getUsers(int count) {
        log.info("Invoked UserService#getUsers method with count: {}", count);
        return fakeDataService.getUsers(count);
    }
}
