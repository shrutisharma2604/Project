package com.example.EcommerceApp.dto;

import com.example.EcommerceApp.entities.Category;
import java.util.HashMap;
import java.util.Set;

public class CategoryDTO {

   private Category category;
   private Set<HashMap<String,String>> fieldValues;
   private Set<Category> subCategories;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<HashMap<String, String>> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(Set<HashMap<String, String>> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public Set<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<Category> subCategories) {
        this.subCategories = subCategories;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "category=" + category +
                ", fieldValues=" + fieldValues +
                ", subCategories=" + subCategories +
                '}';
    }
}
