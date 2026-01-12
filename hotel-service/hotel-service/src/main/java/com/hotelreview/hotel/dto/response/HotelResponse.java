package com.hotelreview.hotel.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelResponse {
    private Integer hotelId;
    private String hotelName;
    private String location;
    private String about;
}
