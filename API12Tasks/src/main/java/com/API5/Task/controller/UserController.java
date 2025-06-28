package com.API5.Task.controller;

import com.API5.Task.model.Task;
import com.API5.Task.service.TaskService;
import com.API5.Task.service.UserService;
import com.API5.Task.model.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.SimpleTimeZone;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    //save
    //delete
    //edit
    //show

    @PostMapping("/save")
    public User save(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/show")
    public List<User> showUsers(){
        return userService.showAll();
    }

    @PutMapping()
    public ResponseEntity<User> updateUser(@RequestBody User newUser, @PathVariable String userName){
        try{
            User updaterUser = userService.editById(userName, newUser);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
