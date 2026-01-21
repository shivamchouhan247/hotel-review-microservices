package com.hotelreview.hotel.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Valid
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelBatchRequest {
    @NotEmpty(message = "hotelIds must not be empty")
    List<String> hotelIds = new ArrayList<>();
}
