package com.example.demo_java_spring.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo_java_spring.models.Category;
import com.example.demo_java_spring.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Cacheable(value = "categories")
    public Page<Category> getAllCategories(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories;
    }

    public Category getCategoryById(String id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = "categories", allEntries = true)
    @CachePut(value = "categoryById", key = "#result.id")
    public Category saveCategory(Category category) {
        category.setCreateDate(new Date().toString());
        category.setStatus("active");
        return categoryRepository.save(category);
    }

    public void deleteCategory(String id) {
        Optional<Category> category = categoryRepository.findById(id);
        Category getCategory = category.get();
        getCategory.setStatus("deleted");
        categoryRepository.save(getCategory);
    }
}
