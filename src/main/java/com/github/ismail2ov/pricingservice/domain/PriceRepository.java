package com.github.ismail2ov.pricingservice.domain;

import java.time.LocalDateTime;

public interface PriceRepository {

    Price getPrice(Integer brandId, Integer productId, LocalDateTime dateTime);
}
