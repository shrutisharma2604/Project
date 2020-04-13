package com.example.EcommerceApp.Repositories;

import com.example.EcommerceApp.Entities.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin,Long> {
    Optional<Admin> findById(Long id);
}
