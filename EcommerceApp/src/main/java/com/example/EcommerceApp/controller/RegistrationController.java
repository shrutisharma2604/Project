package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.dto.CustomerDto;
import com.example.EcommerceApp.dto.SellerDto;
import com.example.EcommerceApp.entities.Admin;
import com.example.EcommerceApp.entities.User;
import com.example.EcommerceApp.events.EmailNotificationService;
import com.example.EcommerceApp.security.AppUserDetailsService;
import com.example.EcommerceApp.services.CustomerActivateService;
import com.example.EcommerceApp.services.CustomerService;
import com.example.EcommerceApp.services.RegisterService;
import com.example.EcommerceApp.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/register")
public class RegistrationController {

    @Autowired
    private EmailNotificationService emailNotificationService;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private CustomerActivateService customerActivateService;

    @PostMapping(path = "/customer")
    public String registerCustomer(@Valid @RequestBody CustomerDto customerDto, HttpServletResponse httpServletResponse){
      String message=registerService.registerCustomer(customerDto);
        System.out.println(message + "Customer");
        // content equals
        if ("Success".equals(message)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PostMapping(path = "/seller")
    public String registerSeller(@Valid @RequestBody SellerDto sellerDto,HttpServletResponse httpServletResponse){
      String message=registerService.registerSeller(sellerDto);
        System.out.println(message + "Seller");
        if("Success".equals(message)){
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
        }
        else{
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }
    @PutMapping(path = "/confirm-account")
    public String confirmCustomerAccount(@RequestParam("token") String token, HttpServletResponse httpServletResponse){
        String message = customerActivateService.activateCustomer(token);
        if(!message.equals("Success")){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PostMapping(path = "/resend-activation")
    public String resendLink(@RequestParam("email") String email,HttpServletResponse httpServletResponse) {
        String message = customerActivateService.resendLink(email);
        if (!message.equals("Success")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }
}
