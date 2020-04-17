package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.CustomerActivate;
import org.springframework.data.repository.CrudRepository;

public interface CustomerActivateRepo extends CrudRepository<CustomerActivate,Long> {
    CustomerActivate findByUserEmail(String email);
    CustomerActivate findByToken(String token);
    void deleteByUserEmail(String email);
}
