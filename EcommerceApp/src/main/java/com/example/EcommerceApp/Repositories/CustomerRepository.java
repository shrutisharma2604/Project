package com.example.EcommerceApp.Repositories;

import com.example.EcommerceApp.Dto.CustomerDto;
import com.example.EcommerceApp.Entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.net.ContentHandler;
import java.util.List;
@Repository
public interface CustomerRepository extends CrudRepository<Customer,Long> {
    Page<Customer> findAll(Pageable pageable);

    Customer findByEmail(String email);
}

