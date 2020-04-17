package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.ForgotPasswordToken;
import org.springframework.data.repository.CrudRepository;

public interface PasswordRepo extends CrudRepository<ForgotPasswordToken,Long> {
    ForgotPasswordToken findByUserEmail(String email);
    void deleteByUserEmail(String email);
}
