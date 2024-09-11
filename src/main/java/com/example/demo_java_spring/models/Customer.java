package com.example.demo_java_spring.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @NotBlank(message = "Name is required!")
    private String name;
    @NotBlank(message = "Gender is required!")
    private String gender;
    @Min(value = 10, message = "Age must be greater than 1!")
    private int age;
    private String status;

}
