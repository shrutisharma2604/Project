package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.dto.*;
import com.example.EcommerceApp.entities.CategoryMetaDataField;
import com.example.EcommerceApp.entities.ProductVariant;
import com.example.EcommerceApp.services.AdminService;
import com.example.EcommerceApp.services.CategoryService;
import com.example.EcommerceApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.Cacheable;

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

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CategoryService categoryService;

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

    //product api
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

    @GetMapping(path = "/hello-world-internationalization")
    public String helloWorldInternationalization(){
        return messageSource.getMessage("good.morning.messages",null, LocaleContextHolder.getLocale());
    }

    //category api
    @PostMapping("/add")
    public String addMetadata(@RequestParam String fieldName, HttpServletResponse response) {
        String getMessage = categoryService.addMetadata(fieldName);
        if (getMessage.contains("Success")) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @GetMapping("/viewMetaData")
    public List<CategoryMetaDataField> viewMetadata(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10") String size, @RequestParam(defaultValue = "id") String SortBy, @RequestParam(defaultValue = "ASC") String order, @RequestParam Optional<String> query) {
        return categoryService.viewMetadata(page,size,SortBy,order,query);
    }
    @PostMapping("/addCategory")
    public String addCategory(@RequestParam String name, @RequestParam(required = false) Optional<Long> parentId, HttpServletResponse response) {
        String getMessage = categoryService.addCategory(name,parentId);
        if (getMessage.contains("Success")) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @DeleteMapping("/delete")
    public String deleteCategory(@RequestParam Long id,HttpServletResponse response) {
        String getMessage = categoryService.deleteCategory(id);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @PutMapping("/updateCategory")
    public String updateCategory(@RequestParam Long id,@RequestParam String name,HttpServletResponse response) {
        String getMessage = categoryService.updateCategory(name,id);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }
    @GetMapping("{id}")
    public CategoryDTO viewCategory(@PathVariable Long id) {
        return categoryService.viewCategory(id);
    }

    @GetMapping("/all")
    public List<CategoryDTO> viewCategories(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10") String size, @RequestParam(defaultValue = "id") String SortBy, @RequestParam(defaultValue = "ASC") String order, @RequestParam Optional<String> query) {
        return categoryService.viewCategories(page,size,SortBy,order,query);
    }
    @PostMapping("/addCategoryMetaData")
    public String addCategoryMetadata(@RequestBody CategoryMetaDataFieldDTO fieldDTO, HttpServletResponse response) {
        String getMessage = categoryService.addCategoryMetaData(fieldDTO);
        if (getMessage.contains("Category Meta Data Field added successfully")) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }
    @PostMapping("/updateCategoryMetaData")
    public String updateCategoryMetadata(@RequestBody CategoryMetaDataFieldDTO fieldValueDTO, Long id, HttpServletResponse response) {
        String getMessage = categoryService.updateCategoryMetaData(fieldValueDTO,id);
        if (getMessage.contains("Field Updated Successfully")) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

}
