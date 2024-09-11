package com.example.demo_java_spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo_java_spring.models.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
    Page<Role> findAll(Pageable pageable);

    Role findByName(String name);
}
