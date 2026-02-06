package com.hotelreview.user.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private int code;
    private String status;
    private T data;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();


}
