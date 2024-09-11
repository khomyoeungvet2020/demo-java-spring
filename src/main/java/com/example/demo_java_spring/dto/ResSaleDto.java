package com.example.demo_java_spring.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResSaleDto {
    private ResInvoiceDto invoice;
    private List<ResInvoiceDetailDto> details;
}
