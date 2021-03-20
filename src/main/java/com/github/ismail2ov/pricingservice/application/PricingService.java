package com.github.ismail2ov.pricingservice.application;

import com.github.ismail2ov.pricingservice.domain.Price;
import com.github.ismail2ov.pricingservice.domain.PriceRepository;

import java.time.LocalDateTime;

public class PricingService {

    private final PriceRepository priceRepository;

    public PricingService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price getPrice(Integer brandId, Integer productId, LocalDateTime dateTime) {
        return priceRepository.getPrice(brandId, productId, dateTime);
    }
}
