package io.akikr.app.controller;

import io.akikr.app.model.User;
import io.akikr.app.service.UserService;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers(@RequestParam(defaultValue = "5") int count) {
        log.info("Invoked UserController#getUsers method with count: {}", count);
        return userService.getUsers(count);
    }

    @GetMapping(path = "/user/search/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> searchUser(@PathVariable(name = "id") String userId) {
        log.info("Invoked UserController#searchUser method with userId: {}", userId);
        return userService.searchUser(userId);
    }

    @PostMapping(path = "/user/on_search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> onSearchUser(@RequestBody User user) {
        log.info("Invoked UserController#onSearchUser method with user: {}", user);
        return userService.onSearchUser(user);
    }
}
