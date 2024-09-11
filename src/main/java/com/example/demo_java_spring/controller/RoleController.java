package com.example.demo_java_spring.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_java_spring.models.Role;
import com.example.demo_java_spring.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/get")
    public Page<Role> getAllRoles(@RequestParam int page, @RequestParam int size) {
        return roleService.getAllRoles(page, size);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Role> getRoleById(String id) {
        Role role = roleService.getRoleById(id);
        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public Role createRole(@RequestBody Role role) {
        return new ResponseEntity<>(roleService.saveRole(role), HttpStatus.CREATED).getBody();
    }

    @PostMapping("/update")
    public ResponseEntity<Role> updateRole(@Valid @PathVariable String id, @RequestBody Role roleDetails) {
        Role role = roleService.getRoleById(id);
        if (role != null) {
            return new ResponseEntity<>(roleService.saveRole(roleDetails), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteRole(String id) {
        roleService.deleteRole(id);
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Role deleted successfully");
        roleService.deleteRole(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
