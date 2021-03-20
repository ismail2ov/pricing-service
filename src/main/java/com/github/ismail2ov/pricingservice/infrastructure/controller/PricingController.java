package com.github.ismail2ov.pricingservice.infrastructure.controller;

import com.github.ismail2ov.pricingservice.application.PricingService;
import com.github.ismail2ov.pricingservice.domain.Price;
import com.github.ismail2ov.pricingservice.domain.PriceDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class PricingController {

    private final PricingService pricingService;

    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping("/brand/{brandId}/products/{productId}")
    public ResponseEntity<PriceDto> getPrice(@PathVariable Integer brandId,
                                             @PathVariable Integer productId,
                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

        LocalDateTime queryDate = (date != null) ? date : LocalDateTime.now();

        Price price = pricingService.getPrice(brandId, productId, queryDate);

        return ResponseEntity.ok(PriceDto.from(price));
    }
}
