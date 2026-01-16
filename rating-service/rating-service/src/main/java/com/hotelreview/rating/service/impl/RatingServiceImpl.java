package com.hotelreview.rating.service.impl;

import com.hotelreview.rating.dto.request.RatingRequest;
import com.hotelreview.rating.dto.response.RatingResponse;
import com.hotelreview.rating.entity.Rating;
import com.hotelreview.rating.exception.RatingAlreadyExistsException;
import com.hotelreview.rating.exception.ResourceNotFoundException;
import com.hotelreview.rating.mapper.RatingMapper;
import com.hotelreview.rating.repository.RatingRepository;
import com.hotelreview.rating.service.RatingService;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {
    private RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public RatingResponse saveRating(RatingRequest request) {
        //one user per hotel rating is allowed
        boolean isAlreadyRated = getRatingByUserIdAndHotelId(request.getUserId(), request.getHotelId()).isPresent();
        if (isAlreadyRated) {
            throw new RatingAlreadyExistsException("Rating already exists for this user and hotel");
        }
        Rating rating = ratingRepository.save(RatingMapper.toEntity(request));
        return RatingMapper.toRatingResponse(rating);
    }

    public Optional<Rating> getRatingByUserIdAndHotelId(String userId, String hotelId) {
        return ratingRepository.findByUserIdAndHotelId(userId, hotelId);
    }

    @Override
    public RatingResponse getRatingById(String ratingId) {
        Rating rating = ratingRepository.findById(ratingId).orElseThrow(() -> new ResourceNotFoundException("Rating does not exist with the given ratingId: " + ratingId));
        return RatingMapper.toRatingResponse(rating);
    }

    @Override
    public List<RatingResponse> getRatingByuserId(String userId) {
        List<Rating> ratings = ratingRepository.findByUserId(userId);
        return ratings.stream().map(RatingMapper::toRatingResponse).toList();
    }

    @Override
    public List<RatingResponse> getRatingByhotelId(String hotelId) {
        List<Rating> ratings = ratingRepository.findByHotelId(hotelId);
        return ratings.stream().map(RatingMapper::toRatingResponse).toList();
    }

    @Override
    public List<RatingResponse> getAllRating() {
        List<Rating> ratings = ratingRepository.findAll();
        return ratings.stream().map(RatingMapper::toRatingResponse).toList();
    }
}
