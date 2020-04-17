package com.example.EcommerceApp.dto;

import javax.validation.constraints.NotEmpty;

public class ProductDto {

    private Long id;

    @NotEmpty
    private String productName;

    @NotEmpty
    private String description;

    @NotEmpty
    private String brand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
