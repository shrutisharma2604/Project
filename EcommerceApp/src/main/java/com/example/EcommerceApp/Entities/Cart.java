package com.example.EcommerceApp.Entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long quantity;
    private boolean isWishListItem;
    @OneToOne
    @JoinColumn(name = "user_id")
    private Customer customer;
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    private Set<Product_Variation> product_variations;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public boolean isWishListItem() {
        return isWishListItem;
    }

    public void setWishListItem(boolean wishListItem) {
        isWishListItem = wishListItem;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Product_Variation> getProduct_variations() {
        return product_variations;
    }

    public void setProduct_variations(Set<Product_Variation> product_variations) {
        this.product_variations = product_variations;
    }
}
