package com.example.EcommerceApp.dto;

import net.minidev.json.JSONObject;

import javax.persistence.Lob;

public class ProductVariationDTO {
    private Long id;
    private Long price;

    @Lob
    private JSONObject metadata;
    private String image;
    private Long quantity;
    private boolean isActive;

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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public JSONObject getMetadata() {
        return metadata;
    }

    public void setMetadata(JSONObject metadata) {
        this.metadata = metadata;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ProductVariationDTO{" +
                "id=" + id +
                ", price=" + price +
                ", metadata=" + metadata +
                ", image='" + image + '\'' +
                ", quantity=" + quantity +
                ", isActive=" + isActive +
                '}';
    }
}
