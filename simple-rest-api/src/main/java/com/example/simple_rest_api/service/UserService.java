package com.example.simple_rest_api.service;

import com.example.simple_rest_api.model.User;
import com.example.simple_rest_api.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    // ✅ 1. User create karna
    public User createUser(User user) {
        // Pehle check karo ki email already exist na kare
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }
        return userRepo.save(user);
    }

    // ✅ 2. Saare users laana
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // ✅ 3. ID ke basis pe user laana
    public Optional<User> getUserById(String  id) {
        return userRepo.findById(id);
    }

    // ✅ 4. User delete karna
    public void deleteUser(String id) {
        userRepo.deleteById(id);
    }
}


