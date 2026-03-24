package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.Service.UserService;
import net.engineeringdigest.journalApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @PostMapping("/saveUser")
    public User saveUser(@RequestBody User user){
       return userService.saveNewUser(user);
    }

    @PostMapping("/saveAdmin")
    public User saveAdmin(@RequestBody User user){
        return userService.saveAdmin(user);
    }

}
