package com.hotelreview.user.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hotel {
    private Integer hotelId;
    private String hotelName;
    private String location;
    private String about;
}
