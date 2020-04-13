package com.example.EcommerceApp.Repositories;

import com.example.EcommerceApp.Dto.SellerDto;
import com.example.EcommerceApp.Entities.Seller;
import com.example.EcommerceApp.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.net.ContentHandler;
import java.util.List;

public interface SellerRepository extends CrudRepository<Seller,Long> {
    Page<Seller> findAll(Pageable pageable);

 //   Seller findByUserId(Long userId);
    Seller findByGst(String gst);
}
