package com.example.EcommerceApp.services;

import com.example.EcommerceApp.EcommerceAppApplication;
import com.example.EcommerceApp.config.EmailNotificationService;
import com.example.EcommerceApp.dto.CustomerDTO;
import com.example.EcommerceApp.dto.SellerDTO;
import com.example.EcommerceApp.entities.*;
import com.example.EcommerceApp.repositories.*;
import com.example.EcommerceApp.validation.GstValidation;
import com.example.EcommerceApp.validation.PasswordValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmailNotificationService emailNotificationService;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private CustomerActivateRepo customerActivateRepo;

    @Autowired
    PasswordValidation passwordValidation;

    @Autowired
    GstValidation gstValidation;

    @Autowired
    private AddressRepository addressRepository;

    private static final Logger LOGGER= LoggerFactory.getLogger(EcommerceAppApplication.class);
    @Transactional
    public String registerCustomer(CustomerDTO customerDto) {

        User user = userRepository.findByEmail(customerDto.getEmail());
        try {
            if (user.getEmail().equals(customerDto.getEmail())) {
                LOGGER.error("Email already exists");
            }
            if (customerDto.getPassword().equals(customerDto.getConfirmPassword())) {
                LOGGER.error("Password does not match");
            }
        } catch (NullPointerException ex) {

        }
        boolean isValidPassword = passwordValidation.isValid(customerDto.getPassword());
        if (!isValidPassword) {
            LOGGER.error("password is invalid");
        }
        customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));


        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        Role role = new Role();
        role.setAuthority("ROLE_CUSTOMER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        customer.setRoles(roleSet);
        customer.setLocked(false);
        customer.setExpired(false);

        userRepository.save(customer);

        String token = UUID.randomUUID().toString();

        CustomerActivate customerActivate = new CustomerActivate();
        customerActivate.setToken(token);
        customerActivate.setUserEmail(customer.getEmail());
        customerActivate.setExpiryDate(new Date());

        customerActivateRepo.save(customerActivate);
        String email = customer.getEmail();

        emailNotificationService.sendNotification("ACCOUNT ACTIVATE TOKEN", "To confirm your account, please click here : "
                + "http://localhost:8080/register/confirm-account?token=" + token, email);

        return "Registered Successfully";
    }

    @Transactional
    public String registerSeller(SellerDTO sellerDto) {
        boolean isValidGst = gstValidation.validateGst(sellerDto.getGST());
        if (!isValidGst) {
            return "gst is not valid";
        }
        User user = userRepository.findByEmail(sellerDto.getEmail());
        try {
            if (user.getEmail().equals(sellerDto.getEmail())) {
                return "Email already exists";
            }
            if (sellerDto.getPassword().equals(sellerDto.getConfirmPassword())) {
                return "Password does not match";
            }
        } catch (NullPointerException ex) {

        }
        Seller user1 = sellerRepository.findByCompanyName(sellerDto.getCompanyName());
        try {
            if (user1.getCompanyName().equalsIgnoreCase(sellerDto.getCompanyName())) {
                return "company name should be unique";
            }
        } catch (NullPointerException ex) {

        }
        List<Seller> user2 = sellerRepository.findByGST(sellerDto.getGST());
        boolean flag = false;
        for (Seller seller1 : user2) {
            if (seller1.getGST().equals(sellerDto.getGST())) {
                flag = true;
                break;
            }
        }
        try {
            if (flag == true) {
                return "gst should be unique";
            }
        } catch (NullPointerException ex) {

        }
        boolean isValidPassword = passwordValidation.isValid(sellerDto.getPassword());
        if (!isValidPassword) {
            return "password is invalid";
        }
        sellerDto.setPassword(passwordEncoder.encode(sellerDto.getPassword()));
        if (sellerDto.getCompanyContact().length() != 10) {
            return "invalid contact";
        }
        if(sellerDto.getAddresses().size() != 1) {
            return "Seller does not have multiple addresses";
        }

        Seller seller = new Seller();
        BeanUtils.copyProperties(sellerDto, seller);
        Role role = new Role();
        role.setAuthority("ROLE_SELLER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        seller.setRoles(roleSet);
        seller.setActive(true);
        seller.setLocked(false);
        seller.setExpired(false);

        CustomerActivate customerActivate = new CustomerActivate();
        customerActivate.setUserEmail(seller.getEmail());

        customerActivateRepo.save(customerActivate);
        String email = seller.getEmail();

        emailNotificationService.sendNotification("ACCOUNT CREATED ", "Your account has been created", email);

        userRepository.save(seller);
        return "Registered Successfully";
    }

}