package com.example.EcommerceApp.Repositories;
import com.example.EcommerceApp.Entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User,Long> {

    //User findByUsername(String username);
    User findByEmail(String email);

}
