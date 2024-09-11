package com.example.demo_java_spring.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<T, D, ID extends Serializable> {
    T create(T entity);

    List<T> getAll();

    Page<D> getAllWithPaginationAndSearch(Pageable pageable, String content);

    T getById(ID id);

    T update(ID id, T entity);

    void delete(ID id);
}
