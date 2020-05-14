package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.entities.Cart;
import com.example.EcommerceApp.entities.Customer;
import com.example.EcommerceApp.repositories.CustomerRepository;
import com.example.EcommerceApp.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/cart")
public class CartController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartService  cartService;

    @PostMapping("/addToCart/{pid}")
    public ResponseEntity<Object> addToCart(@RequestBody Cart cart, @PathVariable("pid") Long pid,Long cid) {
        Optional<Customer> customer = customerRepository.findById(cid);
        Long cusId=customer.get().getId();

        String message = cartService.addToCart(cart, cusId, pid);

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
    @PostMapping("/add-to-wishlist/{vid}/{cid}")
    public ResponseEntity<Object> addToWishlist(@PathVariable("vid") Long vid,@PathVariable("cid") Long cid) {

        Optional<Customer> customer = customerRepository.findById(cid);
        Long cusId=customer.get().getId();

        String message = cartService.addToWishlist(cid, vid);

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

}
