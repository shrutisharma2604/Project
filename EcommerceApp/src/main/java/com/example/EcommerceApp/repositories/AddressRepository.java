package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address,Long> {
}
