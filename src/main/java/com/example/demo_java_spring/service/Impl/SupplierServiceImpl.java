package com.example.demo_java_spring.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.demo_java_spring.dto.SupplierDto;
import com.example.demo_java_spring.models.Supplier;
import com.example.demo_java_spring.repository.SupplierRepository;
import com.example.demo_java_spring.service.SupplierService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SupplierServiceImpl extends BaseServiceImpl<Supplier, SupplierDto, String> implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public JpaRepository<Supplier, String> getRepository() {
        return supplierRepository;
    }

    @Override
    protected SupplierDto convertToDto(Supplier entity) {
        return objectMapper.convertValue(entity, SupplierDto.class);
    }

}
