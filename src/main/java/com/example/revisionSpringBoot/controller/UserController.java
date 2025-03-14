package com.example.revisionSpringBoot.controller;

import com.example.revisionSpringBoot.entity.User;
import com.example.revisionSpringBoot.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("api/users")
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("in user controller : received user : -----> {}", user);
        User savedUser = userService.createUser(user);
        log.info("in user controller : user id = {} name = {} {} email = {}", user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int userId) {
        User getUser = userService.getUserById(userId);
        log.info("in user controller : user id --> {}, name --> {} {}, email --> {}", getUser.getId(), getUser.getFirstName(), getUser.getLastName(), getUser.getEmail());
        return new ResponseEntity<>(getUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        log.info("in user controller : All Users: {}", userList);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUserById(@PathVariable("id") int userId, @RequestBody User user) {
        User updateUser = userService.updateUserById(userId, user);
        log.info("in user controller : user id --> {}, name --> {} {}, email --> {}", updateUser.getId(), updateUser.getFirstName(), updateUser.getLastName(), updateUser.getEmail());
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") int userId) {
        log.info("in user controller : received user id : -----> {}", userId);
        userService.deleteUserById(userId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    // update firstName and lastName by email
    // http://localhost:8080/api/users/update?email=abcd@example.com
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateUserByEmail(
            @RequestParam String email,
            @RequestBody Map<String, String> userDetails) {

        String firstName = userDetails.get("firstName");
        String lastName = userDetails.get("lastName");

        Map<String, Object> response = userService.updateUserByEmail(email, firstName, lastName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
