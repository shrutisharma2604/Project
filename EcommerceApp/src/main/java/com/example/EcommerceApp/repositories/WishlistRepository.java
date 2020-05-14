package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.Wishlist;
import org.springframework.data.repository.CrudRepository;

public interface WishlistRepository extends CrudRepository<Wishlist,Long> {
}
