package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.entities.ProductVariant;
import com.example.EcommerceApp.entities.Product_Variation;
import com.example.EcommerceApp.services.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/product/variant")
public class ProductVariantController {

    @Autowired
    private ProductVariantService productVariantService;

    public Product_Variation findVariant(@PathVariable("id") Long variantId) {
        return productVariantService.findVariant(variantId);
    }

    @GetMapping(path = "/save-variant/{id}")
    public void save(@PathVariable("id") Long id) {

        String message = productVariantService.saveVariant(id);
        System.out.println(message);

    }

    @GetMapping(path = "/view-variant/{vid}")
    public ProductVariant view(@PathVariable("vid") String vid) {
        return productVariantService.getVariant(vid);
    }

    @PutMapping(path = "/updateQuantity/{variantId}")
    public String update(@PathVariable("variantId") Long variantId, Integer qty){
        return productVariantService.updateStoredQuantity(variantId,qty);
    }
}
