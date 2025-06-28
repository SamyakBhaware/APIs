package com.API5.Task.controller;

import com.API5.Task.model.Task;
import com.API5.Task.model.User;
import com.API5.Task.service.TaskService;
import com.API5.Task.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;


    //save
    //delete
    //edit
    //show

    @PostMapping("/save/{userName}")
    public void saveTasksByUserName(@RequestBody Task task, @PathVariable String userName){
        taskService.saveTasksByUserName(task, userName);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable ObjectId id){
        taskService.deleteTaskById(id);
    }

    @PutMapping("/edit/{id}")
    public void editTaskById(@RequestBody Task task, @PathVariable ObjectId id){
        taskService.editTaskById(id, task);
    }

    @GetMapping("/show/{userName}")
    public ResponseEntity<List<Task>> showAllTasksByUserName(@PathVariable String userName){
         return taskService.showAllTasksByUserName(userName);
    }
}
