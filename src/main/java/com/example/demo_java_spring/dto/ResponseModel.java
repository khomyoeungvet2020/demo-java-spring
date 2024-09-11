package com.example.demo_java_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel<T> {
    private T data;
    private String message;
    private String resCode;
    private String error;
}
