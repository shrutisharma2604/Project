package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.dto.AddressDto;
import com.example.EcommerceApp.dto.CustomerProfileDto;
import com.example.EcommerceApp.dto.SellerAddressDto;
import com.example.EcommerceApp.dto.SellerProfileDto;
import com.example.EcommerceApp.services.SellerService;
import com.example.EcommerceApp.validation.PasswordValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/seller/home")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @Autowired
    private PasswordValidation passwordValidation;

    @GetMapping(path = "/getSellerDetails/{id}")
    public SellerProfileDto getCustomerProfile(@PathVariable("id") Long id){
        return sellerService.getSellerDetails(id);
    }
    @PutMapping(path = "/profile-update/{id}")
    public String updateProfile(@Valid @RequestBody SellerProfileDto profileDto, @PathVariable("id") Long id){
        return sellerService.updateSeller(profileDto,id);
    }
    @PatchMapping("/updatePassword/{id}")
    public String passwordUpdate(@PathVariable(value = "id") Long id,
                                 @RequestParam String oldPass,@RequestParam String newPass,@RequestParam String confirmPass, HttpServletResponse response) {
        if (passwordValidation.validPassword(oldPass, newPass, confirmPass)) {
            return sellerService.updatePassword(id, oldPass, newPass, confirmPass, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Password must be matched or password must be of minimum 8 characters and maximum 15 characters and must contain 1 uppercase letter,1 lowercase letter,1 digit and 1 special character";
        }
    }
    @PutMapping("/updateAddress/{id}")
    public String updateAddress(@PathVariable Long id, @RequestBody SellerAddressDto sellerAddressDto, HttpServletResponse response, HttpServletRequest request) {
        String getMessage = sellerService.updateAddress(id,sellerAddressDto,request);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }
}
