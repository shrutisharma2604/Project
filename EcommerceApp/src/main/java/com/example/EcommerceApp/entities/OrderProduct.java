package com.example.EcommerceApp.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Integer quantity;
    private Double price;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;
    @ManyToOne
    @JoinColumn(name = "product_variation_id")
    private Product_Variation product_variation;

    @OneToMany(mappedBy = "orderProduct",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<OrderStatus> status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Product_Variation getProduct_variation() {
        return product_variation;
    }

    public void setProduct_variation(Product_Variation product_variation) {
        this.product_variation = product_variation;
    }

    public Set<OrderStatus> getStatus() {
        return status;
    }

    public void setStatus(Set<OrderStatus> status) {
        this.status = status;
    }
}
