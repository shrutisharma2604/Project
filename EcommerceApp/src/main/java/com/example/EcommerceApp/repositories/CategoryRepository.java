package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.entities.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Long> {
    Category findByName(String name);

    @Query(value = "select * from Category where parent_id=:id",nativeQuery = true)
    List<Optional<Category>> findByParentId(@Param("id") Long id);

    List<Category> findAll(Pageable pageable);

    @Query(value = "select parent_id from Category",nativeQuery = true)
    List<Object> findLeafCategories();

    @Query(value = "select id from Category",nativeQuery = true)
    List<Object> findCategoryId();

    @Query(value = "select * from Category where parent_id is null",nativeQuery = true)
    List<Category> findRootCategories();

    @Query(value = "select * from Category where name=:name AND parent_id=:parentId",nativeQuery = true)
    Category findByNameAndParentId(@Param("name") String name,Long parentId);

    @Transactional
    @Modifying
    @Query(value = "delete from Category where id=:id",nativeQuery = true)
    void deleteById(@Param("id") Long id);

    @Query(value = "select parent_id from Category",nativeQuery = true)
    List<Long> getParentCategories();
}
