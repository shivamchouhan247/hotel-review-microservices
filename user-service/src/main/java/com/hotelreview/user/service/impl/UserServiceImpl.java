package com.hotelreview.user.service.impl;

import com.hotelreview.user.dto.common.ApiResponse;
import com.hotelreview.user.dto.request.CreateUserRequest;
import com.hotelreview.user.dto.request.HotelBatchRequest;
import com.hotelreview.user.dto.response.UserResponse;
import com.hotelreview.user.entity.Hotel;
import com.hotelreview.user.entity.Rating;
import com.hotelreview.user.entity.User;
import com.hotelreview.user.exception.DuplicateResourceException;
import com.hotelreview.user.exception.ExternalServiceException;
import com.hotelreview.user.exception.ResourceNotFoundException;
import com.hotelreview.user.external.service.HotelService;
import com.hotelreview.user.external.service.RatingService;
import com.hotelreview.user.mapper.UserMapper;
import com.hotelreview.user.repository.UserRepository;
import com.hotelreview.user.service.UserService;
import com.hotelreview.user.util.CommonLogic;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private RatingService ratingService;
    private HotelService hotelService;

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, RatingService ratingService, HotelService hotelService) {
        this.userRepository = userRepository;
        this.ratingService = ratingService;
        this.hotelService = hotelService;
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
    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User does not exist with the give userId: " + userId));
    }

    @Override
    @Retry(name = "ratingHotelRetry", fallbackMethod = "getUserDetailsFallbackMethod")
//    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "getUserDetailsFallbackMethod")
    public UserResponse getUserDetails(String userId) {
        LOGGER.info("Processing get user details request");
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User does not exist with the give userId: " + userId));
        try {
            LOGGER.info("Calling rating service:");
            ApiResponse<List<Rating>> RatingApiResponse = ratingService.getRatings(userId);
            List<Rating> ratings = RatingApiResponse.getData();
            if (!ratings.isEmpty()) {
                List<String> hotelIds = ratings.stream().map(Rating::getHotelId).toList();
                LOGGER.info("Calling hotel service ");
                HotelBatchRequest hotelBatchRequest = new HotelBatchRequest(hotelIds);
                ApiResponse<List<Hotel>> hotelApiResponse = hotelService.getBatchHotels(hotelBatchRequest);
                List<Hotel> hotelList = hotelApiResponse.getData();
                if (!hotelList.isEmpty()) {
                    Map<String, Hotel> hotelMap = hotelList.stream().collect(Collectors.toMap(
                            h -> Integer.toString(h.getHotelId()),
                            h -> h
                    ));
                    ratings.forEach(r ->
                            r.setHotel(hotelMap.get(r.getHotelId()))
                    );
                }
            } else {
                LOGGER.info("No rating found for user ");
            }
            user.setRatings(ratings);
        } catch (Exception e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw e;

        }
        return UserMapper.toUserResponse(user);
    }

    public UserResponse getUserDetailsFallbackMethod(String userId, Throwable ex) {
        LOGGER.error("Fallback triggered for userId: {} , reason: {}", userId, ex.getMessage());
        User user = getUser(userId);
        UserResponse userResponse = UserMapper.toUserResponse(user);
        userResponse.setDegraded(true);
        return userResponse;
    }

    @Transactional
    @Override
    public UserResponse deleteUser(String userId) {
        User user = getUser(userId);
        userRepository.deleteById(userId);
        return UserMapper.toUserResponse(user);
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

