package com.example.EcommerceApp.dto;


public class ProductVariationGetDTO {
    private Long id;
    private Long price;
    private int quantity;
    private boolean isActive;

    private Long imageId;
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

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "ProductVariationGetDTO{" +
                "id=" + id +
                ", price=" + price +
                ", quantity=" + quantity +
                ", isActive=" + isActive +
                ", imageId=" + imageId +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                '}';
    }
}
