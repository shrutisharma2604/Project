package com.example.EcommerceApp.dto;

import com.example.EcommerceApp.entities.Address;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Set;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFilter("CustomerDTOFilter")
public class CustomerDTO {
    @Email
    @NotEmpty(message = "Please provide valid email")
    private String email;
    @NotEmpty(message = "Enter your mobile number")
    @Pattern(regexp="\\d{10}", message="Mobile number is invalid")
    private String contact;
    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%])(?=.*[A-Z]).{8,15}",message = "Password must be of minimum 8 characters and maximum 15 " +
            "characters and must contain 1 uppercase letter,1 lowercase letter,1 digit and 1 special character")
    private String password;
    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%])(?=.*[A-Z]).{8,15}",message = "Password must be of minimum 8 characters and maximum 15 " +
            "characters and must contain 1 uppercase letter,1 lowercase letter,1 digit and 1 special character")
    private String confirmPassword;
    @NotEmpty(message = "Please provide your name")
    private String firstName;
    @NotEmpty(message = "Please provide your last name")
    private String lastName;

    private Set<Address> addresses;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
