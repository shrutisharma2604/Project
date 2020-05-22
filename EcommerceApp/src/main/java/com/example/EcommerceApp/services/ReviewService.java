package com.example.EcommerceApp.services;

import com.example.EcommerceApp.dto.ReviewDTO;
import com.example.EcommerceApp.entities.Customer;
import com.example.EcommerceApp.entities.Product;
import com.example.EcommerceApp.entities.ProductReview;
import com.example.EcommerceApp.entities.User;
import com.example.EcommerceApp.exception.NotFoundException;
import com.example.EcommerceApp.exception.UserNotFoundException;
import com.example.EcommerceApp.repositories.ProductRepository;
import com.example.EcommerceApp.repositories.ReviewRepository;
import com.example.EcommerceApp.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    Logger logger = LoggerFactory.getLogger(ReviewService.class);

    /**
     * This method is used to add the review for products
     * @param reviewDTO
     * @param cid
     * @param pid
     * @return
     */
    public String addReview(ReviewDTO reviewDTO, Long cid, Long pid){

        Optional<User> customer = userRepository.findById(cid);
        Optional<Product> product= productRepository.findById(pid);

        if(!customer.isPresent())
            throw new UserNotFoundException("User not found");

        else if(!product.isPresent())
            throw new NotFoundException("Product not found");

        else {
            User user=new User();
            user=customer.get();

            Customer customer1=new Customer();
            customer1=(Customer)user;

            ProductReview productReview=new ProductReview();
            BeanUtils.copyProperties(reviewDTO,productReview);
            productReview.setCustomer(customer1);

            Product product1= new Product();
            product1= product.get();

            productReview.setProduct(product1);

            reviewRepository.save(productReview);

            logger.info("Product Review Posted by Customer");

            return "Review posted successfully";
        }
    }
}
