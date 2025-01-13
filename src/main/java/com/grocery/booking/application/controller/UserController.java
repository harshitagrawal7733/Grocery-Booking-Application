package com.grocery.booking.application.controller;

import com.grocery.booking.application.entity.User;
import com.grocery.booking.application.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/app/v1/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save-users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User savedUser = userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving user: " + e.getMessage());
        }
    }

    @GetMapping("/users/get-user/{id}")
    public ResponseEntity<?> getUserBalance(@PathVariable Long id) {
        try {
            boolean userExists = userService.checkUserExists(id);
            if (userExists) {
                User userBalance = userService.getBalance(id);
                return ResponseEntity.ok(userBalance);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving user balance: " + e.getMessage());
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> checkUserExists(@PathVariable Long id) {
        try {
            boolean userExists = userService.checkUserExists(id);
            if (userExists) {
                return ResponseEntity.ok("User exists");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking if user exists: " + e.getMessage());
        }
    }
}
