package com.hotelreview.rating.exception;

import com.hotelreview.rating.dto.common.ApiResponse;
import com.hotelreview.rating.util.CommonLogic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.NOT_FOUND.value(), "ERROR", e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

}
