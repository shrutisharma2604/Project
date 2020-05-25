package com.example.EcommerceApp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AddressDTO {
    private Long id;
    @NotEmpty(message = "Please enter your city")
    private String city;
    @NotEmpty(message = "Please enter your state")
    private String state;
    @NotEmpty(message = "Please enter your country")
    private String country;
    private String address;
    private String label;
    @Size(max = 6)
    private Integer zipCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                ", id=" + id +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", label='" + label + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }
}
