package com.hotelreview.hotel.mapper;

import com.hotelreview.hotel.dto.request.HotelRequest;
import com.hotelreview.hotel.dto.response.HotelResponse;
import com.hotelreview.hotel.entity.Hotel;

public class HotelMapper {
    public static Hotel toEntity(HotelRequest request) {
        return Hotel.builder()
                .hotelName(request.getHotelName())
                .location(request.getLocation())
                .about(request.getAbout()).build();
    }

    public static HotelResponse toHotelResponse(Hotel hotel) {
        return HotelResponse.builder()
                .hotelName(hotel.getHotelName())
                .location(hotel.getLocation())
                .about(hotel.getAbout())
                .hotelId(hotel.getHotelId()).build();
    }
}
