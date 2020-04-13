package com.example.EcommerceApp.Controller;

import com.example.EcommerceApp.Dto.CustomerDto;
import com.example.EcommerceApp.Dto.SellerDto;
import com.example.EcommerceApp.Entities.Admin;
import com.example.EcommerceApp.Entities.User;
import com.example.EcommerceApp.Events.EmailNotificationService;
import com.example.EcommerceApp.Security.AppUserDetailsService;
import com.example.EcommerceApp.Services.CustomerActivateService;
import com.example.EcommerceApp.Services.CustomerService;
import com.example.EcommerceApp.Services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/register")
public class RegistrationController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private EmailNotificationService emailNotificationService;
    @Autowired
    private AppUserDetailsService appUserDetailsService;
    @Autowired
    private CustomerActivateService customerActivateService;

    @PostMapping(path = "/customer")
    public String registerCustomer(@Valid @RequestBody CustomerDto customerDto, HttpServletResponse response){
        if(customerService.validateCustomer(customerDto).equals("validated")) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            return appUserDetailsService.registerCustomer(customerDto);

        }else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return customerService.validateCustomer(customerDto);
        }
    }

    @PostMapping(path = "/seller")
    public Object registerSeller(@Valid @RequestBody SellerDto sellerDto, HttpServletResponse response){
        if(sellerService.validateSeller(sellerDto).equals("validated")) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            return appUserDetailsService.registerSeller(sellerDto);
        }else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return sellerService.validateSeller(sellerDto);
        }
    }
    @PutMapping(path = "/confirm-account")
    public String confirmCustomerAccount(@RequestParam("token") String token, HttpServletResponse response){
        String message = customerActivateService.activateCustomer(token);
        if(!message.equals("Success")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PostMapping(path = "/resend-activation")
    public String resendLink(@RequestParam("email") String email,HttpServletResponse response){
        String message = customerActivateService.resendLink(email);
        if(!message.equals("Success")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PostMapping(path = "/admin")
    public User registerAdmin(@Valid @RequestBody Admin admin){

        User user = appUserDetailsService.registerAdmin(admin);

        return user;
    }

}
