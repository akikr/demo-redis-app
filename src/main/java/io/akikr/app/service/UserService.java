package io.akikr.app.service;

import io.akikr.app.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
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

    public ResponseEntity<User> searchUser(String userId) {
        log.info("Invoked UserService#searchUsers method with userId: {}", userId);
        var correlationId = "on_search:" + userId;
        User user = fakeDataService.searchUser(correlationId);
        if(user == null){
            log.info("User not found for userId: {}", userId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<Void> onSearchUser(User user) {
        log.info("Invoked UserService#onSearchUser method with user: {}", user);
        if(user == null){
            log.info("User data cannot be NULL");
            return ResponseEntity.badRequest().build();
        }
        var correlationId = "on_search:" + user.id();
        fakeDataService.onSearchUser(correlationId, user);
        return ResponseEntity.created(URI.create("/on_search:" + user.id())).build();
    }
}
