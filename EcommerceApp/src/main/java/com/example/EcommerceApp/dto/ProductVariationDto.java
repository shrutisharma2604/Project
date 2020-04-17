package com.example.EcommerceApp.dto;

import net.minidev.json.JSONObject;

import javax.persistence.Lob;

public class ProductVariationDto {
    private Long id;
    private Double price;
    @Lob
    private JSONObject metaData;
    private Integer quantity;
    private boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public JSONObject getMetaData() {
        return metaData;
    }

    public void setMetaData(JSONObject metaData) {
        this.metaData = metaData;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
