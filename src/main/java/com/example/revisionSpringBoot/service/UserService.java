package com.example.revisionSpringBoot.service;

import com.example.revisionSpringBoot.dto.UserDto;
import com.example.revisionSpringBoot.dto.UserRequest;
import com.example.revisionSpringBoot.dto.UserResponse;
import com.example.revisionSpringBoot.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserResponse createUser (UserRequest userRequest);

    UserResponse getUserById(int userId);

    List<UserResponse> getAllUsers();

    UserResponse updateUserById(int userId, UserRequest userDto);

    void deleteUserById(int userId);

    Map<String, Object> updateUserByEmail(String email, String firstName, String lastName);
}
