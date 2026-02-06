package com.hotelreview.user.external.service;

import com.hotelreview.user.dto.common.ApiResponse;
import com.hotelreview.user.dto.request.HotelBatchRequest;
import com.hotelreview.user.entity.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("HOTEL-SERVICE")
public interface HotelService {
    @PostMapping("/api/v1/hotels/batch")
    ApiResponse<List<Hotel>> getBatchHotels(@RequestBody HotelBatchRequest hotelBatchRequest);
}
