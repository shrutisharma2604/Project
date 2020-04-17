package com.example.EcommerceApp.services;

import com.example.EcommerceApp.entities.Admin;
import com.example.EcommerceApp.entities.Customer;
import com.example.EcommerceApp.entities.Seller;
import com.example.EcommerceApp.entities.User;
import com.example.EcommerceApp.events.EmailNotificationService;
import com.example.EcommerceApp.repositories.AdminRepository;
import com.example.EcommerceApp.repositories.CustomerRepository;
import com.example.EcommerceApp.repositories.SellerRepository;
import com.example.EcommerceApp.repositories.UserRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SellerRepository  sellerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    private CustomerActivateService customerActivateService;

    public String activateCustomer(@PathVariable Long id, HttpServletResponse httpServletResponse) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "no user found with given id";
        }
        if (!user.get().isActive()) {
            user.get().setActive(true);
            userRepository.save(user.get());
            // trigger mail
            emailNotificationService.sendNotification("ACTIVATED", "HEY CUSTOMER YOUR ACCOUNT HAS BEEN ACTIVATED", user.get().getEmail());
            return "Success";
        }
        userRepository.save(user.get());
        System.out.println("already activated");
        return "Success";
    }

    public String deactivateCustomer(@PathVariable Long id, HttpServletResponse httpServletResponse) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "no user found with given id";
        }
        if (user.get().isActive()) {
            user.get().setActive(false);
            userRepository.save(user.get());
            // trigger mail
            emailNotificationService.sendNotification("DEACTIVATED", "HEY CUSTOMER YOUR ACCOUNT HAS BEEN DEACTIVATED", user.get().getEmail());
            return "Success";
        }
        userRepository.save(user.get());
        System.out.println("already deactivated");
        return "Success";
    }

    public String activateSeller(@PathVariable Long id, HttpServletResponse httpServletResponse) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "no user found with given id";
        }
        if (!user.get().isActive()) {
            user.get().setActive(true);
            userRepository.save(user.get());
            // trigger mail
            emailNotificationService.sendNotification("ACTIVATED", "HEY SELLER YOUR ACCOUNT HAS BEEN ACTIVATED", user.get().getEmail());
            return "Success";
        }
        userRepository.save(user.get());
        System.out.println("already activated");
        return "Success";
    }

    public String deactivateSeller(@PathVariable Long id, HttpServletResponse httpServletResponse) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "no user found with given id";
        }
        if (user.get().isActive()) {
            user.get().setActive(false);
            userRepository.save(user.get());
            // trigger mail
            emailNotificationService.sendNotification("DEACTIVATED", "HEY SELLER YOUR ACCOUNT HAS BEEN DEACTIVATED", user.get().getEmail());
            return "Success";
        }
        userRepository.save(user.get());
        System.out.println("already deactivated");
        return "Success";
    }

    public MappingJacksonValue registeredCustomers(String page,String size, String SortBy){
        List<Customer> customers = customerRepository.findAll(PageRequest.of(Integer.parseInt(page),Integer.parseInt(size), Sort.by(SortBy))).getContent();
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","firstName","lastName","email","active");
        FilterProvider filterProvider =  new SimpleFilterProvider().addFilter("CustomerFilter",filter);

        MappingJacksonValue message = new MappingJacksonValue(customers);

        message.setFilters(filterProvider);
        return message;
    }

    public MappingJacksonValue registeredSellers(String page,String size, String SortBy){
        List<Seller> sellers = sellerRepository.findAll(PageRequest.of(Integer.parseInt(page),Integer.parseInt(size), Sort.by(SortBy))).getContent();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","firstName","lastName","email","active","companyName","companyContact","addresses");
        FilterProvider filterProvider =  new SimpleFilterProvider().addFilter("Seller-Filter",filter);

        MappingJacksonValue message = new MappingJacksonValue(sellers);

        message.setFilters(filterProvider);
        return message;
    }
}
