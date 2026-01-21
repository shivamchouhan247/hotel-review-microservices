package com.hotelreview.user.service.external;

import com.hotelreview.user.entity.Rating;

import java.util.List;

public interface RatingService {
    public List<Rating> getUserRatings(String userId) throws Exception;
}
