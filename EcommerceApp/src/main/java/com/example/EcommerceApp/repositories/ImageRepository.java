package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
  /*  @Query("from Image where userId=:userId")
    Image findImageByUserId(@Param("userId") Long userId);

   *//* @Query("from Image where productVariationId=:variationId")
    Image findImageByVariationId(@Param("variationId") Long variationId);*/
}
