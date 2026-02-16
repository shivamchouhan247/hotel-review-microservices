package com.hotelreview.user.service;

import com.hotelreview.user.dto.request.CreateUserRequest;
import com.hotelreview.user.dto.response.UserResponse;
import com.hotelreview.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    public User saveUser(CreateUserRequest request);

    public UserResponse deleteUser(String userId);

    public User updateUser(User user);

    public List<UserResponse> getAllUser();
    public User getUser(String userId);
    public UserResponse getUserDetails(String userId);
}
