package com.grocery.booking.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grocery/app/v1")  // Define the base path here for the controller
public class HealthController {

    @GetMapping("/health")
    public String getApplicationHealth(){
        return "Application is Running up";
    }
}
