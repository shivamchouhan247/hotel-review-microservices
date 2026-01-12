package com.hotelreview.hotel.exception;

import com.hotelreview.hotel.dto.common.ApiResponse;
import com.hotelreview.hotel.util.CommonLogic;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.NOT_FOUND.value(), "ERROR", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.BAD_REQUEST.value(), "ERROR", ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

    }

    //Exception handling for empty and missing body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.BAD_REQUEST.value(), "ERROR", "Invalid Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    // Exception handling for invalid fields in request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> msg = e.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage
                ));
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.BAD_REQUEST.value(), "ERROR", msg);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);

    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.BAD_REQUEST.value(), "ERROR", "Invalid endpoint or missing path variable");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ERROR", "Internal server error");
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
