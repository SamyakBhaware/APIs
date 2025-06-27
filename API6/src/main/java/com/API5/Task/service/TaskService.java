package com.API5.Task.service;

import com.API5.Task.exceptions.InvalidDataException;
import com.API5.Task.exceptions.ResourceNotFoundException;
import com.API5.Task.model.Task;
import com.API5.Task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public Task saveTask(Task task){
        if(task.getName() == null || task.getName().isEmpty() ){
            throw new InvalidDataException("Task name cannot be Empty");
        }
        if(task.getDescription() == null || task.getDescription().isEmpty() ){
            throw new InvalidDataException("Task description cannot be Empty");
        }
         return taskRepository.save(task);
    }



    public void deleteAllTasks(){
        List<Task> tasks = taskRepository.findAll();
        if(tasks.isEmpty()){
            throw new ResourceNotFoundException("No Tasks found to delete");
        }
        taskRepository.deleteAll();
    }



    public Task editTaskById(String id, Task newTask) {
        Optional<Task> oldTask = taskRepository.findById(id);
        if (oldTask.isPresent()) {
            Task updatedTask = oldTask.get();
            updatedTask.setName(newTask.getName());
            updatedTask.setDescription(newTask.getDescription());
            return  taskRepository.save(updatedTask);
        }
        else{
            throw new ResourceNotFoundException("Task with id " + id + " not found.");
        }
    }



    public List<Task> getAll(){
        List<Task> tasks = taskRepository.findAll();
        if(tasks.isEmpty()){
            throw new ResourceNotFoundException("No tasks found");
        }
            return tasks;
        }

    }

