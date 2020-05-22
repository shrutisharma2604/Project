package com.example.EcommerceApp.services;

import com.example.EcommerceApp.EcommerceAppApplication;
import com.example.EcommerceApp.config.EmailNotificationService;
import com.example.EcommerceApp.dto.CustomerDTO;
import com.example.EcommerceApp.dto.SellerDTO;
import com.example.EcommerceApp.entities.*;
import com.example.EcommerceApp.repositories.*;
import com.example.EcommerceApp.validation.EmailValidation;
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
    private EmailNotificationService emailNotificationService;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private CustomerActivateRepo customerActivateRepo;

    @Autowired
    PasswordValidation passwordValidation;

    @Autowired
    GstValidation gstValidation;

    @Autowired
    EmailValidation emailValidation;

    @Autowired
    private AddressRepository addressRepository;

    private static final Logger LOGGER= LoggerFactory.getLogger(EcommerceAppApplication.class);
    @Transactional
    public String registerCustomer(CustomerDTO customerDto) {

        User user = userRepository.findByEmail(customerDto.getEmail());
        if(!emailValidation.validateEmail(customerDto.getEmail())){
            LOGGER.error("Invalid email");
        }
        if (userRepository.findByEmail(customerDto.getEmail())!=null) {
                LOGGER.error("Email already exists");
        }
        if (customerDto.getContact().length() != 10) {
            LOGGER.error("contact is invalid");
        }
        if (!passwordValidation.isValid(customerDto.getPassword())) {
                LOGGER.error("Password is invalid");
        }
        if (!customerDto.getPassword().equals(customerDto.getConfirmPassword())) {
            return "Password does not match";
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
        if (!gstValidation.validateGst(sellerDto.getGST())) {
            LOGGER.error("gst is invalid");
        }
        if (!emailValidation.validateEmail(sellerDto.getEmail())) {
           LOGGER.error("email is invalid");
        }
        if (userRepository.findByEmail(sellerDto.getEmail()) != null) {
            LOGGER.error("email already exist");
        }
        if (sellerRepository.findByCompanyName(sellerDto.getCompanyName()) != null) {
            LOGGER.error("company name should be unique");
        }
        if (sellerRepository.findByGST(sellerDto.getGST()) != null) {
            LOGGER.error("gst should be unique");
        }
        if (!passwordValidation.isValid(sellerDto.getPassword())) {
            LOGGER.error("password in invalid");
        }
        if (sellerDto.getCompanyContact().length() != 10) {
            LOGGER.error("contact is invalid");
        }
        if(sellerDto.getAddresses().size() != 1) {
            return "Seller does not have multiple addresses";
        }
        if (!sellerDto.getPassword().equals(sellerDto.getConfirmPassword())) {
            return "Password does not match";
        }
        sellerDto.setPassword(passwordEncoder.encode(sellerDto.getPassword()));
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
        seller.setAddresses(sellerDto.getAddresses());
       /* Address address=new Address();
        address.setUser(seller);
        addressRepository.save(address);*/
        Set<Address> addresses = seller.getAddresses();
        addresses.forEach(address -> {
            Address addressSave = address;
            addressSave.setUser(seller);
        });

        CustomerActivate customerActivate = new CustomerActivate();
        customerActivate.setUserEmail(seller.getEmail());

        customerActivateRepo.save(customerActivate);
        String email = seller.getEmail();

        emailNotificationService.sendNotification("ACCOUNT CREATED ", "Your account has been created", email);

        userRepository.save(seller);
        return "Registered Successfully";
    }

}