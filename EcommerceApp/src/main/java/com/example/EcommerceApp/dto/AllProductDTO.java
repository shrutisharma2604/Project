package com.example.EcommerceApp.dto;

import com.example.EcommerceApp.entities.Product;

import java.util.List;

public class AllProductDTO {

    private Long categoryId;

    private List<Product> productList;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "AllProductDTO{" +
                "categoryId=" + categoryId +
                ", productList=" + productList +
                '}';
    }
}