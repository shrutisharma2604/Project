package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.ProductVariant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepo extends CrudRepository<ProductVariant,String> {
}
