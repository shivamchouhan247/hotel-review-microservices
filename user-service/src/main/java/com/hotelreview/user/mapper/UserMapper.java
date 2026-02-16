package com.hotelreview.user.mapper;

import com.hotelreview.user.dto.request.CreateUserRequest;
import com.hotelreview.user.dto.response.UserResponse;
import com.hotelreview.user.entity.User;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class UserMapper {
    public static User toEntity(CreateUserRequest request) {
        return User.builder()
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .about(request.getAbout())
                .creationDate(new Date()).build();

    }

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .about(user.getAbout())
                .creationDate(user.getCreationDate())
                .ratings(user.getRatings())
                .build();
    }

}
