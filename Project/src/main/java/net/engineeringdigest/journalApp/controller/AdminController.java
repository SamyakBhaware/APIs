package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Service.UserService;
import net.engineeringdigest.journalApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> all = userService.getAllUsers();
        if (all != null && !all.isEmpty()) {
            return ResponseEntity.ok(all);
        }
            return ResponseEntity.noContent().build();
        }


    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody User user) {
        userService.saveAdmin(user);
        return ResponseEntity.ok("Admin created successfully");
    }


    }

