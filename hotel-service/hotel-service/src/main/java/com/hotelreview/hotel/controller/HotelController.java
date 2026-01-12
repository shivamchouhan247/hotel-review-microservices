package com.hotelreview.hotel.controller;

import com.hotelreview.hotel.dto.common.ApiResponse;
import com.hotelreview.hotel.dto.request.HotelRequest;
import com.hotelreview.hotel.dto.response.HotelResponse;
import com.hotelreview.hotel.entity.Hotel;
import com.hotelreview.hotel.service.HotelService;
import com.hotelreview.hotel.util.CommonLogic;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {
    private HotelService hotelService;
    private final static Logger LOGGER = LoggerFactory.getLogger(HotelController.class);

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createHotel(@Valid @RequestBody HotelRequest request) {
        LOGGER.info("Hotel registration request recieved with hotel name: {}", request.getHotelName());
        hotelService.saveHotel(request);
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.CREATED.value(), "SUCCESS", "Hotel Registered Successfully!");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<ApiResponse> getHotel(@PathVariable Integer hotelId) {
        LOGGER.info("Get Hotel details request is recieved for hotel id: {}", hotelId);
        HotelResponse hotelResponse = hotelService.getHotel(hotelId);
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.OK.value(), "SUCCESS", hotelResponse);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getHotels() {
        LOGGER.info("Get all hotel details request is recieved");
        List<HotelResponse> hotels = hotelService.getHotels();
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.OK.value(), "SUCCESS", hotels);
        return ResponseEntity.ok(apiResponse);
    }


}
