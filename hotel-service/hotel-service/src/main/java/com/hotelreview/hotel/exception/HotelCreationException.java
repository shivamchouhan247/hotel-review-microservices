package com.hotelreview.hotel.exception;

public class HotelCreationException extends RuntimeException {
    public HotelCreationException(String msg) {
        super(msg);
    }

    public HotelCreationException() {
        super("Unable to save hotel at the moment");
    }
}
