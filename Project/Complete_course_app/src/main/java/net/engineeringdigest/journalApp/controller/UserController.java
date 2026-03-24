package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Service.EmailService;
import net.engineeringdigest.journalApp.Service.UserService;
import net.engineeringdigest.journalApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/api/User")
public class UserController{

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @PostMapping("/save")
    public User saveUser(@RequestBody User user){
        return userService.saveNewUser(user);
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("/deleteUser/{username}")
    public void deleteUser(@PathVariable String username){
        userService.deleteUserByUsername(username);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser){
        return userService.updateUser(updatedUser);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllUsers(){
        userService.deleteAllUsers();
    }

    @GetMapping("/weather/{city}")
    public ResponseEntity<String> getWeather(@PathVariable String city, @RequestBody String body){
          String greeting =  userService.getWeather(city);
        if(greeting != null){
            emailService.sendEmail("samyakbhawareytl@gmail.com", "Test Email from Journal App", body);
            return new ResponseEntity<>(greeting, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}