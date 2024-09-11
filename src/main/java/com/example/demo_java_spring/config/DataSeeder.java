package com.example.demo_java_spring.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo_java_spring.models.Role;
import com.example.demo_java_spring.models.User;
import com.example.demo_java_spring.repository.RoleRepository;
import com.example.demo_java_spring.repository.UserRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    private void seedData() {
        userRepository.findAll().forEach(user -> {
            user.setRoles(null);
            userRepository.save(user);
        });

        // Delete all existing data
        userRepository.deleteAll();
        roleRepository.deleteAll();
        // Create roles
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");

        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        roleRepository.save(adminRole);
        roleRepository.save(userRole);

        // Create users
        User admin = new User();
        admin.setUsername("admin");
        String encodedAdminPassword = passwordEncoder.encode("admin@123");
        admin.setPassword(encodedAdminPassword);
        admin.setStatus("active");

        User user = new User();
        user.setUsername("user");
        String encodedUserPassword = passwordEncoder.encode("user@123");
        user.setPassword(encodedUserPassword); // Corrected line
        user.setStatus("active");

        // Set roles to users
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        admin.setRoles(adminRoles);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRoles(userRoles);

        userRepository.save(admin);
        userRepository.save(user);
    }
}
