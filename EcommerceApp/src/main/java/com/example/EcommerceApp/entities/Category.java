package com.example.EcommerceApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private Set<Product> products;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id",referencedColumnName = "id",nullable = true)
    private Category parentId;


    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<CategoryMetaDataFieldValue> categoryMetaDataFieldValues;

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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Category getParentId() {
        return parentId;
    }

    public void setParentId(Category parentId) {
        this.parentId = parentId;
    }

    public Set<CategoryMetaDataFieldValue> getCategoryMetaDataFieldValues() {
        return categoryMetaDataFieldValues;
    }

    public void setCategoryMetaDataFieldValues(Set<CategoryMetaDataFieldValue> categoryMetaDataFieldValues) {
        this.categoryMetaDataFieldValues = categoryMetaDataFieldValues;
    }
}
