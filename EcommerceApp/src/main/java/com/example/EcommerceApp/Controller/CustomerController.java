package com.example.EcommerceApp.Controller;
import com.example.EcommerceApp.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/customer/home")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @GetMapping(path = "/getCustomerDetails/{email}")
    public MappingJacksonValue getCustomerProfile(@PathVariable("email") String email){
        return customerService.getCustomerDetails(email);
    }
}
