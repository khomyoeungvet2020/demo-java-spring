package com.example.demo_java_spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo_java_spring.models.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Page<Category> findAll(Pageable pageable);
}
