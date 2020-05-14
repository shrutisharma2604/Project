package com.example.EcommerceApp.dto;

import com.example.EcommerceApp.entities.Address;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Set;

public class SellerDTO {
    @NotEmpty(message = "first name must be entered")
    private String firstName;

    private String lastName;

    @Email(message = "Please provide a valid e-mail")
    @NotEmpty(message = "Please provide an e-mail")
    private String email;
    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%])(?=.*[A-Z]).{8,15}",message = "Password must be of minimum 8 characters and maximum 15 " +
            "characters and must contain 1 uppercase letter,1 lowercase letter,1 digit and 1 special character")
    private String password;

    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%])(?=.*[A-Z]).{8,15}",message = "Password must be of minimum 8 characters and maximum 15 " +
            "characters and must contain 1 uppercase letter,1 lowercase letter,1 digit and 1 special character")
    private String confirmPassword;
    @NotEmpty(message = "Must provide gst number")
    @Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$",message = "The first 2 digits denote the State Code (01-37) as defined in the Code List for Land Regions.\n" +
            "\n" +
            "The next 10 characters pertain to PAN Number in AAAAA9999X format.\n" +
            "\n" +
            "13th character indicates the number of registrations an entity has within a state for the same PAN.\n" +
            "\n" +
            "14th character is currently defaulted to \"Z\"\n" +
            "\n" +
            "15th character is a checksum digit")
    private String GST;
    @NotEmpty(message = "Please provide your company name")
    private String companyName;
    private Set<Address> addresses;
    @NotEmpty(message = "Must provide company contact number")
    @Pattern(regexp="\\d{10}", message="Mobile number is invalid")
    private String companyContact;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
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

    @Override
    public String toString() {
        return "SellerDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", GST='" + GST + '\'' +
                ", companyName='" + companyName + '\'' +
                ", addresses=" + addresses +
                ", companyContact='" + companyContact + '\'' +
                '}';
    }
}
