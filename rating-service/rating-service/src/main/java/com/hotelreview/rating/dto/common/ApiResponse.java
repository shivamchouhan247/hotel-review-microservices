package com.hotelreview.rating.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
    private int code;
    private String status;
    private Object data;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();


}
