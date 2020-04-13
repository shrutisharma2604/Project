package com.example.EcommerceApp.Repositories;

import com.example.EcommerceApp.Entities.ForgotPasswordToken;
import org.springframework.data.repository.CrudRepository;

public interface PasswordRepo extends CrudRepository<ForgotPasswordToken,Long> {
    ForgotPasswordToken findByUserEmail(String email);
    void deleteByUserEmail(String email);
}
