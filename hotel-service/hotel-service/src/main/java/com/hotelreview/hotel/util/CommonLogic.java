package com.hotelreview.hotel.util;

import com.hotelreview.hotel.dto.common.ApiResponse;

public class CommonLogic {
    public static ApiResponse generateApiResponse(int code, String status, Object data) {
        return ApiResponse.builder()
                .code(code)
                .status(status)
                .data(data)
                .build();
    }
}
