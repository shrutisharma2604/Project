package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.entities.Customer;
import com.example.EcommerceApp.entities.Orders;
import com.example.EcommerceApp.repositories.CustomerRepository;
import com.example.EcommerceApp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/order/{cartId}")
    public ResponseEntity<Object> OrderPlace(@RequestBody Orders orders, @PathVariable("cartId") Long cartId,Long cid){
        Optional<Customer> customer = customerRepository.findById(cid);
        Long cusId=customer.get().getId();

        String message = orderService.placeOrder(orders, cid, cartId);

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

}
