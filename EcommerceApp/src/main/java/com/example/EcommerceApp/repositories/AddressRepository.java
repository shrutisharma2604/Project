package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address,Long> {

    /*@Query(value = "select user_id from Address where id=:id",nativeQuery = true)
    Optional<Address> findByUserId(@Param("id") Long id);*/
}
