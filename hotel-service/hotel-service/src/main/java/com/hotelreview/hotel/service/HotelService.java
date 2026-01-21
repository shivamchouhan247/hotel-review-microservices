package com.hotelreview.hotel.service;

import com.hotelreview.hotel.dto.request.HotelRequest;
import com.hotelreview.hotel.dto.response.HotelResponse;
import com.hotelreview.hotel.entity.Hotel;

import java.util.List;

public interface HotelService {
    public HotelResponse saveHotel(HotelRequest request);

    public HotelResponse getHotel(Integer hotelId);

    public List<HotelResponse> getHotels();

    public List<HotelResponse> getBatchHotelDetails(List<String> hotelIds);
}
