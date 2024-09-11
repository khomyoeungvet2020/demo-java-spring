package com.example.demo_java_spring.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo_java_spring.helper.Helper;
import com.example.demo_java_spring.models.Role;
import com.example.demo_java_spring.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Page<Role> getAllRoles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Role> roles = roleRepository.findAll(pageable);
        return roles;
    }

    public Role getRoleById(String id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role saveRole(Role role) {
        role.setCreateDate(new Date());
        role.setStatus(Helper.ACTIVE);
        return roleRepository.save(role);
    }

    public void deleteRole(String id) {
        Optional<Role> role = roleRepository.findById(id);
        Role getrole = role.get();
        getrole.setStatus(Helper.DELETE);
        roleRepository.save(getrole);
    }
}
