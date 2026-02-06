package com.hotelreview.user.external.service;

import com.hotelreview.user.dto.common.ApiResponse;
import com.hotelreview.user.entity.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("RATING-SERVICE")
public interface RatingService {
    @GetMapping("/api/v1/ratings/users/{userId}")
    ApiResponse<List<Rating>> getRatings(@PathVariable("userId") String userId);
}
