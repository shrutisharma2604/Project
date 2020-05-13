package com.example.EcommerceApp.dto;

public class ProductVariationDTO {
    private Long product_variant_id;
    private Long price;
    private String image;
    private Integer quantity;
    private boolean isActive;

    public Long getProduct_variant_id() {
        return product_variant_id;
    }

    public void setProduct_variant_id(Long product_variant_id) {
        this.product_variant_id = product_variant_id;
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
                "product_variant_id=" + product_variant_id +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", quantity=" + quantity +
                ", isActive=" + isActive +
                '}';
    }
}
