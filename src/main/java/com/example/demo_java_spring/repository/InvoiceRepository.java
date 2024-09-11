package com.example.demo_java_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo_java_spring.models.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {

}
