package com.example.revisionSpringBoot.controller;

import com.example.revisionSpringBoot.entity.User;
import com.example.revisionSpringBoot.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("api/users")
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("received user : -----> {}", user);
        User savedUser = userService.createUser(user);
        log.info("user id = {} name = {} {} email = {}", user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
