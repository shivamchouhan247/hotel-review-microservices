package com.hotelreview.user.service.impl;

import com.hotelreview.user.dto.request.CreateUserRequest;
import com.hotelreview.user.dto.response.UserResponse;
import com.hotelreview.user.entity.Hotel;
import com.hotelreview.user.entity.Rating;
import com.hotelreview.user.entity.User;
import com.hotelreview.user.exception.DuplicateResourceException;
import com.hotelreview.user.exception.ResourceNotFoundException;
import com.hotelreview.user.mapper.UserMapper;
import com.hotelreview.user.repository.UserRepository;
import com.hotelreview.user.service.UserService;
import com.hotelreview.user.service.external.HotelService;
import com.hotelreview.user.service.external.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RatingService ratingService;
    private final HotelService hotelService;

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
    public UserResponse getUser(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User does not exist with the give userId: " + userId));
        try {
            List<Rating> ratings = ratingService.getUserRatings(userId);
            if (!ratings.isEmpty()) {
                List<Integer> hotelIds = ratings.stream().map(r -> Integer.parseInt(r.getHotelId())).toList();
                List<Hotel> hotelList = hotelService.getBatchHotels(hotelIds);

                if (!hotelList.isEmpty()) {
                    Map<String, Hotel> hotelMap = hotelList.stream().collect(Collectors.toMap(
                            h -> Integer.toString(h.getHotelId()),
                            h -> h
                    ));
                    ratings.forEach(r ->
                            r.setHotel(hotelMap.get(r.getHotelId()))
                    );
                }
            }
            user.setRatings(ratings);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
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

