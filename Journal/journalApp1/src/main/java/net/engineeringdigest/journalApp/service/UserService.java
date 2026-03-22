package net.engineeringdigest.journalApp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;

import java.util.Arrays;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public User getUser(String username) {
        if (username == null || username.isEmpty()) {
            logger.warn("Cannot get user: username is null or empty");
            return null;
        }
        User user = userRepository.findByUsername(username);
        if (user != null) {
            logger.info("User retrieved successfully: {}", username);
        } else {
            logger.warn("User not found: {}", username);
        }
        return user;
    }

    public void saveUser(User user) {
        if (user == null) {
            logger.warn("Attempted to save null user");
            return;
        }
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);

        emailService.sendEmail( user.getEmail(), "Welcome to Journal App, "+ user.getUsername(),
                user.getUsername() + ", Your account has been created successfully.");

        logger.info("User saved successfully: {}", user.getUsername());
    }

    public void deleteUser(User user) {
        if (user != null) {
            userRepository.delete(user);
            logger.info("User deleted successfully: {}", user.getId());
        } else {
            logger.warn("Attempted to delete null user");
        }
    }

    public void deleteUserByUserName(String username) {
        if (username == null || username.isEmpty()) {
            logger.warn("Cannot delete user: username is null or empty");
            return;
        }
        User userInDB = userRepository.findByUsername(username);
        if (userInDB != null) {
            userRepository.delete(userInDB);
            logger.info("User deleted by username: {}", username);
        } else {
            logger.warn("User not found for deletion: {}", username);
        }
    }

    public void updateUser(User user) {
        if (user == null) {
            logger.warn("Attempted to update null user");
            return;
        }
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(encoder.encode(user.getPassword()));
            // Update other fields as necessary
            userRepository.save(existingUser);
            logger.info("User updated successfully: {}", user.getUsername());
        } else {
            logger.warn("User not found for update: {}", user.getId());
        }
    }

}
