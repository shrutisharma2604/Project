package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.OrderProduct;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductRepo extends CrudRepository<OrderProduct,Long> {
}
