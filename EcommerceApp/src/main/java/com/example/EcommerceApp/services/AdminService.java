package com.example.EcommerceApp.services;

import com.example.EcommerceApp.config.EmailNotificationService;
import com.example.EcommerceApp.entities.Customer;
import com.example.EcommerceApp.entities.Seller;
import com.example.EcommerceApp.entities.User;
import com.example.EcommerceApp.exception.UserNotFoundException;
import com.example.EcommerceApp.repositories.CustomerRepository;
import com.example.EcommerceApp.repositories.SellerRepository;
import com.example.EcommerceApp.repositories.UserRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
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

    Logger logger = LoggerFactory.getLogger(AdminService.class);
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
        logger.info("already activated");
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
        logger.info("already deactivated");
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
        logger.info("already activated");
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
        logger.info("already deactivated");
        return "Success";
    }
    public String unlockUser(String email){
        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new UserNotFoundException("User not found");
        }
        emailNotificationService.sendNotification("Unlock Account","http://localhost:8080/register/unlock-account?email="+email,user.getEmail());
        logger.info("mail send to all the registered user");
        return "Mail sent to locked user";
    }

    @Transactional
    @Modifying
    public String unlockSuccessMessage(String email){
        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new UserNotFoundException("User not found");
        }
        user.setLocked(false);
        emailNotificationService.sendNotification("Unlock Account Success Message","Your account has been unlocked",user.getEmail());
        return "Account Unlocked Successfully";
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
