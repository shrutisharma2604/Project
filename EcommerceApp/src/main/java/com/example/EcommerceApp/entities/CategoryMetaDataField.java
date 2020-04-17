package com.example.EcommerceApp.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class CategoryMetaDataField {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "categoryMetaDataField")
    private Set<CategoryMetaDataFieldValue> categoryMetaDataFieldValueSet;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<CategoryMetaDataFieldValue> getCategoryMetaDataFieldValueSet() {
        return categoryMetaDataFieldValueSet;
    }

    public void setCategoryMetaDataFieldValueSet(Set<CategoryMetaDataFieldValue> categoryMetaDataFieldValueSet) {
        this.categoryMetaDataFieldValueSet = categoryMetaDataFieldValueSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
