package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.dto.ReviewDTO;
import com.example.EcommerceApp.entities.Customer;
import com.example.EcommerceApp.entities.ProductReview;
import com.example.EcommerceApp.repositories.CustomerRepository;
import com.example.EcommerceApp.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/addReview/{cid}/{pid}")
    public ResponseEntity<Object> addReview(@Valid @RequestBody ReviewDTO reviewDTO,@PathVariable("cid") Long cid,@PathVariable("pid") Long pid){
        Optional<Customer> customer = customerRepository.findById(cid);
        Long cusId=customer.get().getId();

        String message = reviewService.addReview(reviewDTO,cusId,pid);

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
}
