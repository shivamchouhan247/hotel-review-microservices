package com.hotelreview.user.controller;

import com.hotelreview.user.dto.common.ApiResponse;
import com.hotelreview.user.dto.request.CreateUserRequest;
import com.hotelreview.user.dto.response.UserResponse;
import com.hotelreview.user.entity.User;
import com.hotelreview.user.mapper.UserMapper;
import com.hotelreview.user.service.UserService;
import com.hotelreview.user.util.CommonLogic;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        LOGGER.info("Create user request recieved");

        User user = userService.saveUser(request);
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.CREATED.value(), "SUCCESS", "User Registered Successfully!");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }

    @RateLimiter(name = "userRateLimiter")
    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<?>> getUser(@PathVariable(name = "userId") String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User id must not be null or empty");
        }
        LOGGER.info("Get user request recieved for userId: {}", userId);
        UserResponse userResponse = userService.getUserDetails(userId);
        ApiResponse<?> apiResponse = CommonLogic.generateApiResponse(HttpStatus.OK.value(), userResponse.isDegraded() ? "PARTIAL_SUCCESS" : "SUCCESS", userResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable(name = "userId") String userId) {
        Map<String, Object> response = new HashMap<>();

        UserResponse userResponse = userService.deleteUser(userId);
        response.put("user", userResponse);
        response.put("message", "User deleted successfully!");
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.OK.value(), "SUCCESS", response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getUsers() {
        List<UserResponse> users = userService.getAllUser();
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.OK.value(), "SUCCESS", users);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
