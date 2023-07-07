package com.example.finances.service;

// Base Dependencies
import java.util.List;
import java.util.Optional;

// Local Dependencies
import com.example.finances.model.User;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long userId);
    User saveUser(User user);
    User updateUser(User user);
    void deleteUser(Long userId);
}

