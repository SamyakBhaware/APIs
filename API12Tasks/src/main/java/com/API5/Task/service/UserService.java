package com.API5.Task.service;

import com.API5.Task.model.Task;
import com.API5.Task.repository.TaskRepository;
import com.API5.Task.repository.UserRepository;
import com.API5.Task.model.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    //save
    //delete
    //edit
    //show


    public User saveUser(User user){
        return userRepository.save(user);
    }

    //public void deleteUserById (ObjectId id){
//        userRepository.deleteById(id);
//    }

    public User editById(String userName, User newUser) {
        User foundUser = userRepository.findByUserName(userName);
        if (foundUser != null) {
            foundUser.setUserName(newUser.getUserName());
            foundUser.setPassword(newUser.getPassword());
            return userRepository.save(foundUser);
        }
        throw new RuntimeException("user not found");
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public List<User> showAll(){
        return userRepository.findAll();
    }

}
