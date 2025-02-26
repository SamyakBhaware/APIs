package com.example.simple_rest_api.controller;


import com.example.simple_rest_api.model.User;
import com.example.simple_rest_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController (UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping
    List<User>  getAllusers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")

    public User getUserbyId(@PathVariable String id) {
        return userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found!"));
    }



    @DeleteMapping("/{id}")
    public void deleteUserbyId(@PathVariable String id) {
        userService.deleteUser(id);
    }
}




