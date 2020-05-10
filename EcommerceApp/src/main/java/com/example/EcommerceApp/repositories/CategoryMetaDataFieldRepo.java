package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.CategoryMetaDataField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CategoryMetaDataFieldRepo extends CrudRepository<CategoryMetaDataField,Long> {
    CategoryMetaDataField findByName(String name);
    List<CategoryMetaDataField> findAll(Pageable pageable);

    @Query(value = "select * from CategoryMetaDataField c where c.name = :name",nativeQuery = true)
    Optional<CategoryMetaDataField> findByMetaDataName(@Param("name") String name);
}
