package com.example.EcommerceApp.dto;

import java.util.Map;

public class CategoryFieldValueDTO {

    private Long parentId;

    private String categoryName;

    private Long categoryId;

    private Map<String, String> fieldValueMap;

    public Map<String, String> getFieldValueMap() {
        return fieldValueMap;
    }

    public void setFieldValueMap(Map<String, String> fieldValueMap) {
        this.fieldValueMap = fieldValueMap;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "CategoryFieldValueDTO{" +
                "parentId=" + parentId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryId=" + categoryId +
                ", fieldValueMap=" + fieldValueMap +
                '}';
    }
}
