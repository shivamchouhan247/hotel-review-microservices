package com.hotelreview.hotel.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class HotelRequest {
    @NotBlank
    private String hotelName;
    @NotBlank
    private String location;
    @NotBlank
    private String about;
}
