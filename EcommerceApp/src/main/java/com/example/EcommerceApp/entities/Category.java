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

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory",cascade = CascadeType.ALL)
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

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
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
    /*  public void addSubCategories(Category category){
        if(category!=null){
            if(subCategories==null){
                subCategories=new HashSet<>();
            }
            subCategories.add(category);
            category.setParentCategory(this);
        }
    }
    public void addProducts(Product product){
        if(product!=null){
            if(products==null){
                products=new HashSet<>();
            }
            products.add(product);
            product.setCategory(this);
        }
    }*/


}
