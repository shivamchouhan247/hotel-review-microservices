package com.hotelreview.rating.service.impl;

import com.hotelreview.rating.dto.request.RatingRequest;
import com.hotelreview.rating.dto.response.RatingResponse;
import com.hotelreview.rating.repository.RatingRepository;
import com.hotelreview.rating.service.RatingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    private RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public RatingResponse saveRating(RatingRequest request) {
        return null;
    }

    @Override
    public RatingResponse getRatingById(String ratingId) {
        return null;
    }

    @Override
    public List<RatingResponse> getRatingByuserId(String userId) {
        return List.of();
    }

    @Override
    public List<RatingResponse> getRatingByhotelId(String hotelId) {
        return List.of();
    }

    @Override
    public List<RatingResponse> getAllRating() {
        return List.of();
    }
}
