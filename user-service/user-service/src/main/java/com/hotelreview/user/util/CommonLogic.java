package com.hotelreview.user.util;

import com.hotelreview.user.dto.common.ApiResponse;

import java.time.LocalDateTime;

public class CommonLogic {
    public static ApiResponse generateApiResponse(int code, String status, Object data) {
        return ApiResponse.builder()
                .code(code)
                .status(status)
                .data(data)
                .build();
    }
}
