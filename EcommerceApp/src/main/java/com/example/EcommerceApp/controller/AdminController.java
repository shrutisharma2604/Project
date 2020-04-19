package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.dto.AllProductDTO;
import com.example.EcommerceApp.dto.CategoryDTO;
import com.example.EcommerceApp.dto.ProductVariationGetDTO;
import com.example.EcommerceApp.entities.CategoryMetaDataField;
import com.example.EcommerceApp.services.AdminService;
import com.example.EcommerceApp.services.CategoryService;
import com.example.EcommerceApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/admin/home")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProductService productService;

    @GetMapping(path = "/customers")
    public MappingJacksonValue getCustomers(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10") String size, @RequestParam(defaultValue = "id") String SortBy) {
        return adminService.registeredCustomers(page, size, SortBy);
    }

    @GetMapping(path = "/sellers")
    public MappingJacksonValue getSellers(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10") String size, @RequestParam(defaultValue = "id") String SortBy) {
        return adminService.registeredSellers(page, size, SortBy);
    }

    @PatchMapping(path = "/activate-Customer/{id}")
    public String activationOfCustomer(@PathVariable(value = "id") Long id, HttpServletResponse response) {

        String message = adminService.activateCustomer(id, response);
        if (!message.equals("Success")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PatchMapping(path = "/de-activateCustomer/{id}")
    public String deactivationOfCustomer(@PathVariable(value = "id") Long id, HttpServletResponse response) {
        String message = adminService.deactivateCustomer(id, response);
        if (!message.equals("Success")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PatchMapping(path = "/activate-Seller/{id}")
    public String activationOfSeller(@PathVariable(value = "id") Long id, HttpServletResponse response) {
        String message = adminService.activateSeller(id, response);
        if (!message.equals("Success")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PatchMapping(path = "/de-activateSeller/{id}")
    public String deactivationOfSeller(@PathVariable(value = "id") Long id, HttpServletResponse response) {
        String message = adminService.deactivateSeller(id, response);
        if (!message.equals("Success")) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return message;
    }
    @GetMapping(path = "/product/{productId}")
    public List<ProductVariationGetDTO> getProduct(@PathVariable(value = "productId") Long productId) {
        return productService.getProductForAdmin(productId);
    }

    @GetMapping(path = "/{categoryId}/product")
    public AllProductDTO getProducts(@PathVariable(value = "categoryId") Long categoryId) {
        return productService.getAllProductsByCategoryId(categoryId);
    }

    @PutMapping(path = "/product/{productId}/activate")
    public String activateProduct(@PathVariable(value = "productId") Long productId) {
        return productService.activateProduct(productId);
    }

    @PutMapping(path = "/product/{productId}/deActivate")
    public String deActivateProduct(@PathVariable(value = "productId") Long productId) {
        return productService.deActivateProduct(productId);
    }

}
