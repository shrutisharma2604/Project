package com.example.EcommerceApp.repositories;
import com.example.EcommerceApp.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

    //User findByUsername(String username);
    User findByEmail(String email);

}
