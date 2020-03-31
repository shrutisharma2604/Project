package com.example.EcommerceApp.Entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String brand;
    private boolean isActive;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "product_seller",joinColumns = @JoinColumn(name = "product_id",referencedColumnName = "id")
            ,inverseJoinColumns = @JoinColumn(name ="seller_id",referencedColumnName = "user_id"))
    private Set<Seller> sellers;

    @OneToMany(mappedBy = "product" ,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Product_Variation> product_variations;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Category> categories;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Set<Seller> getSellers() {
        return sellers;
    }

    public void setSellers(Set<Seller> sellers) {
        this.sellers = sellers;
    }

    public Set<Product_Variation> getProduct_variations() {
        return product_variations;
    }

    public void setProduct_variations(Set<Product_Variation> product_variations) {
        this.product_variations = product_variations;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
