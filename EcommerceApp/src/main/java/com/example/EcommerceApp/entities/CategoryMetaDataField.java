package com.example.EcommerceApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Set;

@Entity
public class CategoryMetaDataField {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "categoryMetaDataField",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<CategoryMetaDataFieldValue> categoryMetaDataFieldValueSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Set<CategoryMetaDataFieldValue> getCategoryMetaDataFieldValueSet() {
        return categoryMetaDataFieldValueSet;
    }

    public void setCategoryMetaDataFieldValueSet(Set<CategoryMetaDataFieldValue> categoryMetaDataFieldValueSet) {
        this.categoryMetaDataFieldValueSet = categoryMetaDataFieldValueSet;
    }
}
