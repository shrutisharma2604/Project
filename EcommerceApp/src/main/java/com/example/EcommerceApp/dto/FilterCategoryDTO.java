package com.example.EcommerceApp.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class FilterCategoryDTO {

    private Set<HashMap<String,String>> filedValuesSet;
    private List<String> brands;
    String minPrice;
    String maxPrice;

    public Set<HashMap<String, String>> getFiledValuesSet() {
        return filedValuesSet;
    }

    public void setFiledValuesSet(Set<HashMap<String, String>> filedValuesSet) {
        this.filedValuesSet = filedValuesSet;
    }

    public List<String> getBrands() {
        return brands;
    }

    public void setBrands(List<String> brands) {
        this.brands = brands;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public String toString() {
        return "CategoryFilterDto{" +
                "filedValuesSet=" + filedValuesSet +
                ", brands=" + brands +
                ", minPrice='" + minPrice + '\'' +
                ", maxPrice='" + maxPrice + '\'' +
                '}';
    }
}
