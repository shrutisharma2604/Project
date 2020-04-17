package com.example.EcommerceApp.controller;
import com.example.EcommerceApp.dto.AddressDto;
import com.example.EcommerceApp.dto.CustomerProfileDto;
import com.example.EcommerceApp.services.CustomerService;
import com.example.EcommerceApp.validation.PasswordValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/customer/home")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordValidation passwordValidation;

    @GetMapping(path = "/getCustomerDetails/{id}")
    public CustomerProfileDto getCustomerProfile(@PathVariable("id") Long id){
        return customerService.getCustomerDetails(id);
    }
    @GetMapping(path = "/getCustomerAddresses/{id}")
    public MappingJacksonValue getCustomerAddress(@PathVariable("id") Long id){
        return customerService.getCustomerAddresses(id);
    }

    @PutMapping(path = "profile-update/{id}")
    public String updateProfile(@Valid @RequestBody CustomerProfileDto profileDto, @PathVariable("id") Long id){
      return customerService.updateCustomer(profileDto,id);
    }
    @PatchMapping("/updatePassword/{id}")
    public String passwordUpdate(@PathVariable(value = "id") Long id,
                                 @RequestParam String oldPass,@RequestParam String newPass,@RequestParam String confirmPass, HttpServletResponse response) {
        if (passwordValidation.validPassword(oldPass, newPass, confirmPass)) {
            return customerService.updatePassword(id, oldPass, newPass, confirmPass, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Password must be matched or password must be of minimum 8 characters and maximum 15 characters and must contain 1 uppercase letter,1 lowercase letter,1 digit and 1 special character";
        }
    }
    @PostMapping(path = "/{id}/address")
    public String addAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable(value = "id") Long id){
        return customerService.addAddress(addressDto,id);
    }

    @DeleteMapping("/address/{id}")
    public String deleteAddress(@PathVariable Long id,HttpServletResponse response,HttpServletRequest request) {
        String getMessage = customerService.deleteAddress(id,request);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @PutMapping("/updateAddress/{id}")
    public String updateAddress(@PathVariable Long id, @RequestBody AddressDto addressDto, HttpServletResponse response, HttpServletRequest request) {
        String getMessage = customerService.updateAddress(id,addressDto,request);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

}
