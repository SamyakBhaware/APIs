package com.API5.Task.service;

import com.API5.Task.exceptions.InvalidDataException;
import com.API5.Task.exceptions.ResourceNotFoundException;
import com.API5.Task.model.Task;
import com.API5.Task.model.User;
import com.API5.Task.repository.TaskRepository;
import com.API5.Task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User saveUser(User user){
        if(user.getUserName().isEmpty()){
            throw new InvalidDataException("Username cannot be Empty");
        }
        if(user.getPassword().isEmpty()){
            throw new InvalidDataException("Password cannot be Empty");
        }
         return userRepository.save(user);
    }



    public void deleteAllUsers(){
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new ResourceNotFoundException("No Users found to delete");
        }
        userRepository.deleteAll();
    }



    public User editUserById(String id, User newuser) {
        Optional<User> oldUser = userRepository.findById(id);
        if (oldUser.isPresent()) {
            User updatedUser = oldUser.get();
            updatedUser.setUserName(newuser.getUserName());
            updatedUser.setPassword(newuser.getPassword());
            return  userRepository.save(updatedUser);
        }
        else{
            throw new ResourceNotFoundException("User not found.");
        }
    }



    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new ResourceNotFoundException("No users found");
        }
            return users;
        }

    public User findByUsername (String userName){
        return userRepository.findByUserName(userName);

    }

    }

