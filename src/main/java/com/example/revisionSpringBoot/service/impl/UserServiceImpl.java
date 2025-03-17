package com.example.revisionSpringBoot.service.impl;

import com.example.revisionSpringBoot.dto.UserDto;
import com.example.revisionSpringBoot.entity.User;
import com.example.revisionSpringBoot.mapper.UserMapper;
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

    @Override
    public UserDto createUser(UserDto userDto) {
        System.out.println("in user service implementation : User ID before saving: " + userDto.getId());
        User user = UserMapper.mapToUserEntity(userDto);

        User saveUser = userRepository.save(user);

        UserDto saveUserDto = UserMapper.mapToUserDto(saveUser);
        return saveUserDto;
    }

    @Override
    public UserDto getUserById(int userId) {
        log.info("in user service implementation : received user id : -----> {}", userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUserById(int userId, UserDto user) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        log.info("in user service implementation : user id --> {}, name --> {} {}, email --> {}", existingUser.getId(), existingUser.getFirstName(), existingUser.getLastName(), existingUser.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        User updateUser = userRepository.save(existingUser);
        log.info("in user service implementation : user id --> {}, name --> {} {}, email --> {}", updateUser.getId(), updateUser.getFirstName(), updateUser.getLastName(), updateUser.getEmail());
        return UserMapper.mapToUserDto(updateUser);
    }

    public void deleteUserById(int userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public Map<String, Object> updateUserByEmail(String email, String firstName, String lastName) {
        User existingUser = userRepository.findByEmail(email);

        if (existingUser == null) {
            throw new RuntimeException("User not found");
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
