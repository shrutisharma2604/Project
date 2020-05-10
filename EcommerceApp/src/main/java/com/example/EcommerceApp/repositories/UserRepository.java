package com.example.EcommerceApp.repositories;
import com.example.EcommerceApp.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

    User findByEmail(String email);

   // User findByUsername(String username);
}
