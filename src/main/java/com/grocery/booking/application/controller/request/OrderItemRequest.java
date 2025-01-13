package com.grocery.booking.application.controller.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class OrderItemRequest {

    private Long groceryItemId;  // ID of the grocery item to be ordered
    private int quantity;        // Quantity of the grocery item to be ordered
    private double price;        // Price of the item (if not calculated dynamically)


}
