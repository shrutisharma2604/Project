package com.example.EcommerceApp.entities;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFilter("Seller-Filter")
public class Seller extends User{
    private String GST;
    private String companyContact;
    private String companyName;
    @OneToMany(mappedBy = "seller",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Product> products;
    private String image;

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "GST='" + GST + '\'' +
                ", companyContact='" + companyContact + '\'' +
                ", companyName='" + companyName + '\'' +
                ", products=" + products +
                '}';
    }
}
