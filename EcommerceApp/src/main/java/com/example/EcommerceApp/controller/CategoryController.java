package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.dto.CategoryDTO;
import com.example.EcommerceApp.entities.Category;
import com.example.EcommerceApp.entities.CategoryMetaDataField;
import com.example.EcommerceApp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public String addMetadata(@RequestParam String fieldName, HttpServletResponse response) {
        String getMessage = categoryService.addMetadata(fieldName);
        if (getMessage.contains("Success")) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @GetMapping("/viewMetaData")
    public List<CategoryMetaDataField> viewMetadata(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10") String size, @RequestParam(defaultValue = "id") String SortBy, @RequestParam(defaultValue = "ASC") String order, @RequestParam Optional<String> query) {
        return categoryService.viewMetadata(page,size,SortBy,order,query);
    }
    @PostMapping("/addCategory")
    public String addCategory(@RequestParam String name, @RequestParam(required = false) Optional<Long> parentId, HttpServletResponse response) {
        String getMessage = categoryService.addCategory(name,parentId);
        if (getMessage.contains("Success")) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @DeleteMapping("/delete")
    public String deleteCategory(@RequestParam Long id,HttpServletResponse response) {
        String getMessage = categoryService.deleteCategory(id);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @PutMapping("/updateCategory")
    public String updateCategory(@RequestParam Long id,@RequestParam String name,HttpServletResponse response) {
        String getMessage = categoryService.updateCategory(name,id);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @GetMapping("{id}")
    public CategoryDTO viewCategory(@PathVariable Long id) {
        return categoryService.viewCategory(id);
    }

    @GetMapping("/all")
    public List<CategoryDTO> viewCategories(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10") String size, @RequestParam(defaultValue = "id") String SortBy, @RequestParam(defaultValue = "ASC") String order, @RequestParam Optional<String> query) {
        return categoryService.viewCategories(page,size,SortBy,order,query);
    }
    @GetMapping("/categories")
    public List<Category> viewLeafCategories(@RequestParam Optional<Long> categoryId) {
        return categoryService.viewCategoriesSameParent(categoryId);
    }

    @GetMapping("/filterCategories/{categoryId}")
    public List<?> filterCategory(@PathVariable Long categoryId) {
        return categoryService.filterCategory(categoryId);
    }
}
