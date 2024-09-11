package com.example.demo_java_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResInvoiceDetailDto {
    private String itemId;
    private String id;
    private String invoiceId;
    private String itemName;
    private Double price;
    private int qty;
}
