package com.example.EcommerceApp.Repositories;

import com.example.EcommerceApp.Entities.UserLoginAttempts;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAttemptsRepo extends CrudRepository<UserLoginAttempts,Long> {
    Optional<UserLoginAttempts> findByEmail(String email);
}
