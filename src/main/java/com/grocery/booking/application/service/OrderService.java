package com.grocery.booking.application.service;

import com.grocery.booking.application.controller.request.OrderItemRequest;
import com.grocery.booking.application.controller.request.OrderRequest;
import com.grocery.booking.application.entity.GroceryItem;
import com.grocery.booking.application.entity.Order;
import com.grocery.booking.application.entity.OrderItem;
import com.grocery.booking.application.entity.User;
import com.grocery.booking.application.exception.GroceryItemNotFoundException;
import com.grocery.booking.application.exception.InsufficientBalanceException;
import com.grocery.booking.application.exception.InsufficientInventoryException;
import com.grocery.booking.application.repo.GroceryItemRepository;
import com.grocery.booking.application.repo.OrderItemRepo;
import com.grocery.booking.application.repo.OrderRepository;
import com.grocery.booking.application.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private GroceryItemRepository groceryItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepo orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    // Get all available grocery items
    public List<GroceryItem> getAllGroceryItems() {
        return groceryItemRepository.findAll();
    }

    // Place an order
    public Order placeOrder(OrderRequest orderRequest) {
        // Get the user details to check the current balance
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Calculate the total amount of the order first
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : orderRequest.getItems()) {
            // Fetch the grocery item by its ID
            GroceryItem groceryItem = groceryItemRepository.findById(itemRequest.getGroceryItemId())
                    .orElseThrow(() -> new GroceryItemNotFoundException("Grocery item not found"));

            // Check if inventory is sufficient
            if (groceryItem.getInventoryLevel() < itemRequest.getQuantity()) {
                throw new InsufficientInventoryException("Insufficient inventory for item: " + groceryItem.getName());
            }

            // Calculate total amount for this item
            BigDecimal itemTotal = BigDecimal.valueOf(groceryItem.getPrice()).multiply(new BigDecimal(itemRequest.getQuantity()));
            totalAmount = totalAmount.add(itemTotal); // Add item total to order's total
        }

        // Check if the user has enough balance to place the order
        // Check if the user has enough balance to place the order
        if (user.getCurrentBalance() < totalAmount.doubleValue()) {
            throw new InsufficientBalanceException("Insufficient balance to place the order");
        }

// Create new order
        Order order = new Order();
        order.setUserId(orderRequest.getUserId());
        order.setTotalAmount(totalAmount);
        order = orderRepository.save(order);

// Deduct balance from the user
        user.setCurrentBalance(user.getCurrentBalance() - totalAmount.doubleValue());
        userRepository.save(user);


        // Process order items
        for (OrderItemRequest itemRequest : orderRequest.getItems()) {
            GroceryItem groceryItem = groceryItemRepository.findById(itemRequest.getGroceryItemId())
                    .orElseThrow(() -> new GroceryItemNotFoundException("Grocery item not found"));

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setGroceryItem(groceryItem);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItemRepository.save(orderItem);

            // Update the inventory level
            groceryItem.setInventoryLevel(groceryItem.getInventoryLevel() - itemRequest.getQuantity());
            groceryItemRepository.save(groceryItem);
        }

        // Save the updated order with total amount
        return orderRepository.save(order);
    }



}
