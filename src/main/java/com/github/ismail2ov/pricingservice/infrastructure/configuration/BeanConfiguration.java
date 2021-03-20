package com.github.ismail2ov.pricingservice.infrastructure.configuration;

import com.github.ismail2ov.pricingservice.application.PricingService;
import com.github.ismail2ov.pricingservice.domain.PriceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    static PricingService pricingService(PriceRepository priceRepository) {
        return new PricingService(priceRepository);
    }
}
