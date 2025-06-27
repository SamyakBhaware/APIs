package com.API5.Task.controller;

import com.API5.Task.model.Task;
import com.API5.Task.service.TaskService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Tasks")
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping("/save")
    public ResponseEntity<Task> saveTask(@RequestBody Task task){
       // return taskService.saveTask(task);
        return new ResponseEntity<>(taskService.saveTask(task), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllTasks(){
        taskService.deleteAllTasks( );
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Task> editTaskById(@PathVariable String id,@RequestBody Task newTask) {
        return new ResponseEntity<>(taskService.editTaskById(id, newTask), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Task>> getTaskById(){
        return new ResponseEntity<>(taskService.getAll(), HttpStatus.FOUND);
    }
}
