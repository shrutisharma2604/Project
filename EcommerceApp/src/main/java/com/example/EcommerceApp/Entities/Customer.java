package com.example.EcommerceApp.Entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User {
    private String contact;
    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    private Cart cart;

   @OneToMany(mappedBy = "customer",cascade =CascadeType.ALL)
    private Set<Orders> orders;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Set<Orders> getOrders() {
        return orders;
    }

    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }
}
