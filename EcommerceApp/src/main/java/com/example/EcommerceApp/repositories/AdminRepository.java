package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin,Long> {
    Optional<Admin> findById(Long id);
}
