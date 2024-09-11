package com.example.demo_java_spring.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_java_spring.models.Item;
import com.example.demo_java_spring.service.ItemService;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/get")
    public Page<Item> getAllItems(@RequestParam int page, @RequestParam int size) {
        return itemService.getAllItems(page, size);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id) {
        Item item = itemService.getItemById(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public Item createItem(@RequestBody Item item) {
        return new ResponseEntity<>(itemService.saveItem(item), HttpStatus.CREATED).getBody();
    }

    @PutMapping("/update")
    public ResponseEntity<Item> updateItem(@PathVariable String id, @RequestBody Item itemDetails) {
        Item item = itemService.getItemById(id);
        if (item != null) {
            return new ResponseEntity<>(itemService.saveItem(itemDetails), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Item deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
