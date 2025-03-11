package com.example.revisionSpringBoot.controller;

import com.example.revisionSpringBoot.entity.User;
import com.example.revisionSpringBoot.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int userId) {
        User getUser = userService.getUserById(userId);
        log.info("user id --> {}, name --> {} {}, email --> {}", getUser.getId(), getUser.getFirstName(), getUser.getLastName(), getUser.getEmail());
        return new ResponseEntity<>(getUser, HttpStatus.OK);
    }
}
