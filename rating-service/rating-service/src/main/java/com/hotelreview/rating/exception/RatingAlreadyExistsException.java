package com.hotelreview.rating.exception;

import com.hotelreview.rating.entity.Rating;

public class RatingAlreadyExistsException extends RuntimeException {
    public RatingAlreadyExistsException(String str) {
        super(str);
    }
}
