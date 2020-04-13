package com.example.EcommerceApp.Entities;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    private String brand;

    private boolean isReturnable;
    private boolean isCancellable;
    private boolean isActive;
    private boolean isDeleted;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "seller_user_id")
    private Seller seller;

    @OneToMany(mappedBy = "product" ,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Product_Variation> product_variations;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<Review> reviews;

    public Product(){

    }
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

    public boolean isReturnable() {
        return isReturnable;
    }

    public void setReturnable(boolean returnable) {
        isReturnable = returnable;
    }

    public boolean isCancellable() {
        return isCancellable;
    }

    public void setCancellable(boolean cancellable) {
        isCancellable = cancellable;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Set<Product_Variation> getProduct_variations() {
        return product_variations;
    }

    public void setProduct_variations(Set<Product_Variation> product_variations) {
        this.product_variations = product_variations;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", isReturnable=" + isReturnable +
                ", isCancellable=" + isCancellable +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", seller=" + seller +
                ", product_variations=" + product_variations +
                ", category=" + category +
                ", reviews=" + reviews +
                '}';
    }
}
