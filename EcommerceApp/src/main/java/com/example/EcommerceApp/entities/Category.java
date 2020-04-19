package com.example.EcommerceApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Product> products;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Category parentId;

    @OneToMany(mappedBy = "parentId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Category> subCategories;

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

    public Set<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public Set<CategoryMetaDataFieldValue> getCategoryMetaDataFieldValues() {
        return categoryMetaDataFieldValues;
    }

    public void setCategoryMetaDataFieldValues(Set<CategoryMetaDataFieldValue> categoryMetaDataFieldValues) {
        this.categoryMetaDataFieldValues = categoryMetaDataFieldValues;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", products=" + products +
                ", parentId=" + parentId +
                ", subCategories=" + subCategories +
                ", categoryMetaDataFieldValues=" + categoryMetaDataFieldValues +
                '}';
    }
}
