package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.dto.AddressDTO;
import com.example.EcommerceApp.dto.CustomerProfileDTO;
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
    public CustomerProfileDTO getCustomerProfile(@PathVariable("id") Long id){
        return customerService.getCustomerDetails(id);
    }
    @GetMapping(path = "/getCustomerAddresses/{id}")
    public MappingJacksonValue getCustomerAddress(@PathVariable("id") Long id){
        return customerService.getCustomerAddresses(id);
    }

    @PutMapping(path = "profile-update/{id}")
    public String updateProfile(@Valid @RequestBody CustomerProfileDTO profileDto, @PathVariable("id") Long id){
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
    public String addAddress(@Valid @RequestBody AddressDTO addressDto, @PathVariable(value = "id") Long id){
        return customerService.addAddress(addressDto,id);
    }

    @DeleteMapping("/address/{id}")
    public String deleteAddress(@PathVariable Long id,HttpServletResponse response,HttpServletRequest request) {
        String getMessage = customerService.deleteAddress(id,request);
        if ("Address Deleted".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @PutMapping(path = "/{userId}/address/{id}")
    public String updateAddress(@Valid @RequestBody AddressDTO addressDto, @PathVariable(value = "id") Long id, @PathVariable(value = "userId") Long userId){
        return customerService.updateAddress(addressDto,id,userId);
    }

}
