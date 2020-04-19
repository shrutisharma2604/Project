package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.CategoryMetaDataField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface CategoryMetaDataFieldRepo extends CrudRepository<CategoryMetaDataField,Long> {

    CategoryMetaDataField findByName(String name);
    List<CategoryMetaDataField> findAll(Pageable pageable);
}
