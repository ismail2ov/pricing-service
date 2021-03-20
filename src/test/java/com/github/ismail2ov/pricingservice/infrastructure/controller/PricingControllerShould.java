package com.github.ismail2ov.pricingservice.infrastructure.controller;

import com.github.ismail2ov.pricingservice.domain.PriceDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PricingControllerShould {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void return_not_found_when_date_is_not_specified() {
        String url = String.format("/api/brand/%d/products/%d", 1, 35455);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @CsvSource({
            "2, 35455, 2020-06-14T10:00:00",
            "1, 35456, 2020-06-14T10:00:00",
            "1, 35455, 2021-03-20T10:00:00"
    })
    void return_not_found(Long brandId, Long productId, String date) {
        String url = String.format("/api/brand/%d/products/%d?date=%s", brandId, productId, date);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 35455, 2020-06-14T10:00:00, 35.50",
            "1, 35455, 2020-06-14T16:00:00, 25.45",
            "1, 35455, 2020-06-14T21:00:00, 35.50",
            "1, 35455, 2020-06-15T10:00:00, 30.50",
            "1, 35455, 2020-06-16T21:00:00, 38.95"
    })
    public void return_product_price(Long brandId, Long productId, String date, Double expected) {
        String url = String.format("/api/brand/%d/products/%d?date=%s", brandId, productId, date);

        ResponseEntity<PriceDto> response = restTemplate.getForEntity(url, PriceDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PriceDto priceDto = response.getBody();
        assertThat(priceDto).isNotNull();
        assertThat(priceDto.getPrice()).isEqualTo(expected);
    }
}