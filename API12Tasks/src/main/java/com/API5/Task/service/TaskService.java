package com.API5.Task.service;

import com.API5.Task.model.Task;
import com.API5.Task.model.User;
import com.API5.Task.repository.TaskRepository;
import com.API5.Task.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    //save
    //delete
    //edit
    //show

    public  void saveTasksByUserName(Task newtask, String userName){
        User user = userRepository.findByUserName(userName);
        taskRepository.save(newtask);
        user.getTasks().add(newtask);
        userRepository.save(user);
    }

    public ResponseEntity<List<Task>> showAllTasksByUserName(String userName){
        User user = userRepository.findByUserName(userName);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Task> allTasks = user.getTasks();
        return new ResponseEntity<>(allTasks, HttpStatus.OK);
    }

    public  ResponseEntity<?> deleteTaskById(ObjectId id){
        Optional<Task> findTask = taskRepository.findById(id);
        if(findTask.isPresent()){
            Task taskToDelete = findTask.get();
            List<User> allUsers = userRepository.findAll();
            for(User user: allUsers){
                user.getTasks().removeIf(task -> task.getId().equals(taskToDelete.getId()));
                userRepository.save(user);
            }
            taskRepository.delete(taskToDelete);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public void editTaskById(ObjectId id, Task newTask){
        Optional<Task> findTask = taskRepository.findById(id);
        if(findTask.isPresent()){
            Task taskInDB = findTask.get();
            taskInDB.setTaskName(newTask.getTaskName());
            taskInDB.setTaskDescription(newTask.getTaskDescription());
            taskRepository.save(taskInDB);
        }

        else{
            throw new RuntimeException("Task not found");
        }
    }
}
