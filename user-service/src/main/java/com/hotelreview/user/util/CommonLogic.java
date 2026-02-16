package com.hotelreview.user.util;

import com.hotelreview.user.dto.common.ApiResponse;

public class CommonLogic {
    public static <T> ApiResponse<T> generateApiResponse(int code, String status, T data) {
        return ApiResponse.<T>builder()
                .code(code)
                .status(status)
                .data(data)
                .build();
    }
}
