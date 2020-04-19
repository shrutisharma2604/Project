package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.dto.*;
import com.example.EcommerceApp.entities.Product;
import com.example.EcommerceApp.services.CategoryService;
import com.example.EcommerceApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

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

    @DeleteMapping(path = "/{userId}/category/{categoryId}/product/{productId}")
    public String deleteProduct(@PathVariable(value = "userId") Long userId, @PathVariable(value = "productId") Long productId){
        return productService.deleteProduct(userId,productId);
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
    @GetMapping(path = "/product/{productId}")
    public List<ProductVariationGetDTO> getProduct(@PathVariable(value = "productId") Long productId) {
        return productService.getProductForUser(productId);
    }

    @GetMapping(path = "/category/{categoryId}/product")
    public AllProductDTO getProducts(@PathVariable(value = "categoryId") Long categoryId) {
        return productService.getAllProductsByCategoryId(categoryId);
    }

    @GetMapping(path = "/product/{productId}/")
    public List<Product> getSimilarProducts(@PathVariable(value = "productId") Long productId) {
        return productService.getSimilarProducts(productId);
    }

}
