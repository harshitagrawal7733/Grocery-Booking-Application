package com.grocery.booking.application.controller;

import com.grocery.booking.application.controller.request.OrderRequest;
import com.grocery.booking.application.entity.GroceryItem;
import com.grocery.booking.application.exception.GroceryItemNotFoundException;
import com.grocery.booking.application.exception.InsufficientBalanceException;
import com.grocery.booking.application.exception.InsufficientInventoryException;
import com.grocery.booking.application.service.OrderService;
import com.grocery.booking.application.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grocery/app/v1/user")
@Slf4j
public class UserOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/grocery-items")
    public List<GroceryItem> getAllGroceryItems() {
        return orderService.getAllGroceryItems();
    }

    @PostMapping("/order")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) {
        try {
            orderService.placeOrder(orderRequest);
            return ResponseEntity.ok("Order placed successfully");
        } catch (InsufficientBalanceException | InsufficientInventoryException | GroceryItemNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage()); // Return a 400 Bad Request with the error message
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(500).body("Internal server error");
        }
    }


}