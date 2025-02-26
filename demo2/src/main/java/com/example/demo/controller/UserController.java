package com.example.demo.controller;


import com.example.demo.model.User;
import com.example.demo.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;

    }

    @PostMapping
    public User addUser(@RequestBody User user){
        return userService.addUser(user);

    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

}

