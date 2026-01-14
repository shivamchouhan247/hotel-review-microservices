package com.hotelreview.rating.service;

import com.hotelreview.rating.dto.request.RatingRequest;
import com.hotelreview.rating.dto.response.RatingResponse;
import com.hotelreview.rating.entity.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {
    public RatingResponse saveRating(RatingRequest request);

    public RatingResponse getRatingById(String ratingId);

    public List<RatingResponse> getRatingByuserId(String userId);

    public List<RatingResponse> getRatingByhotelId(String hotelId);

    public List<RatingResponse> getAllRating();


}
