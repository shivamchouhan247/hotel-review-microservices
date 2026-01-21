package com.hotelreview.hotel.repository;

import com.hotelreview.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    public List<Hotel> findByHotelIdIn(List<Integer> ids);
}
