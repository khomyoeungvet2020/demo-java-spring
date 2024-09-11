package com.example.demo_java_spring.controller;

import java.io.Serializable;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo_java_spring.dto.ResponseModel;
import com.example.demo_java_spring.models.BaseEntity;
import com.example.demo_java_spring.service.BaseService;

import jakarta.validation.ConstraintViolationException;

public abstract class BaseController<T, D, ID extends Serializable> {

    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

    public abstract BaseService<T, D, ID> getService();

    @PostMapping
    public ResponseModel<T> create(@RequestBody T entity) {
        int retryCount = 0;
        while (true) {
            try {
                if (entity instanceof BaseEntity<?>) {
                    BaseEntity<String> baseEntity = (BaseEntity<String>) entity;
                    log.info("Before setting id: {}", baseEntity.getId());
                    String uuid = UUID.randomUUID().toString();
                    baseEntity.setId(uuid);
                    log.info("After setting id: {}", baseEntity.getId());
                    baseEntity.setStatus("ACTIVE");
                    baseEntity.setModNum(0);
                    baseEntity.setMainActionCode("NEW");
                    baseEntity.setCreatedDate(System.currentTimeMillis());
                    baseEntity.setCreatedBy("admin");
                    baseEntity.setModifiedBy("");
                    baseEntity.setModifiedDate(null);
                }
                T createdEntity = getService().create(entity);
                ResponseModel<T> response = new ResponseModel<>(createdEntity, "Entity created successfully", "200",
                        null);
                log.info("Response to user: {}", response);

                return response;
                // return new ResponseModel<>(createdEntity, "Entity created successfully",
                // "200", null);
            } catch (TransactionSystemException e) {
                Throwable cause = e.getRootCause();
                if (cause instanceof ConstraintViolationException) {
                    // Handle constraint violation
                    return new ResponseModel<>(null, "Constraint violation: " + cause.getMessage(), "01", null);
                } else if (cause instanceof DeadlockLoserDataAccessException) {
                    // Handle deadlock
                    if (++retryCount > 3) {
                        return new ResponseModel<>(null, "Deadlock: " + cause.getMessage(), "02", null);
                    } else {
                        continue;
                    }
                } else {
                    // Handle other exceptions
                    return new ResponseModel<>(null, "Other exception: " + cause.getMessage(), "03", null);
                }

            }
        }
    }

    @GetMapping
    public ResponseModel<Page<D>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "25") int limit,
            @RequestParam(defaultValue = "DESC") Sort.Direction order,
            @RequestParam(defaultValue = "id") String orderBy,
            @RequestParam(defaultValue = "") String content) {
        Pageable pageable = PageRequest.of(offset, limit, order, orderBy);
        Page<D> page = getService().getAllWithPaginationAndSearch(pageable, content);
        return new ResponseModel<>(page, "Fetched entities", "200", null);
    }

    @GetMapping("/id")
    public ResponseModel<T> getById(@RequestParam ID id) {
        T entity = getService().getById(id);
        if (entity == null) {
            return new ResponseModel<>(null, "Entity not found", "404", null);
        }
        return new ResponseModel<>(entity, "Fetched entity", "200", null);
    }

    @PutMapping("/{id}")
    public ResponseModel<T> update(@PathVariable ID id, @RequestBody T entity) {
        T updatedEntity = getService().update(id, entity);
        return new ResponseModel<>(updatedEntity, "Entity updated successfully", "200", null);
    }

    @DeleteMapping("/{id}")
    public ResponseModel<Void> delete(@PathVariable ID id) {
        getService().delete(id);
        return new ResponseModel<>(null, "Entity deleted successfully", "200", null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseModel<Void> handleException(Exception e) {
        return new ResponseModel<>(null, e.getMessage(), "500", null);
    }
}
