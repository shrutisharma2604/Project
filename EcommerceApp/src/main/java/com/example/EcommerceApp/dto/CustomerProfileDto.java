package com.example.EcommerceApp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class CustomerProfileDto{
    private Long id;
    @NotEmpty(message = "Please provide your first name")
    private String firstName;
    @NotEmpty(message = "Please provide your last name")
    private String lastName;
    @Email
    @NotEmpty(message = "Please provide valid email")
    private String email;
    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%])(?=.*[A-Z]).{8,15}",message = "Password must be of minimum 8 characters and maximum 15 " +
            "characters and must contain 1 uppercase letter,1 lowercase letter,1 digit and 1 special character")
    private String password;
    private Boolean isActive;
    private String image;
    @NotEmpty(message = "Enter your mobile number")
    @Pattern(regexp="\\d{10}", message="Mobile number is invalid")
    private String contact;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
