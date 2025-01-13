package com.grocery.booking.application.service;

import com.grocery.booking.application.entity.GroceryItem;
import com.grocery.booking.application.repo.GroceryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroceryItemService {

    @Autowired
    private GroceryItemRepository groceryItemRepository;

    // Add new grocery item
    public void addGroceryItem(GroceryItem groceryItem) {
        groceryItemRepository.save(groceryItem);
    }

    // Get all grocery items
    public List<GroceryItem> getAllGroceryItems() {
        return groceryItemRepository.findAll();
    }

    // Delete grocery item by ID
    public void deleteGroceryItem(Long id) {
        groceryItemRepository.deleteById(id);
    }

    // Update existing grocery item
    public void updateGroceryItem(Long id, GroceryItem updatedGroceryItem) {
        Optional<GroceryItem> existingGroceryItem = groceryItemRepository.findById(id);
        if (existingGroceryItem.isPresent()) {
            GroceryItem groceryItem = existingGroceryItem.get();
            groceryItem.setName(updatedGroceryItem.getName());
            groceryItem.setPrice(updatedGroceryItem.getPrice());
            groceryItem.setInventoryLevel(updatedGroceryItem.getInventoryLevel());
            groceryItemRepository.save(groceryItem);
        }
    }

    // Update inventory level of grocery item
    public void updateInventoryLevel(Long id, int inventoryLevel) {
        Optional<GroceryItem> groceryItem = groceryItemRepository.findById(id);
        groceryItem.ifPresent(item -> {
            item.setInventoryLevel(inventoryLevel);
            groceryItemRepository.save(item);
        });
    }
}
