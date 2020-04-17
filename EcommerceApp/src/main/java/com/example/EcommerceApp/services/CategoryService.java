package com.example.EcommerceApp.services;

import com.example.EcommerceApp.dto.CategoryMetaDataFieldDto;
import com.example.EcommerceApp.entities.Category;
import com.example.EcommerceApp.entities.CategoryMetaDataField;
import com.example.EcommerceApp.repositories.*;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMetaDataFieldRepo categoryMetaDataFieldRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryMetaDataFieldValueRepo categoryMetaDataFieldValueRepo;

    @Autowired
    private ProductRepository productRepository;

    public String addMetadataField(CategoryMetaDataFieldDto categoryMetaDataFieldDto){
        CategoryMetaDataField categoryMetaDataField=categoryMetaDataFieldRepo.findByName(categoryMetaDataFieldDto.getName());
        if(categoryMetaDataField!=null){
            return "Field name already exist";
        }
        else {
            CategoryMetaDataField categoryMetaDataField1=new CategoryMetaDataField();
           BeanUtils.copyProperties(categoryMetaDataFieldDto,categoryMetaDataField1);
           categoryMetaDataFieldRepo.save(categoryMetaDataField1);
            return "MetaData field is added "+ categoryMetaDataField1.getId();
        }
    }

    public MappingJacksonValue viewAllMetaDataFields(){
        List<CategoryMetaDataField> categoryMetaDataFields = categoryMetaDataFieldRepo.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("MetaData-Filter", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(categoryMetaDataFields);

        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }
    public String addCategory(String name, Optional<Long> parentId) {
        if (categoryRepository.findByName(name) != null) {
            return  " category already exist";
        }
        Category category = new Category();
        if (parentId.isPresent()) {
            if (productRepository.findByCategoryId(parentId.get()) != null) {
                return "parent id is already associated with some product";
            }
            else {
                category.setName(name);
                category.setParentId(categoryRepository.findById(parentId.get()).get());
                categoryRepository.save(category);
                return "Success " + categoryRepository.findByName(name).getId();
            }
        }
        if (!parentId.isPresent()) {
            category.setName(name);
            categoryRepository.save(category);
            return "Success " + categoryRepository.findByName(name).getId();
        }
        return "Success " + categoryRepository.findByName(name).getId();
    }
    @Transactional
    public String deleteCategory(Long id) {
        if (!categoryRepository.findById(id).isPresent()) {
            return " category does not exist";
        }
        if (productRepository.findByCategoryId(id) != null) {
            return "id is associated with some product, cannot delete";
        }
        if (categoryRepository.findByParentId(id).isPresent()) {
            return "id is a parent category, cannot delete";
        }
        categoryRepository.deleteById(id);
        return "Success";
    }

    public String updateCategory(String name,Long id) {
        if (!categoryRepository.findById(id).isPresent()) {
            return id+" category does not exist";
        }
        if (categoryRepository.findByName(name) != null) {
            return name + " category already exist";
        }
        Optional<Category> category = categoryRepository.findById(id);
        Category updateCategory = category.get();
        updateCategory.setName(name);
        categoryRepository.save(updateCategory);
        return "Success";
    }


}
