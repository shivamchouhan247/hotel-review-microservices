package com.hotelreview.hotel.service.impl;

import com.hotelreview.hotel.dto.request.HotelRequest;
import com.hotelreview.hotel.dto.response.HotelResponse;
import com.hotelreview.hotel.entity.Hotel;
import com.hotelreview.hotel.exception.HotelCreationException;
import com.hotelreview.hotel.exception.ResourceNotFoundException;
import com.hotelreview.hotel.mapper.HotelMapper;
import com.hotelreview.hotel.repository.HotelRepository;
import com.hotelreview.hotel.service.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(HotelServiceImpl.class);

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public HotelResponse saveHotel(HotelRequest request) {
        LOGGER.info("Saving hotel with name: {}", request.getHotelName());
        try {
            Hotel hotel = HotelMapper.toEntity(request);
            hotelRepository.save(hotel);
            return HotelMapper.toHotelResponse(hotel);
        } catch (Exception e) {
            LOGGER.error("Error while saving hotel details: {}", e.getMessage());
            throw new HotelCreationException();
        }
    }

    @Override
    public HotelResponse getHotel(Integer hotelId) {
        if (hotelId == null || hotelId < 0) {
            throw new IllegalArgumentException("Hotel must not be null or smaller than zero");
        }
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel with the given hotelId is not present: " + hotelId));
        return HotelMapper.toHotelResponse(hotel);
    }

    @Override
    public List<HotelResponse> getHotels() {
        List<HotelResponse> hotels = hotelRepository.findAll().stream().map(HotelMapper::toHotelResponse).toList();
        if (hotels.isEmpty()) {
            throw new ResourceNotFoundException("No Hotel is registered");
        }
        return hotels;
    }
}
