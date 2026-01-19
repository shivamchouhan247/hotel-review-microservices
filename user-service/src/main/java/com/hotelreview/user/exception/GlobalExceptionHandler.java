package com.hotelreview.user.exception;

import com.hotelreview.user.dto.common.ApiResponse;
import com.hotelreview.user.util.CommonLogic;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.NOT_FOUND.value(), "ERROR", ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);

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

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse> handleDuplicateResourceException(DuplicateResourceException e) {
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.CONTINUE.value(), "ERROR", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ERROR", "Internal server error");
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex) {

        ApiResponse response = CommonLogic.generateApiResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "FAILURE", "Unsupported Content-Type. Please use application/json");
        return ResponseEntity
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(response);
    }

}
