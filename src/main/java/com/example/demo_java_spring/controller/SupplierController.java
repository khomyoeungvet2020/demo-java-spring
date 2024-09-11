package com.example.demo_java_spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_java_spring.dto.SupplierDto;
import com.example.demo_java_spring.models.Supplier;
import com.example.demo_java_spring.service.BaseService;
import com.example.demo_java_spring.service.SupplierService;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController extends BaseController<Supplier, SupplierDto, String> {

    @Autowired
    private SupplierService supplierService;

    @Override
    public BaseService<Supplier, SupplierDto, String> getService() {
        return supplierService;
    }
}
