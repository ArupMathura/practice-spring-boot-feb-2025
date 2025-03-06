package com.example.revisionSpringBoot.service.impl;

import com.example.revisionSpringBoot.entity.User;
import com.example.revisionSpringBoot.repository.UserRepository;
import com.example.revisionSpringBoot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        System.out.println("User ID before saving: " + user.getId());
        return userRepository.save(user);
    }
}
