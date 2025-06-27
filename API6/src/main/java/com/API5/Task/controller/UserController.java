package com.API5.Task.controller;

import com.API5.Task.model.Task;
import com.API5.Task.model.User;
import com.API5.Task.service.TaskService;
import com.API5.Task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody User user){
       // return taskService.saveTask(task);
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllUsers(){
        userService.deleteAllUsers( );
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @PutMapping("/edit/{userName}")
    public ResponseEntity<User> findByUsername(@PathVariable String userName,@RequestBody User newUser) {
        return new ResponseEntity<>(userService.editUserById(userName, newUser), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.FOUND);
    }
}
