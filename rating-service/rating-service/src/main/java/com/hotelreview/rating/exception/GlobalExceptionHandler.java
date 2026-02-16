package com.hotelreview.rating.exception;

import com.hotelreview.rating.dto.common.ApiResponse;
import com.hotelreview.rating.util.CommonLogic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ERROR", "Internal server error connect with the tech team");
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(
                FieldError::getField,
                FieldError::getDefaultMessage,
                (oldValue, newValue) -> oldValue
        ));
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.BAD_REQUEST.value(), "ERROR", errors);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.BAD_REQUEST.value(), "ERROR", "Invalid request");
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.BAD_REQUEST.value(), "ERROR", "Invalid request or url");
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RatingAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleRatingAlreadyExistsException(RatingAlreadyExistsException e) {
        System.out.println(e.getMessage());
        ApiResponse apiResponse = CommonLogic.generateApiResponse(HttpStatus.CONFLICT.value(), "ERROR", e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ApiResponse> handleExternalServiceException(ExternalServiceException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(CommonLogic.generateApiResponse(
                        HttpStatus.SERVICE_UNAVAILABLE.value(),
                        "SERVICE UNAVAILABLE",
                        e.getMessage()
                ));
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
