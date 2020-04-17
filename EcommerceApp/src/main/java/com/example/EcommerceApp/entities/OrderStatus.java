package com.example.EcommerceApp.entities;

import javax.persistence.*;

@Entity
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    String fromStatus;
    String toStatus;
    @ManyToOne
    @JoinColumn(name="order_product_id")
    private OrderProduct orderProduct;

}
