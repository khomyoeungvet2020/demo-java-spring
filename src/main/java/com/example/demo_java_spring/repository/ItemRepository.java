package com.example.demo_java_spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_java_spring.models.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    Page<Item> findAll(Pageable pageable);
}
