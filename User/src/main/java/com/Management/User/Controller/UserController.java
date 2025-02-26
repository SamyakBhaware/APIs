package com.Management.User.Controller;

import com.Management.User.Model.User;
import com.Management.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
      UserService userService;


    @GetMapping("/id/{id}")
    Optional<User> getUserbyId(@PathVariable String id){
        return userService.getUserbyId(id);
    }

    @GetMapping("/getAll")
    List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/save")
    public User saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @PostMapping("/saveAll")
    public List<User> saveAllUsers(@RequestBody List<User> user){
        return userService.saveAllUsers(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById( @PathVariable String id){
        userService.deleteById(id);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll(){
        userService.deleteAll();
    }



}
