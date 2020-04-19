package com.example.EcommerceApp.dto;

import com.example.EcommerceApp.entities.Category;

public class ProductViewDTO {

    private Long id;

    private Category category;

    private String companyName;

    private String productName;

    private String description;

    private String brand;

    private boolean isCancellable;

    private boolean isReturnable;

    private boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public boolean isCancellable() {
        return isCancellable;
    }

    public void setCancellable(boolean cancellable) {
        isCancellable = cancellable;
    }

    public boolean isReturnable() {
        return isReturnable;
    }

    public void setReturnable(boolean returnable) {
        isReturnable = returnable;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "ProductViewDTO{" +
                "id=" + id +
                ", category=" + category +
                ", companyName='" + companyName + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", isCancellable=" + isCancellable +
                ", isReturnable=" + isReturnable +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
