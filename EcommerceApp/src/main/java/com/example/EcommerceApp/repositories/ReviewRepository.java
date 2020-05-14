package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.ProductReview;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<ProductReview,Long> {
}
