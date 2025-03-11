package com.example.revisionSpringBoot.service;

import com.example.revisionSpringBoot.entity.User;

public interface UserService {
    User createUser (User user);

    User getUserById(int userId);
}
