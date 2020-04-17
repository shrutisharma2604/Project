package com.example.EcommerceApp.dto;

import com.example.EcommerceApp.entities.CategoryMetaDataField;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

public class CategoryMetaDataFieldValueDto  {
    private Long categoryId;
    private List<CategoryMetaDataField> fieldValue;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<CategoryMetaDataField> getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(List<CategoryMetaDataField> fieldValue) {
        this.fieldValue = fieldValue;
    }
}
