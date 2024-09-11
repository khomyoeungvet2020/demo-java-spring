package com.example.demo_java_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo_java_spring.models.VwItem;

public interface VwItemRepository extends JpaRepository<VwItem, String> {
    List<VwItem> findAllByStatus(String status);

}
