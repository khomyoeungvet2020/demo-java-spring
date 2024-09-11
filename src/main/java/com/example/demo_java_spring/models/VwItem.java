package com.example.demo_java_spring.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vw_items")
public class VwItem {
    @Id
    private String id;
    private String full_name;
    private String category_name;
    private double price;
    private int qty;
    private String status;
}
