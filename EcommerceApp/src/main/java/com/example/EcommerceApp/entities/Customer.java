package com.example.EcommerceApp.entities;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFilter("CustomerFilter")
public class Customer extends User {
    private String contact;
    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "customer",cascade =CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Orders> orders;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Product> products;

    private String image;

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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "contact='" + contact + '\'' +
                ", cart=" + cart +
                ", orders=" + orders +
                ", products=" + products +
                '}';
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
