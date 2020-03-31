package com.example.EcommerceApp.Repositories;
import com.example.EcommerceApp.Entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

}
