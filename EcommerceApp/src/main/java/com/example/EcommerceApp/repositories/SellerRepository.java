package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.Customer;
import com.example.EcommerceApp.entities.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SellerRepository extends CrudRepository<Seller,Long> {
    Page<Seller> findAll(Pageable pageable);

    Seller findByCompanyName(String companyName);

    List<Seller> findByGST(String gst);

    Seller findByEmail(String email);
}
