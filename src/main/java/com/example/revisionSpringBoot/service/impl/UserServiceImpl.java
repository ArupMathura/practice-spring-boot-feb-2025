package com.example.revisionSpringBoot.service.impl;


import com.example.revisionSpringBoot.dto.UserRequest;
import com.example.revisionSpringBoot.dto.UserResponse;
import com.example.revisionSpringBoot.entity.User;
import com.example.revisionSpringBoot.exception.ResourceNotFoundException;
import com.example.revisionSpringBoot.mapper.AutoUserMapper;

import com.example.revisionSpringBoot.repository.UserRepository;
import com.example.revisionSpringBoot.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private AutoUserMapper autoUserMapper;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
//        System.out.println("in user service implementation : User ID before saving: " + userRequest.getId());
//        User user = UserMapper.mapToUserEntity(userDto);
        User user = autoUserMapper.mapToUserEntity(userRequest);

        User saveUser = userRepository.save(user);

//        UserDto saveUserDto = UserMapper.mapToUserDto(saveUser);
        UserResponse saveUserResponse = autoUserMapper.mapToUserResponse(saveUser);
        return saveUserResponse;
    }

    @Override
    public UserResponse getUserById(int userId) {
        log.info("in user service implementation : received user id : -----> {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", (long) userId));
//        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
//        return UserMapper.mapToUserDto(user);
        return autoUserMapper.mapToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
//        return users.stream()
//                .map(UserMapper::mapToUserDto)
//                .collect(Collectors.toList());

        return users.stream()
                .map(autoUserMapper::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUserById(int userId, UserRequest user) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", (long) userId));
        log.info("in user service implementation : user id --> {}, name --> {} {}, email --> {}", existingUser.getId(), existingUser.getFirstName(), existingUser.getLastName(), existingUser.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        User updateUser = userRepository.save(existingUser);
        log.info("in user service implementation : user id --> {}, name --> {} {}, email --> {}", updateUser.getId(), updateUser.getFirstName(), updateUser.getLastName(), updateUser.getEmail());
//        return UserMapper.mapToUserDto(updateUser);
        return autoUserMapper.mapToUserResponse(updateUser);
    }

    public void deleteUserById(int userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public Map<String, Object> updateUserByEmail(String email, String firstName, String lastName) {
        User existingUser = userRepository.findByEmail(email);

        if (existingUser == null) {
            throw new ResourceNotFoundException("User", "email", email);
        }

        // update user's firstName and lastName
        userRepository.updateUserByEmail(email, firstName, lastName);

        // prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("id", existingUser.getId());
        response.put("msg", "firstname - \"" + firstName + "\" and lastname - \"" + lastName + "\" updated");

        return response;
    }


}
