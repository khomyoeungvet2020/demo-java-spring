package com.example.demo_java_spring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo_java_spring.models.Customer;
import com.example.demo_java_spring.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Page<Customer> getAllCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers;
    }

    public Customer getCustomerById(String id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer saveCustomer(Customer customer) {
        // customer.setCreateDate(new Date());
        customer.setStatus("active");
        return customerRepository.save(customer);
    }

    public void deleteCustomer(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        Customer getCustomer = customer.get();
        getCustomer.setStatus("deleted");
        customerRepository.save(getCustomer);
    }
}
