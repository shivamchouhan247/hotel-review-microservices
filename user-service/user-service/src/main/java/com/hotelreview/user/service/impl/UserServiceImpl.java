package com.hotelreview.user.service.impl;

import com.hotelreview.user.dto.request.CreateUserRequest;
import com.hotelreview.user.dto.response.UserResponse;
import com.hotelreview.user.entity.User;
import com.hotelreview.user.exception.DuplicateResourceException;
import com.hotelreview.user.exception.ResourceNotFoundException;
import com.hotelreview.user.mapper.UserMapper;
import com.hotelreview.user.repository.UserRepository;
import com.hotelreview.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(CreateUserRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new DuplicateResourceException("User already registered with email: " + request.getEmail());
                });

        User user = UserMapper.toEntity(request);
        return userRepository.save(user);
    }

    @Override
    public UserResponse getUser(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User id must not be null or empty");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User does not exist with the give userId: " + userId));
        return UserMapper.toUserResponse(user);
    }

    @Transactional
    @Override
    public UserResponse deleteUser(String userId) {
        UserResponse userResponse = getUser(userId);
        userRepository.deleteById(userId);
        return userResponse;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<UserResponse> users = userRepository.findAll().stream().map(UserMapper::toUserResponse).toList();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No user available");
        }
        return users;
    }
}

