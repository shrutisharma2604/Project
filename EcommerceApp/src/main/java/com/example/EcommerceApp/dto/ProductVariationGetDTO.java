package com.example.EcommerceApp.dto;

import net.minidev.json.JSONObject;

import javax.persistence.Lob;

public class ProductVariationGetDTO {
    private Long id;
    private Long price;

    @Lob
    private JSONObject metadata;
    private int quantity;
    private boolean isActive;

    private Long productId;

    private String productName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public JSONObject getMetadata() {
        return metadata;
    }

    public void setMetadata(JSONObject metadata) {
        this.metadata = metadata;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "ProductVariationGetDTO{" +
                "id=" + id +
                ", price=" + price +
                ", metadata=" + metadata +
                ", quantity=" + quantity +
                ", isActive=" + isActive +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                '}';
    }
}
