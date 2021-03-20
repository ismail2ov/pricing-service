package com.github.ismail2ov.pricingservice.domain;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException() {

    }

    public PriceNotFoundException(String message) {
        super(message);
    }
}
