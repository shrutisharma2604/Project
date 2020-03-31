package com.example.EcommerceApp.Entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Seller extends User{
    private long gst;
    private long company_contact;
    private String company_name;
    @ManyToMany(mappedBy = "sellers")
    private Set<Product> products;

    public long getGst() {
        return gst;
    }

    public void setGst(long gst) {
        this.gst = gst;
    }

    public long getCompany_contact() {
        return company_contact;
    }

    public void setCompany_contact(long company_contact) {
        this.company_contact = company_contact;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
