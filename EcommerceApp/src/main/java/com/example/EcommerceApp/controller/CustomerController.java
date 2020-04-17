package com.example.EcommerceApp.controller;
import com.example.EcommerceApp.dto.AddressDto;
import com.example.EcommerceApp.dto.CustomerProfileDto;
import com.example.EcommerceApp.services.CustomerService;
import com.example.EcommerceApp.validation.PasswordValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/customer/home")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordValidation passwordValidation;

    @GetMapping(path = "/getCustomerDetails/{user_id}")
    public CustomerProfileDto getCustomerProfile(@PathVariable("user_id") Long user_id){
        return customerService.getCustomerDetails(user_id);
    }
    @GetMapping(path = "/getCustomerAddresses/{user_id}")
    public MappingJacksonValue getCustomerAddress(@PathVariable("user_id") Long user_id){
        return customerService.getCustomerAddresses(user_id);
    }

    @PutMapping(path = "profile-update/{user_id}")
    public String updateProfile(@Valid @RequestBody CustomerProfileDto profileDto, @PathVariable("user_id") Long user_id){
      return customerService.updateCustomer(profileDto,user_id);
    }
    @PatchMapping("/updatePassword/{user_id}")
    public String passwordUpdate(@PathVariable(value = "user_id") Long user_id,
                                 @RequestParam String oldPass,@RequestParam String newPass,@RequestParam String confirmPass, HttpServletResponse response) {
        if (passwordValidation.validPassword(oldPass, newPass, confirmPass)) {
            return customerService.updatePassword(user_id, oldPass, newPass, confirmPass, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Password must be matched or password must be of minimum 8 characters and maximum 15 characters and must contain 1 uppercase letter,1 lowercase letter,1 digit and 1 special character";
        }
    }
    @PostMapping(path = "/{user_id}/address")
    public String addAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable(value = "user_id") Long user_id){
        return customerService.addAddress(addressDto,user_id);
    }

    @DeleteMapping(path = "/delete/{addressId}")
    public String deleteAddress(@PathVariable(value = "addressId") Long addressId){
        return customerService.deleteAddress(addressId);
    }

    @PutMapping(path = "/{user_id}/address/{addressId}")
    public String updateAddress(@Valid @RequestBody AddressDto addressDto,@PathVariable(value = "addressId") Long addressId, @PathVariable(value = "user_id") Long user_id){
        return customerService.updateAddress(addressDto,addressId,user_id);
    }

}
