package net.engineeringdigest.journalApp.Service;


import net.engineeringdigest.journalApp.Repo.ToDoRepo;
import net.engineeringdigest.journalApp.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    ToDoRepo toDoRepo;

    // saveTask
    // saveAllTasks
    // getTaskById
    // getAllTasks
    // deleteTaskById
    // deleteAllTasks
    // updateTaskById

    public Todo saveToDo(Todo todo){
        return toDoRepo.save(todo);
    }

    public List<Todo> saveAllToDo(List<Todo> todos){
        return toDoRepo.saveAll(todos);
    }

    public List<Todo> getAllTodos(){
        return toDoRepo.findAll();
    }

    public Optional<Todo> getTodoById(String id){
        return toDoRepo.findById(id);
    }

    public void deleteAllToDo(){
        toDoRepo.deleteAll();
    }

    public void deleteAllById(String id){
        toDoRepo.deleteById(id);
    }

    public Todo updateTodo(String id,Todo updatedtodo){
        Optional<Todo> optionalTodo = toDoRepo.findById(id);
        if(optionalTodo.isPresent()){
            Todo existingTodo = optionalTodo.get();
            existingTodo.setTask(updatedtodo.getTask());
            existingTodo.setDescription(updatedtodo.getDescription());
            return toDoRepo.save(existingTodo);
        }

        return null;
    }

}
