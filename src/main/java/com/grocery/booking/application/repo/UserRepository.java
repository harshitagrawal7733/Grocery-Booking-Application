package com.grocery.booking.application.repo;

import com.grocery.booking.application.entity.GroceryItem;
import com.grocery.booking.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
