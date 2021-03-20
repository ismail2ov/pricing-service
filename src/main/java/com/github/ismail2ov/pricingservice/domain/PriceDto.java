package com.github.ismail2ov.pricingservice.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class PriceDto {
    Long productId;

    Long brandId;

    Integer priceList;

    LocalDateTime startDate;

    LocalDateTime endDate;

    Double price;

    String currency;

    public static PriceDto from(Price price) {
        return PriceDto.builder()
                .productId(price.getProductId())
                .brandId(price.getBrandId())
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .price(price.getPrice())
                .currency(price.getCurrency())
                .build();
    }
}
