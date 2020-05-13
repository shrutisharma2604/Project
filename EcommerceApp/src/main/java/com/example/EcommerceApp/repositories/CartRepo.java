package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepo extends CrudRepository<Cart, Long> {
}
