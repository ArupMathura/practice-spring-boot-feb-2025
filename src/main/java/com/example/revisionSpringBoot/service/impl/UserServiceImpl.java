package com.example.revisionSpringBoot.service.impl;

import com.example.revisionSpringBoot.entity.User;
import com.example.revisionSpringBoot.repository.UserRepository;
import com.example.revisionSpringBoot.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        System.out.println("User ID before saving: " + user.getId());
        return userRepository.save(user);
    }

    @Override
    public User getUserById(int userId) {
        log.info("received user id : -----> {}", userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
    }


}
