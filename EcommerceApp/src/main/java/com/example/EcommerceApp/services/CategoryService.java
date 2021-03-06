package com.example.EcommerceApp.services;

import com.example.EcommerceApp.dto.*;
import com.example.EcommerceApp.entities.Category;
import com.example.EcommerceApp.entities.CategoryMetaDataField;
import com.example.EcommerceApp.entities.CategoryMetaDataFieldValue;
import com.example.EcommerceApp.exception.NotFoundException;
import com.example.EcommerceApp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.transaction.Transactional;
import java.util.*;


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

    @Autowired
    private ProductVariationRepo productVariationRepo;

    Logger logger = LoggerFactory.getLogger(CategoryService.class);

    /**
     * This method is used to add meta data
     * @param fieldName
     * @return
     */
    public String addMetadata(String fieldName) {
        if (categoryMetaDataFieldRepo.findByName(fieldName) != null) {
            return  "field name already exist";
        }
        CategoryMetaDataField metadata = new CategoryMetaDataField();
        metadata.setName(fieldName);
        categoryMetaDataFieldRepo.save(metadata);
        return "Success " + categoryMetaDataFieldRepo.findByName(fieldName).getId();
    }

    /**
     * This method is used to view meta data field
     * @param page
     * @param size
     * @param sortBy
     * @param order
     * @param query
     * @return
     */

    public List<CategoryMetaDataField> viewMetadata(String page, String size, String sortBy, String order, Optional<String> query) {
        if (query.isPresent()) {
            List<CategoryMetaDataField> categoryMetadataFields = new ArrayList<>();
            categoryMetadataFields.add(categoryMetaDataFieldRepo.findById(Long.parseLong(query.get())).get());
            return categoryMetadataFields;
        }
        List<CategoryMetaDataField> categoryMetadataFields =  categoryMetaDataFieldRepo.findAll(PageRequest.of(Integer.parseInt(page),Integer.parseInt(size), Sort.by(Sort.Direction.fromString(order),sortBy)));
        return categoryMetadataFields;
    }

    /**
     * This method is used to add category
     * @param name
     * @param parentId
     * @return
     */
    public String addCategory(String name, Optional<Long> parentId) {
        Category category = new Category();
        if (parentId.isPresent()) {
            if (!productRepository.findByCategoryId(parentId.get()).isEmpty()) {
                return "parent id is already associated with some product";
            }
            List<Category> rootCategory = categoryRepository.findRootCategories();
            rootCategory.forEach(r->{
                if (r.getName().equals(name)) {
                    logger.info(name+ "root category already exist");
                }
            });
            List<Optional<Category>> subCategory = categoryRepository.findByParentId(parentId.get());
            if (!subCategory.isEmpty()) {
                subCategory.forEach(s->{
                    if (s.get().getName().equals(name)) {
                        logger.info(name + " already exist");
                    }
                });
            }
            category.setName(name);
            category.setParentId(categoryRepository.findById(parentId.get()).get());
            categoryRepository.save(category);
            return "Success " + categoryRepository.findByNameAndParentId(name,parentId.get()).getId();

        }
        if (!parentId.isPresent()) {
            if (categoryRepository.findByName(name) != null) {
                return name + " category already exist";
            }
            category.setName(name);
            categoryRepository.save(category);
            return "Success " + categoryRepository.findByName(name).getId();
        }
        return "Success" + categoryRepository.findByNameAndParentId(name,parentId.get()).getId();
    }

    /**
     * This method is used to delete the category
     * @param id
     * @return
     */

   @Transactional
    public String deleteCategory(Long id) {
        if (!categoryRepository.findById(id).isPresent()) {
            return id + " category does not exist";
        }
        if (!productRepository.findByCategoryId(id).isEmpty()) {
            return "id is associated with some product, cannot delete";
        }
        if (!categoryRepository.findByParentId(id).isEmpty()) {
            return "id is a parent category, cannot delete";
        }
        categoryRepository.deleteById(id);
        return "Success";
    }

    /**
     * This method is used to update the category
     * @param name
     * @param id
     * @return
     */

    public String updateCategory(String name,Long id) {
        if (!categoryRepository.findById(id).isPresent()) {
            return id + "category does not exist";
        }
        List<Category> rootCategories = categoryRepository.findRootCategories();
        rootCategories.forEach(r->{
            if (r.getName().equals(name)) {
                logger.info( name + " already a root category");
            }
        });
        List<Optional<Category>> subCategory = categoryRepository.findByParentId(id);
        if (!subCategory.isEmpty()) {
            subCategory.forEach(s->{
                if (s.get().getName().equals(name)) {
                    logger.info(name + " already exist");
                }
            });
        }

        Optional<Category> category = categoryRepository.findById(id);
        Category updateCategory = category.get();
        updateCategory.setName(name);
        categoryRepository.save(updateCategory);
        return "Success";
    }

    /**
     * This method is used to view particular category
     * @param id
     * @return
     */
    public CategoryDTO viewCategory(Long id) {
        if (!categoryRepository.findById(id).isPresent()) {
            throw new NotFoundException(id + " category does not exist");
        }
        CategoryDTO categoryDto = new CategoryDTO();
        Optional<Category> category = categoryRepository.findById(id);
        try {
            List<Object[]> categoryFieldValues = categoryMetaDataFieldValueRepo.findCategoryMetadataFieldValuesById(id);
            Set<HashMap<String,String>> filedValuesSet = new HashSet<>();
            categoryFieldValues.forEach(c->{
                HashMap fieldValueMap = new HashMap<>();
                List<Object> arr = Arrays.asList(c);
                for (int i=0;i<arr.size();i++) {
                    fieldValueMap.put(arr.get(0),arr.get(i));
                }
                filedValuesSet.add(fieldValueMap);
            });
            List<Optional<Category>> subCategory = categoryRepository.findByParentId(id);
            Set<Category> subCategorySet = new HashSet<>();
            subCategory.forEach(s->{
                subCategorySet.add(s.get());
            });
            categoryDto.setCategory(category.get());
            categoryDto.setSubCategories(subCategorySet);
            categoryDto.setFieldValues(filedValuesSet);
        }catch (Exception ex) {}

        return categoryDto;
    }

    /**
     * This method is used to  list all categories
     * @param page
     * @param size
     * @param sortBy
     * @param order
     * @param query
     * @return
     */
    public List<CategoryDTO> viewCategories(String page, String size, String sortBy, String order, Optional<String> query) {
        if (query.isPresent()) {
            Optional<Category> category = categoryRepository.findById(Long.parseLong(query.get()));
            List<CategoryDTO> categoryDTOS = new ArrayList<>();
            categoryDTOS.add(viewCategory(category.get().getId()));
            return categoryDTOS;
        }

        List<Category> categories = categoryRepository.findAll(PageRequest.of(Integer.parseInt(page),Integer.parseInt(size),Sort.by(Sort.Direction.fromString(order),sortBy)));
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        categories.forEach(c-> {
            categoryDTOS.add(viewCategory(c.getId()));
        });
        return categoryDTOS;
    }

    /**
     * This method is used to view the leaf categories
     * @return
     */
    public List<CategoryDTO> viewLeafCategories() {
        List<Object> leafCategoryIds = categoryRepository.findLeafCategories();
        List<Object> categoryIds = categoryRepository.findCategoryId();
        categoryIds.removeAll(leafCategoryIds);
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Object o : categoryIds) {
            CategoryDTO categoryDTO = viewCategory(Long.parseLong(o.toString()));
            categoryDTOS.add(categoryDTO);
        }
        return categoryDTOS;
    }

    /**
     * This method is used to view category same as parent category
     * @param categoryId
     * @return
     */
    public List<Category> viewCategoriesSameParent(Optional<Long> categoryId) {
        if (categoryId.isPresent()) {
            if (!categoryRepository.findById(categoryId.get()).isPresent()) {
                throw new NotFoundException(categoryId.get() + " category does not exist");
            }
            List<Optional<Category>> subCategory = categoryRepository.findByParentId(categoryId.get());
            List<Category> subCategoryList = new ArrayList<>();
            subCategory.forEach(c->{
                subCategoryList.add(c.get());
            });
            return subCategoryList;
        }
        List<Category> categories = categoryRepository.findRootCategories();
        return categories;
    }

    /**
     * This method is used to filter category
     * @param categoryId
     * @return
     */
    public List<?> filterCategory(Long categoryId) {
        if (!categoryRepository.findById(categoryId).isPresent()) {
            throw new NotFoundException(categoryId + " category does not exist");
        }
        List<FilterCategoryDTO> categoryDTOS = new ArrayList<>();
        List<Long> leafCategories = categoryRepository.getParentCategories();
        if (leafCategories.contains(categoryId)) {
            // not a leaf category
            List<Optional<Category>> subCategory = categoryRepository.findByParentId(categoryId);
            subCategory.forEach(s->{
                FilterCategoryDTO filterCategoryDTO = filterCategoryProvider(categoryId);
                categoryDTOS.add(filterCategoryDTO);
            });
        }
        if (!leafCategories.contains(categoryId)) {
            // leaf category
            FilterCategoryDTO filterCategoryDTO = filterCategoryProvider(categoryId);
            categoryDTOS.add(filterCategoryDTO);
        }
        return categoryDTOS;
    }

    /**
     * This method is used in filterCategory method to filter the category
     * @param id
     * @return
     */
    private FilterCategoryDTO filterCategoryProvider(Long id) {
        List<Object[]> categoryFieldValues = categoryMetaDataFieldValueRepo.findCategoryMetadataFieldValuesById(id);
        Set<HashMap<String,String>> filedValuesSet = new HashSet<>();
        categoryFieldValues.forEach(c->{
            HashMap fieldValueMap = new HashMap<>();
            List<Object> arr = Arrays.asList(c);
            for (int i=0;i<arr.size();i++) {
                fieldValueMap.put(arr.get(0),arr.get(i));
            }
            filedValuesSet.add(fieldValueMap);
        });
        FilterCategoryDTO filterCategoryDTO = new FilterCategoryDTO();
        filterCategoryDTO.setFiledValuesSet(filedValuesSet);
        filterCategoryDTO.setBrands(productRepository.getBrandsOfCategory(id));
        Optional<String> minPrice = productVariationRepo.getMinPrice(id);
        if (minPrice.isPresent()) {
            filterCategoryDTO.setMinPrice(minPrice.get());
        }
        Optional<String> maxPrice = productVariationRepo.getMaxPrice(id);
        if (maxPrice.isPresent()) {
            filterCategoryDTO.setMinPrice(maxPrice.get());
        }
        return filterCategoryDTO;
    }

    /**
     * This method is used add category meta data values
     * @param categoryMetaDataFieldDTO
     * @return
     */
    public String addCategoryMetaData(CategoryMetaDataFieldDTO categoryMetaDataFieldDTO)  {
        Optional<Category> category=categoryRepository.findById(categoryMetaDataFieldDTO.getCategoryId());
        if(!category.isPresent()){
            throw new NotFoundException("category doesn't exist");
        }
        Optional<CategoryMetaDataField> categoryMetaDataField=categoryMetaDataFieldRepo.findByMetaDataName(categoryMetaDataFieldDTO.getName());
        if(!categoryMetaDataField.isPresent()){
            throw new NotFoundException("meta data field doesn't exist");
        }
        HashMap<String,HashSet<String>> fieldValue=categoryMetaDataFieldDTO.getFiledValues();
        CategoryMetaDataFieldValue categoryMetaDataFieldValue=new CategoryMetaDataFieldValue();
        categoryMetaDataFieldValue.setCategory(category.get());
        categoryMetaDataFieldValue.setCategoryMetaDataField(categoryMetaDataField.get());
        categoryMetaDataFieldValue.setValue(String.valueOf(fieldValue));
        categoryMetaDataField.get().getCategoryMetaDataFieldValueSet().add(categoryMetaDataFieldValue);
        categoryMetaDataFieldRepo.save(categoryMetaDataField.get());
        return "Category Meta Data Field added successfully";

    }

    /**
     * This method is used to update category meta data values
     * @param categoryMetaDataFieldDTO
     * @param id
     * @return
     */
    public String updateCategoryMetaData(CategoryMetaDataFieldDTO categoryMetaDataFieldDTO,Long id)  {
        Optional<Category> category=categoryRepository.findById(categoryMetaDataFieldDTO.getCategoryId());
        if(!category.isPresent()){
            throw new NotFoundException("category doesn't exist");
        }
        Optional<CategoryMetaDataField> categoryMetaDataField=categoryMetaDataFieldRepo.findByMetaDataName(categoryMetaDataFieldDTO.getName());
        if(!categoryMetaDataField.isPresent()){
            throw new NotFoundException("meta data field doesn't exist");
        }
        HashMap<String,HashSet<String>> fieldValue=categoryMetaDataFieldDTO.getFiledValues();
        Optional<CategoryMetaDataFieldValue> categoryMetaDataFieldValue=categoryMetaDataFieldValueRepo.findById(id);
        categoryMetaDataFieldValue.get().setCategory(category.get());
        categoryMetaDataFieldValue.get().setCategoryMetaDataField(categoryMetaDataField.get());
        categoryMetaDataFieldValue.get().setValue(String.valueOf(fieldValue));
        categoryMetaDataField.get().getCategoryMetaDataFieldValueSet().add(categoryMetaDataFieldValue.get());
        categoryMetaDataFieldValueRepo.save(categoryMetaDataFieldValue.get());
        return "Field updated successfully";

    }
}
