package com.example.EcommerceApp.entities;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Double amountPaid;
    @Temporal(TemporalType.DATE)
    private Date dateCreated;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_user_id")
    private Customer customer;

   @Embedded
   private OrderAddress orderAddress;

   @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
   private Set<OrderProduct> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OrderAddress getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(OrderAddress orderAddress) {
        this.orderAddress = orderAddress;
    }

    public Set<OrderProduct> getItems() {
        return items;
    }

    public void setItems(Set<OrderProduct> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", amountPaid=" + amountPaid +
                ", dateCreated=" + dateCreated +
                ", customer=" + customer +
                ", orderAddress=" + orderAddress +
                ", items=" + items +
                '}';
    }
}
