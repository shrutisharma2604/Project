package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.CategoryMetaDataFieldValue;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CategoryMetaDataFieldValueRepo extends CrudRepository<CategoryMetaDataFieldValue,Long> {
    List<CategoryMetaDataFieldValue> findAll();

    List<Object[]> findCategoryMetadataFieldValuesById(Long id);
}
