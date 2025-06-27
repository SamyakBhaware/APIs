package com.API4.API4.Service;


import com.API4.API4.Repository.TaskRepository;
import com.API4.API4.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    //getTaskById
    //getAllTasks
    //save
    //SaveAll
    //deleteById
    //deleteAll
    //updateTaskById

    public Optional<Task> getTaskById(String id){
        return taskRepository.findById(id);
    }


    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }


    public Task saveTask(Task task){
        return taskRepository.save(task);
    }

    public List<Task> saveAllTasks(List<Task> tasks){
        return taskRepository.saveAll(tasks);
    }


    public String deleteTaskById(String id){
        if(taskRepository.existsById(id)){
            taskRepository.deleteById(id);
            return id + " Deleted successfully";
        } else {
            return "Task not found";
        }
    }

    public void deleteAll(){
        taskRepository.deleteAll();
    }


    public Task updateTask(String id, Task updateTask){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            Task existingTodo = optionalTask.get();
            existingTodo.setTask(updateTask.getTask());
            existingTodo.setDescription(updateTask.getDescription());
            return taskRepository.save(existingTodo);
        }
        return null;
    }
 }

