package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Exceptions.ResourceNotFoundException;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final UserService userService;

    public PublicController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok("User created successfully");
    }

    @GetMapping
    public String getSomething() {
        return "Hello from the public controller!";
    }


}
