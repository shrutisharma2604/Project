package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.UserLoginAttempts;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAttemptsRepo extends CrudRepository<UserLoginAttempts,Long> {
    Optional<UserLoginAttempts> findByEmail(String email);
}
