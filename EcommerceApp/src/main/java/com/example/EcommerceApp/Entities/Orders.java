package com.example.EcommerceApp.Entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Double amountPaid;
    private Date dateCreated;
    @ManyToOne
    @JoinColumn(name = "customer_user_id")
    private Customer customer;

   /* @ManyToMany(mappedBy = "orders",cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name="customer_address_city", referencedColumnName="city"),
            @JoinColumn(name="customer_address_state", referencedColumnName="state"),
            @JoinColumn(name="customer_address_country", referencedColumnName="country"),
            @JoinColumn(name="customer_address_address", referencedColumnName="address"),
            @JoinColumn(name="customer_address_zipCode", referencedColumnName="zipCode"),
            @JoinColumn(name="customer_address_label", referencedColumnName="label")
    })
    private Set<Address> address;*/


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
}
