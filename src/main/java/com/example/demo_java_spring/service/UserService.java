package com.example.demo_java_spring.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo_java_spring.helper.Helper;
import com.example.demo_java_spring.models.User;
import com.example.demo_java_spring.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<User> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);
        return users;
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setCreateDate(new Date().toString());
        user.setStatus(Helper.ACTIVE);
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        Optional<User> user = userRepository.findById(id);
        User getUser = user.get();
        getUser.setStatus("deleted");
        userRepository.save(getUser);
    }
}
