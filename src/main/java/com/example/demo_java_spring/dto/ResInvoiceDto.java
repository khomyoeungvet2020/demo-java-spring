package com.example.demo_java_spring.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResInvoiceDto {
    private String id;
    private String invoiceNo;
    private Date invDate;
    private Double totalAmount;
    private String status;
    private Date createdDate;
    private String userId;
    private String userName;
}
