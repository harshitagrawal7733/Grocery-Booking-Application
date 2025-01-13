package com.grocery.booking.application.service;


import com.grocery.booking.application.entity.User;
import com.grocery.booking.application.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User save(User  user){
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public boolean checkUserExists(Long userId) {
        return userRepository.existsById(userId); // Assuming you have a User entity and a UserRepository
    }


        public User getBalance(Long id) {
            // Fetch the user by id
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

            // Return the user's current balance as a Double
            return user;
        }
    }

