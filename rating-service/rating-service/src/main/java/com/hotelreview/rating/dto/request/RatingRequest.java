package com.hotelreview.rating.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class RatingRequest {
    @NotBlank(message = "userId is mandatory")
    private String userId;
    @NotBlank(message = "hotelId is mandatory")
    private String hotelId;
    @NotBlank(message = "rating is mandatory")
    private int rating;
    @NotBlank(message = "feedback is mandatory")
    private String feedback;
}
