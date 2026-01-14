package com.hotelreview.rating.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingResponse {
    @NotBlank(message = "ratingId is mandatory")
    private String ratingId;
    @NotBlank(message = "userId is mandatory")
    private String userId;
    @NotBlank(message = "hotelId is mandatory")
    private String hotelId;
    @NotBlank(message = "rating is mandatory")
    private int rating;
    @NotBlank(message = "feedback is mandatory")
    private String feedback;
}
