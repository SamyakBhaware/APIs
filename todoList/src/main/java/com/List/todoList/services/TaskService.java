package com.List.todoList.services;

import com.List.todoList.Modal.Task;
import com.List.todoList.Repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    TaskRepo taskRepo;

    @Autowired
    TaskService(TaskRepo taskRepo){
        this.taskRepo = taskRepo;
    }

    public List<Task> getAllTasks(){
        return taskRepo.findAll();
    }

     public Task getTaskById(String id){
        Optional<Task> task = taskRepo.findById(id);
        return task.orElse(null);
    }

    public Task CreateOrUpdateTask(Task task){
        return taskRepo.save(task);
    }

    public void deleteTaskById(String id){
          taskRepo.deleteById(id);
    }

    public List<Task> createMultipleTasks(List<Task> tasks){
        return taskRepo.saveAll(tasks);
    }

}
