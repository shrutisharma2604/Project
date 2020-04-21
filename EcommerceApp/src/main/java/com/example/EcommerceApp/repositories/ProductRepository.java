package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Long> {
    @Query(value = "select * from Product where brand=:brand AND category_id=:categoryId AND seller_user_id=:sellerId AND name=:name",nativeQuery = true)
    Long findUniqueProduct(@Param("brand")  String brand, @Param("categoryId") Long categoryId,@Param("sellerId") Long sellerId, @Param("name") String name);

    @Query(value = "select * from Product where seller_user_id=:userId AND is_deleted=true",nativeQuery = true)
    List<Product> findAllProducts(@Param("userId") Long userId);

    @Query(value = "select * from Product where category_id=:categoryId",nativeQuery = true)
    List<Product> findAllProduct(@Param("categoryId") Long categoryId);

    @Query(value = "select * from Product p where p.category_id=:categoryId or p.product_name=:productName or p.id in (select pv.product_id from Product_Variation pv)",nativeQuery = true)
    List<Product> findSimilarProducts(@Param(value = "categoryId") Long categoryId, @Param(value = "productName") String productName);

    @Query(value = "select * from Product where category_id=:id",nativeQuery = true)
    List<Product> findByCategoryId(@Param("id") Long id);

    @Query(value = "select brand from Product where category_id=:id",nativeQuery = true)
    List<String> getBrandsOfCategory(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "delete from Product where id=:id",nativeQuery = true)
    void deleteById(@Param("id") Long id);
}
