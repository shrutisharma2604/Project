package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.Product_Variation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductVariationRepo extends CrudRepository<Product_Variation,Long> {
    @Query(value = "select * from Product_Variation where product_id=:productId",nativeQuery = true)
    List<Product_Variation> getAll(@Param("productId") Long productId, Pageable pageable);

    @Query(value = "SELECT * FROM hibernate_sequence limit 1", nativeQuery = true)
    BigDecimal getNextValMySequence();

    @Query(value = "select max(price) from Product_Variation inner join Product where Product_Variation.product_id=Product.id AND Product.category_id=:catId",nativeQuery = true)
    Optional<String> getMaxPrice(@Param("catId") Long catId);

    @Query(value = "select min(price) from Product_Variation inner join Product where Product_Variation.product_id=Product.id AND Product.category_id=:catId",nativeQuery = true)
    Optional<String> getMinPrice(@Param("catId") Long catId);

    List<Product_Variation> findByProductId(Long productId);
}
