package com.example.demo_java_spring.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo_java_spring.helper.Helper;
import com.example.demo_java_spring.models.Item;
import com.example.demo_java_spring.repository.ItemRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Page<Item> getAllItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Item> items = itemRepository.findAll(pageable);
        return items;
    }

    public Item getItemById(String id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item saveItem(Item item) {
        item.setCreatedDate(new Date());
        item.setStatus(Helper.ACTIVE);
        return itemRepository.save(item);
    }

    public void deleteItem(String id) {
        Optional<Item> item = itemRepository.findById(id);
        Item getItem = item.get();
        getItem.setStatus(Helper.DELETE);
        itemRepository.save(getItem);
    }

}
