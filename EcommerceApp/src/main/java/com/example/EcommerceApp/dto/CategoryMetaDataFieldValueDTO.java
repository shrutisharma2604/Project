package com.example.EcommerceApp.dto;

import com.example.EcommerceApp.entities.CategoryMetaDataField;

import java.util.List;

public class CategoryMetaDataFieldValueDTO {
    private Long categoryId;
    private List<CategoryMetaDataField> fieldId;
    private String value;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public List<CategoryMetaDataField> getFieldId() {
        return fieldId;
    }

    public void setFieldId(List<CategoryMetaDataField> fieldId) {
        this.fieldId = fieldId;
    }

    @Override
    public String toString() {
        return "CategoryMetaDataFieldValueDTO{" +
                "categoryId=" + categoryId +
                ", fieldId=" + fieldId +
                ", value='" + value + '\'' +
                '}';
    }
}
