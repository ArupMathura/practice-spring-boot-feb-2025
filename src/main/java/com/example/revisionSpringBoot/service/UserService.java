package com.example.revisionSpringBoot.service;

import com.example.revisionSpringBoot.dto.UserDto;
import com.example.revisionSpringBoot.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserDto createUser (UserDto userDto);

    UserDto getUserById(int userId);

    List<UserDto> getAllUsers();

    UserDto updateUserById(int userId, UserDto userDto);

    void deleteUserById(int userId);

    Map<String, Object> updateUserByEmail(String email, String firstName, String lastName);
}
