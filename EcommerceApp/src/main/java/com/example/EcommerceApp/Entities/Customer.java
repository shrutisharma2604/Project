package com.example.EcommerceApp.Entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User {
    private Number contact;
    @OneToOne(mappedBy = "cart",cascade = CascadeType.ALL)
    private Cart cart;
    @Embedded
    @OneToMany(mappedBy = "address",cascade = CascadeType.ALL)
    private Set<Address> address;

    @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL)
    private Set<Order> orders;

    public Number getContact() {
        return contact;
    }

    public void setContact(Number contact) {
        this.contact = contact;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Set<Address> getAddress() {
        return address;
    }

    public void setAddress(Set<Address> address) {
        this.address = address;
    }
}
