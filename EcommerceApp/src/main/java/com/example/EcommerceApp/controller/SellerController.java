package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.dto.*;
import com.example.EcommerceApp.services.CategoryService;
import com.example.EcommerceApp.services.SellerService;
import com.example.EcommerceApp.validation.PasswordValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/seller/home")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @Autowired
    private PasswordValidation passwordValidation;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(path = "/getSellerDetails/{id}")
    public SellerProfileDTO getSellerProfile(@PathVariable("id") Long id){
        return sellerService.getSellerDetails(id);
    }
    @PutMapping(path = "/profile-update/{id}")
    public String updateProfile(@Valid @RequestBody SellerProfileDTO profileDto, @PathVariable("id") Long id){
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
    @PutMapping(path = "/{userId}/address/{addressId}")
    public String updateAddress(@Valid @RequestBody AddressDTO addressDto, @PathVariable(value = "addressId") Long addressId, @PathVariable Long userId) {
        return sellerService.updateAddress(addressDto, addressId, userId);
    }


    //category
    @GetMapping("/categories")
    public List<CategoryDTO> viewLeafCategories() {
        return categoryService.viewLeafCategories();
    }
}
