package com.example.revisionSpringBoot.service;

import com.example.revisionSpringBoot.entity.User;

import java.util.List;

public interface UserService {
    User createUser (User user);

    User getUserById(int userId);

    List<User> getAllUsers();

    User updateUserById(int userId, User user);

    void deleteUserById(int userId);
}
