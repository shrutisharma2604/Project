package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.dto.AddressDto;
import com.example.EcommerceApp.dto.CustomerProfileDto;
import com.example.EcommerceApp.dto.SellerProfileDto;
import com.example.EcommerceApp.services.SellerService;
import com.example.EcommerceApp.validation.PasswordValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/seller/home")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @Autowired
    private PasswordValidation passwordValidation;

    @GetMapping(path = "/getSellerDetails/{user_id}")
    public SellerProfileDto getCustomerProfile(@PathVariable("user_id") Long user_id){
        return sellerService.getSellerDetails(user_id);
    }
    @PutMapping(path = "/profile-update/{user_id}")
    public String updateProfile(@Valid @RequestBody SellerProfileDto profileDto, @PathVariable("user_id") Long user_id){
        return sellerService.updateSeller(profileDto,user_id);
    }
    @PatchMapping("/updatePassword/{user_id}")
    public String passwordUpdate(@PathVariable(value = "user_id") Long user_id,
                                 @RequestParam String oldPass,@RequestParam String newPass,@RequestParam String confirmPass, HttpServletResponse response) {
        if (passwordValidation.validPassword(oldPass, newPass, confirmPass)) {
            return sellerService.updatePassword(user_id, oldPass, newPass, confirmPass, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Password must be matched or password must be of minimum 8 characters and maximum 15 characters and must contain 1 uppercase letter,1 lowercase letter,1 digit and 1 special character";
        }
    }
    @PutMapping(path = "/{user_id}/address/{addressId}")
    public String updateAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable(value = "addressId") Long addressId, @PathVariable(value = "user_id") Long user_id){
        return sellerService.updateAddress(addressDto,addressId,user_id);
    }
}
