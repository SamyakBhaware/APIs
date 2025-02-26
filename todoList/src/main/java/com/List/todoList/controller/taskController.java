package com.List.todoList.controller;

import com.List.todoList.Modal.Task;
import com.List.todoList.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class taskController {

    private final TaskService taskService;

    @Autowired
    public taskController(TaskService taskService){
    this.taskService = taskService; }

    @GetMapping
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable String id){
        return taskService.getTaskById(id);
    }

    @PostMapping
    public Task CreateOrUpdateTask(@RequestBody Task task){
        return taskService.CreateOrUpdateTask(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTaskById(@PathVariable String id){
        taskService.deleteTaskById(id);
    }

    @PostMapping("/bulk")
    public List<Task> createMultipleTasks(@RequestBody List<Task> tasks){
        return taskService.createMultipleTasks(tasks);
    }
}
