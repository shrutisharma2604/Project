package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.dto.CategoryMetaDataFieldDto;
import com.example.EcommerceApp.services.AdminService;
import com.example.EcommerceApp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/admin/home")
public class AdminController {

    @Autowired
    private AdminService adminService;

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

    @PostMapping(path = "/metaDataField")
    public String addMetaDataField(@Valid @RequestBody CategoryMetaDataFieldDto categoryMetaDataFieldDto) {
        return categoryService.addMetadataField(categoryMetaDataFieldDto);
    }

    @GetMapping(path = "/metaData")
    public MappingJacksonValue getMetaDataField() {
        return categoryService.viewAllMetaDataFields();
    }

    @PostMapping("/add")
    public String addCategory(@RequestParam String name, @RequestParam(required = false) Optional<Long> parentId, HttpServletResponse response) {
        String getMessage = categoryService.addCategory(name, parentId);
        if (getMessage.contains("Success")) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @DeleteMapping("/delete")
    public String deleteCategory(@RequestParam Long id, HttpServletResponse response) {
        String getMessage = categoryService.deleteCategory(id);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @PutMapping("/update")
    public String updateCategory(@RequestParam Long id, @RequestParam String name, HttpServletResponse response) {
        String getMessage = categoryService.updateCategory(name, id);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }
}
