package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.CategoryMetaDataField;
import com.example.EcommerceApp.entities.CategoryMetaDataFieldValue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryMetaDataFieldRepo extends CrudRepository<CategoryMetaDataField,Long> {
    CategoryMetaDataField findByName(String name);
    List<CategoryMetaDataField> findAll();
}