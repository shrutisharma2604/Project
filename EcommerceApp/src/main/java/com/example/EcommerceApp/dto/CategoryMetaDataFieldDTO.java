package com.example.EcommerceApp.dto;

import java.util.HashMap;
import java.util.HashSet;

public class CategoryMetaDataFieldDTO {
 private Long categoryId;
 private String name;
 private HashMap<String, HashSet<String>> filedValues;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public HashMap<String, HashSet<String>> getFiledValues() {
        return filedValues;
    }

    public void setFiledValues(HashMap<String, HashSet<String>> filedValues) {
        this.filedValues = filedValues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryMetaDataFieldDTO{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", filedValues=" + filedValues +
                '}';
    }
}
