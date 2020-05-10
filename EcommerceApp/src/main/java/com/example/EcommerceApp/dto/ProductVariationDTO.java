package com.example.EcommerceApp.dto;

public class ProductVariationDTO {
    private Long id;
    private Long price;
    private String image;
    private Integer quantity;
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
                ", image='" + image + '\'' +
                ", quantity=" + quantity +
                ", isActive=" + isActive +
                '}';
    }
}
