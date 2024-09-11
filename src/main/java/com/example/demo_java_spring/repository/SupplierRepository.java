package com.example.demo_java_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo_java_spring.models.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, String> {

}
