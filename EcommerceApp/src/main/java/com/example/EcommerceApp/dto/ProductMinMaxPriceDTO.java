package com.example.EcommerceApp.dto;

import org.springframework.stereotype.Component;

@Component
public interface ProductMinMaxPriceDTO {

    Double getMinPrice();
    Double getMaxPrice();

}
