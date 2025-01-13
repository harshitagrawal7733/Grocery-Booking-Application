package com.grocery.booking.application.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {

    private Long userId;
    private List<OrderItemRequest> items;

    // Getters and Setters
}
