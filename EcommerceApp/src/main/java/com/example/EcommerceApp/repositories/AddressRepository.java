package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.Address;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address,Long> {
}
