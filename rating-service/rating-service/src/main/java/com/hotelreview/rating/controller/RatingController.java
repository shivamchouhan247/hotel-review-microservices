package com.hotelreview.rating.controller;

import com.hotelreview.rating.dto.common.ApiResponse;
import com.hotelreview.rating.dto.request.RatingRequest;
import com.hotelreview.rating.dto.response.RatingResponse;
import com.hotelreview.rating.service.RatingService;
import com.hotelreview.rating.util.CommonLogic;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    private final static Logger LOGGER = LoggerFactory.getLogger(RatingController.class);

    @PostMapping
    public ResponseEntity<ApiResponse> createRating(@Valid @RequestBody RatingRequest request) {
        LOGGER.info("Create rating request recieved");
        ratingService.saveRating(request);
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.CREATED.value(), "SUCCESS", "Rating saved successfully!");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<ApiResponse> getRating(@PathVariable String ratingId) {
        RatingResponse ratingResponse = ratingService.getRatingById(ratingId);
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.OK.value(), "SUCCESS", ratingResponse);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("users/{userId}")
    public ResponseEntity<ApiResponse> getRatingByUserId(@PathVariable String userId) {
        List<RatingResponse> ratings = ratingService.getRatingByuserId(userId);
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.OK.value(), "SUCCESS", ratings);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("hotels/{hotelId}")
    public ResponseEntity<ApiResponse> getRatingByHotelId(@PathVariable String hotelId) {
        LOGGER.info("Get rating request recieved for hotelId: "+hotelId);
        List<RatingResponse> ratings = ratingService.getRatingByhotelId(hotelId);
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.OK.value(), "SUCCESS", ratings);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping
    public ResponseEntity<ApiResponse> getAllRatings() {
        List<RatingResponse> ratings = ratingService.getAllRating();
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.OK.value(), "SUCCESS", ratings);
        return ResponseEntity.ok(apiResponse);
    }
}
