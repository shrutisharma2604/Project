package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/admin/home")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping(path = "/customers")
    public MappingJacksonValue getCustomers(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10")String size, @RequestParam(defaultValue = "id") String SortBy){
        return adminService.registeredCustomers(page, size, SortBy);
    }

    @GetMapping(path = "/sellers")
    public MappingJacksonValue getSellers(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10")String size, @RequestParam(defaultValue = "id") String SortBy){
        return adminService.registeredSellers(page, size, SortBy);
    }

    @PatchMapping(path = "/activate-Customer/{id}")
    public String activationOfCustomer(@PathVariable(value = "id") Long id, HttpServletResponse response){

        String message = adminService.activateCustomer(id,response);
        if(!message.equals("Success")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PatchMapping(path = "/de-activateCustomer/{id}")
    public String deactivationOfCustomer(@PathVariable(value = "id") Long id,HttpServletResponse response){
        String message = adminService.deactivateCustomer(id,response);
        if(!message.equals("Success")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PatchMapping(path = "/activate-Seller/{id}")
    public String activationOfSeller(@PathVariable(value = "id") Long id, HttpServletResponse response){
        String message = adminService.activateSeller(id,response);
        if(!message.equals("Success")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PatchMapping(path = "/de-activateSeller/{id}")
    public String deactivationOfSeller(@PathVariable(value = "id") Long id, HttpServletResponse response){
        String message = adminService.deactivateSeller(id,response);
        if(!message.equals("Success")){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return message;
    }
}