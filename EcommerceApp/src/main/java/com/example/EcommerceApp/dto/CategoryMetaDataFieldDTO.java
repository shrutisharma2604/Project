package com.example.EcommerceApp.dto;

import java.util.HashMap;
import java.util.HashSet;

public class CategoryMetaDataFieldDTO {
 private Long categoryId;
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

    @Override
    public String toString() {
        return "CategoryMetaDataFieldDto{" +
                "categoryId=" + categoryId +
                ", filedIdValues=" + filedValues +
                '}';
    }
}
