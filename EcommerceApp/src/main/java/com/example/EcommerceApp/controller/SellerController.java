package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.dto.*;
import com.example.EcommerceApp.entities.Category;
import com.example.EcommerceApp.services.CategoryService;
import com.example.EcommerceApp.services.ProductService;
import com.example.EcommerceApp.services.SellerService;
import com.example.EcommerceApp.validation.PasswordValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "/seller/home")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @Autowired
    private PasswordValidation passwordValidation;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping(path = "/getSellerDetails/{id}")
    public SellerProfileDTO getSellerProfile(@PathVariable("id") Long id){
        return sellerService.getSellerDetails(id);
    }
    @PutMapping(path = "/profile-update/{id}")
    public String updateProfile(@Valid @RequestBody SellerProfileDTO profileDto, @PathVariable("id") Long id){
        return sellerService.updateSeller(profileDto,id);
    }
    @PatchMapping(path = "/updatePassword/{id}")
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

    // product api
    @PostMapping(path = "/{userId}/{categoryId}")
    public String addProduct(@PathVariable(value = "userId") Long userId, @PathVariable(value = "categoryId") Long categoryId, @Valid @RequestBody ProductDTO productDto){
        return productService.addProduct(userId,categoryId,productDto);
    }

    @PostMapping(path = "/product/{productId}")
    public String addVariation(@PathVariable(value = "productId") Long productId, @RequestBody ProductVariationDTO productVariationDto){
        return productService.addProductVariation(productId, productVariationDto);
    }

    @GetMapping(path = "{userId}/{categoryId}/{productId}")
    public ProductViewDTO viewProduct(@PathVariable(value = "userId") Long userId, @PathVariable(value = "productId") Long productId){
        return productService.getProduct(userId, productId);
    }

    @GetMapping(path = "/{userId}/{categoryId}/{productId}/{variationId}")
    public ProductVariationGetDTO getProductVariation(@PathVariable(value = "userId") Long userId, @PathVariable(value ="variationId") Long variationId){
        return productService.getProductVariation(userId, variationId);
    }

    @GetMapping(path = "/{userId}/category/{categoryId}/product/{productId1}")
    public List<ProductVariationGetDTO> getProductVariations(@PathVariable(value = "userId") Long userId, @PathVariable(value ="productId1") Long productId1){
        return productService.getProductVariations(userId, productId1);
    }

    @GetMapping(path = "/{userId}/category/{categoryId}/product")
    public Set<ProductViewDTO> viewProducts(@PathVariable(value = "userId") Long userId){
        return productService.getProducts(userId);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long id){
        return productService.deleteProduct(id);
    }

    @PutMapping(path = "{userId}/category/{categoryId}/product/{productId}")
    public String updateProduct(@PathVariable(value = "userId") Long userId ,@PathVariable(value = "productId") Long productId, @RequestBody ProductViewDTO productViewDto){
        return productService.updateProduct(userId, productId, productViewDto);
    }

    @PutMapping(path = "/{userId}/category/{categoryId}/product/{productId}/name")
    public String updateProductName(@PathVariable(value = "userId") Long userId,@PathVariable(value = "categoryId") Long categoryId,@PathVariable(value = "productId") Long productId, @RequestBody ProductViewDTO productViewDto){
        return productService.updateProductName(userId, categoryId, productId, productViewDto);
    }

    @PutMapping(path = "/{userId}/category/{categoryId}/product/{productId}/name/variation/{variationId}")
    public String updateProductVariation(@PathVariable(value = "userId") Long userId, @PathVariable(value = "variationId") Long variationId,@RequestBody ProductVariationDTO productVariationDto){
        return productService.updateProductVariation(userId,variationId,productVariationDto);
    }

    //category api
    @GetMapping("/categories")
    public List<Category> viewLeafCategories(@RequestParam Optional<Long> categoryId) {
        return categoryService.viewCategoriesSameParent(categoryId);
    }

    @GetMapping(path = "/hello-world-internationalization")
    public String sellerHome(){
        return messageSource.getMessage("good.morning.messages",null, LocaleContextHolder.getLocale());
    }
}
