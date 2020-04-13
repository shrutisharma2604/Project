package com.example.EcommerceApp.Repositories;

import com.example.EcommerceApp.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
