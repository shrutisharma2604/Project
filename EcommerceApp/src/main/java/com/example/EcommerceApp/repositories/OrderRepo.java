package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepo extends CrudRepository<Orders,Long> {
}
