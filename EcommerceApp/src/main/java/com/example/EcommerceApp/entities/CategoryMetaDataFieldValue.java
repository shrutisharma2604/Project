package com.example.EcommerceApp.entities;

import javax.persistence.*;

@Entity
public class CategoryMetaDataFieldValue  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "category_metadata_field_id")
    private CategoryMetaDataField categoryMetaDataField;

    private String value;

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

    public CategoryMetaDataField getCategoryMetaDataField() {
        return categoryMetaDataField;
    }

    public void setCategoryMetaDataField(CategoryMetaDataField categoryMetaDataField) {
        this.categoryMetaDataField = categoryMetaDataField;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CategoryMetaDataFieldValue{" +
                "id=" + id +
                ", category=" + category +
                ", categoryMetaDataField=" + categoryMetaDataField +
                ", value='" + value + '\'' +
                '}';
    }
}
