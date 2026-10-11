package com.example.revisionSpringBoot.service.impl;


import com.example.revisionSpringBoot.client.AddressClient;
import com.example.revisionSpringBoot.client.AddressClientImpl;
import com.example.revisionSpringBoot.client.OrderClient;
import com.example.revisionSpringBoot.dto.*;
import com.example.revisionSpringBoot.entity.User;
import com.example.revisionSpringBoot.exception.DownstreamServiceException;
import com.example.revisionSpringBoot.exception.DownstreamServiceTimeoutException;
import com.example.revisionSpringBoot.exception.ResourceNotFoundException;
import com.example.revisionSpringBoot.exception.UserNotFoundException;
import com.example.revisionSpringBoot.mapper.AutoUserMapper;

import com.example.revisionSpringBoot.repository.UserRepository;
import com.example.revisionSpringBoot.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final OrderClient orderClient;
    private final UserRepository userRepository;
    private final AutoUserMapper autoUserMapper;
    private final AddressClient addressClient;

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

    /*@Override
    public UserResponse getUserById(int userId) {
        log.info("in user service implementation : received user id : -----> {}", userId);
        // 1. Get user from local database
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + userId)
                );
//        return autoUserMapper.mapToUserResponse(user);

        // 2. Map User -> UserResponse
        UserResponse userResponse =
                autoUserMapper.mapToUserResponse(user);

        // 3. Get addresses from Beeceptor or bruno
        List<AddressResponse> addresses =
                addressClient.getAddressesByUserId((long) userId);

        // 4. Add addresses to UserResponse
        userResponse.setAddresses(addresses);

        // 5. Get Orders from bruno
        List<OrderResponse> orders = orderClient.getOrdersByUserId((long) userId);

        userResponse.setOrders(orders);

        return userResponse;
    }*/

    /*@Override
    public UserResponse getUserById(int userId) {
        log.info("received user id: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + userId));

        UserResponse userResponse = autoUserMapper.mapToUserResponse(user);
        List<PartialResponseWarning> warnings = new ArrayList<>();

        // Address service
        try {
            List<AddressResponse> addresses =
                    addressClient.getAddressesByUserId((long) userId);
            userResponse.setAddresses(addresses);
        } catch (DownstreamServiceException | DownstreamServiceTimeoutException e) {
            // Service is actually down - add warning and continue
            log.warn("Address service unavailable for userId={}", userId);
            userResponse.setAddresses(Collections.emptyList());
            warnings.add(
                    new PartialResponseWarning(
                            "ADDRESS_SERVICE",
                            "Address information is temporarily unavailable"
                    )
            );
        }

        // Order service
        try {
            List<OrderResponse> orders =
                    orderClient.getOrdersByUserId((long) userId);
            userResponse.setOrders(orders);
        } catch (DownstreamServiceException | DownstreamServiceTimeoutException e) {
            // Service is actually down - add warning and continue
            log.warn("Order service unavailable for userId={}", userId);
            userResponse.setOrders(Collections.emptyList());
            warnings.add(
                    new PartialResponseWarning(
                            "ORDER_SERVICE",
                            "Order information is temporarily unavailable"
                    )
            );
        }

        userResponse.setWarnings(warnings);
        return userResponse;
    }*/

    @Override
    public UserResponse getUserById(int userId) {
        log.info("received user id: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + userId
                        ));

        UserResponse userResponse =
                autoUserMapper.mapToUserResponse(user);

        List<PartialResponseWarning> warnings =
                new ArrayList<>();

        ServiceCallResult<List<AddressResponse>> addressResult =
                addressClient.getAddressesByUserId((long) userId);

        userResponse.setAddresses(
                addressResult.getData() != null
                        ? addressResult.getData()
                        : Collections.emptyList()
        );

        if (!addressResult.isHasData()
                && addressResult.getWarning() != null) {
            warnings.add(addressResult.getWarning());
        }

        ServiceCallResult<List<OrderResponse>> orderResult =
                orderClient.getOrdersByUserId((long) userId);

        userResponse.setOrders(
                orderResult.getData() != null
                        ? orderResult.getData()
                        : Collections.emptyList()
        );

        if (!orderResult.isHasData()
                && orderResult.getWarning() != null) {
            warnings.add(orderResult.getWarning());
        }

        userResponse.setWarnings(warnings);

        return userResponse;
    }

   /* @Override
    public UserResponse getUserById(int userId) {

        log.info("received user id: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + userId));

        UserResponse userResponse =
                autoUserMapper.mapToUserResponse(user);

        List<PartialResponseWarning> warnings = new ArrayList<>();

        // Address service
        try {

            List<AddressResponse> addresses =
                    addressClient.getAddressesByUserId((long) userId);

            userResponse.setAddresses(addresses);

        } catch (Exception e) {

            log.error(
                    "Address service unavailable for userId={}",
                    userId,
                    e);

            userResponse.setAddresses(Collections.emptyList());

            warnings.add(
                    new PartialResponseWarning(
                            "ADDRESS_SERVICE",
                            "Address information is temporarily unavailable"
                    )
            );
        }

        // Order service
        try {

            List<OrderResponse> orders =
                    orderClient.getOrdersByUserId((long) userId);

            userResponse.setOrders(orders);

        } catch (Exception e) {

            log.error(
                    "Order service unavailable for userId={}",
                    userId,
                    e);

            userResponse.setOrders(Collections.emptyList());

            warnings.add(
                    new PartialResponseWarning(
                            "ORDER_SERVICE",
                            "Order information is temporarily unavailable"
                    )
            );
        }

        userResponse.setWarnings(warnings);

        return userResponse;
    }*/

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(autoUserMapper::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUserById(int userId, UserRequest user) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        log.info("in user service implementation : user id --> {}, name --> {} {}, email --> {}", existingUser.getId(), existingUser.getFirstName(), existingUser.getLastName(), existingUser.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        User updateUser = userRepository.save(existingUser);
        log.info("in user service implementation : user id --> {}, name --> {} {}, email --> {}", updateUser.getId(), updateUser.getFirstName(), updateUser.getLastName(), updateUser.getEmail());
        return autoUserMapper.mapToUserResponse(updateUser);
    }

    public void deleteUserById(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + userId)
                );

        userRepository.delete(user);
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
