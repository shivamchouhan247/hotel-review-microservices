package com.hotelreview.user.service.external;

import com.hotelreview.user.entity.Hotel;

import java.util.List;

public interface HotelService {
    public List<Hotel> getBatchHotels(List<Integer> hotelIds);
}
