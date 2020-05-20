package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.CategoryMetaDataFieldValue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryMetaDataFieldValueRepo extends CrudRepository<CategoryMetaDataFieldValue,Long> {

    @Query(value = "select CategoryMetaDataField.name,CategoryMetaDataFieldValue.value from CategoryMetaDataFieldValue inner join CategoryMetaDataField on CategoryMetaDataFieldValue.category_metadata_field_id = CategoryMetaDataField.id AND CategoryMetaDataFieldValue.category_id=:id",nativeQuery = true)
    List<Object[]> findCategoryMetadataFieldValuesById(@Param("id") Long id);
}
