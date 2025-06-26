package com.example.bookmanager.service;

import com.example.bookmanager.entity.User;
import java.util.Optional;

public interface UserService {
    User register(String username, String password, String role);
    String login(String username, String password);
    Optional<User> findByUsername(String username);
    User getCurrentUser();
} 