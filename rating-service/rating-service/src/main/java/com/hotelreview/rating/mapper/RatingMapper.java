package com.hotelreview.rating.mapper;

import com.hotelreview.rating.dto.request.RatingRequest;
import com.hotelreview.rating.dto.response.RatingResponse;
import com.hotelreview.rating.entity.Rating;

public class RatingMapper {
    public static Rating toEntity(RatingRequest request) {
        return Rating.builder()
                .userId(request.getUserId().trim())
                .hotelId(request.getHotelId().trim())
                .rating(request.getRating())
                .feedback(request.getFeedback().trim())
                .build();
    }


    public static RatingResponse toRatingResponse(Rating rating) {
        return RatingResponse.builder()
                .ratingId(rating.getRatingId())
                .userId(rating.getUserId())
                .hotelId(rating.getHotelId())
                .rating(rating.getRating())
                .feedback(rating.getFeedback())
                .build();
    }
}
