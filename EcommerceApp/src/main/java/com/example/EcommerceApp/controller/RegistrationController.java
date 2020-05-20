package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.dto.CustomerDTO;
import com.example.EcommerceApp.dto.SellerDTO;
import com.example.EcommerceApp.config.EmailNotificationService;
import com.example.EcommerceApp.services.CustomerActivateService;
import com.example.EcommerceApp.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/register")
public class RegistrationController {

    @Autowired
    EmailNotificationService emailNotificationService;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private CustomerActivateService customerActivateService;

    @PostMapping(path = "/customer")
    public String registerCustomer(@Valid @RequestBody CustomerDTO customerDto, HttpServletResponse httpServletResponse){
      String message=registerService.registerCustomer(customerDto);
        System.out.println(message + "Customer");
        // content equals
        if ("Registered Successfully".equals(message)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PostMapping(path = "/seller")
    public String registerSeller(@Valid @RequestBody SellerDTO sellerDto, HttpServletResponse httpServletResponse){
      String message=registerService.registerSeller(sellerDto);
        System.out.println(message + "Seller");
        if("Registered Successfully".equals(message)){
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
