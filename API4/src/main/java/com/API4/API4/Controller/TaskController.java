package com.API4.API4.Controller;

import com.API4.API4.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.API4.API4.model.Task;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Tasks")

public class TaskController{

@Autowired
TaskService taskService;

@GetMapping("/get/{id}")
public ResponseEntity<Task> getTaskById(@PathVariable String id){
    Optional<Task> foundTask = taskService.getTaskById(id);
    if(foundTask.isPresent()){
        return new ResponseEntity<>(foundTask.get(),HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}


@GetMapping("/getAll")
public ResponseEntity<List<Task>> getAllTasks(){
     List<Task> tasks = taskService.getAllTasks();
     return new ResponseEntity<>(tasks, HttpStatus.OK);
}

@PostMapping("/save")
public ResponseEntity<Task> saveTask(@RequestBody Task task){
    Task savedTask = taskService.saveTask(task);
    return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
}

@PostMapping("/saveAll")
public ResponseEntity<List<Task>> saveAllTasks(@RequestBody List<Task> tasks){
    List<Task> savedTasks = taskService.saveAllTasks(tasks);
      return new ResponseEntity<>(savedTasks, HttpStatus.CREATED);
}

@DeleteMapping("/delete/{id}")
public ResponseEntity deleteTaskById(@PathVariable String id){
     taskService.deleteTaskById(id);
     return new ResponseEntity(HttpStatus.OK);
}

@DeleteMapping("/deleteAll")
public ResponseEntity deleteAll(){
     taskService.deleteAll();
    return new ResponseEntity(HttpStatus.OK);

}

@PutMapping("/update/{id}")
public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody Task updateTask) {
    Task updatedTask = taskService.updateTask(id, updateTask);
    if (updatedTask != null) {
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
}

