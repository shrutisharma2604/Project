package com.example.EcommerceApp.Entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Cart {
    private long quantity;
    private boolean isWishListItem;
    @OneToOne
    @JoinColumn(name = "user_id")
    private Customer customer;

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
}
