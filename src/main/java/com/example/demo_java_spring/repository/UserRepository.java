package com.example.demo_java_spring.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_java_spring.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAllByStatus(String status);

    User findByUsername(String username);

    Page<User> findAll(Pageable pageable);

}
