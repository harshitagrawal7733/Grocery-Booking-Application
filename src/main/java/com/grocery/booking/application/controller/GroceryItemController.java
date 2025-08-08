package com.grocery.booking.application.controller;

import com.grocery.booking.application.controller.request.InventoryUpdateRequest;
import com.grocery.booking.application.controller.response.MessageResponse;
import com.grocery.booking.application.entity.GroceryItem;
import com.grocery.booking.application.exception.GroceryItemNotFoundException;
import com.grocery.booking.application.exception.InvalidRequestException;
import com.grocery.booking.application.service.GroceryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grocery/app/v1/admin")
public class GroceryItemController {

    @Autowired
    private GroceryItemService groceryItemService;

    @PostMapping("/grocery-items")
    public ResponseEntity<?> addGroceryItem(@RequestBody GroceryItem groceryItem) {
        try {
            groceryItemService.addGroceryItem(groceryItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Grocery item added successfully"));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Invalid request: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Unexpected error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/grocery-items")
    public ResponseEntity<?> getAllGroceryItems() {
        try {
            List<GroceryItem> groceryItems = groceryItemService.getAllGroceryItems();
            return ResponseEntity.ok(groceryItems);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Unexpected error occurred: " + e.getMessage()));
        }
    }

    @DeleteMapping("/grocery-items/{id}")
    public ResponseEntity<?> deleteGroceryItem(@PathVariable Long id) {
        try {
            groceryItemService.deleteGroceryItem(id);
            return ResponseEntity.ok(new MessageResponse("Grocery item deleted successfully"));
        } catch (GroceryItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Grocery item not found: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Unexpected error occurred: " + e.getMessage()));
        }
    }

    @PutMapping("/grocery-items/{id}")
    public ResponseEntity<?> updateGroceryItem(@PathVariable Long id, @RequestBody GroceryItem groceryItem) {
        System.out.println("Enterned in the updateGroceryItem");
        try {
            groceryItemService.updateGroceryItem(id, groceryItem);
            return ResponseEntity.ok(new MessageResponse("Grocery item updated successfully"));
        } catch (GroceryItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Grocery item not found: " + e.getMessage()));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Invalid request: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Unexpected error occurred: " + e.getMessage()));
        }
    }

    @PatchMapping("/grocery-items/{id}/inventory")
    public ResponseEntity<?> updateInventoryLevel(@PathVariable Long id, @RequestBody InventoryUpdateRequest request) {
        try {
            groceryItemService.updateInventoryLevel(id, request.getNewInventoryLevel());
            return ResponseEntity.ok(new MessageResponse("Inventory level updated successfully"));
        } catch (GroceryItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Grocery item not found: " + e.getMessage()));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Invalid request: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Unexpected error occurred: " + e.getMessage()));
        }
    }
}
