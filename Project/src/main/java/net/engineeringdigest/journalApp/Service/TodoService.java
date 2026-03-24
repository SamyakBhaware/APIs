package net.engineeringdigest.journalApp.Service;


import net.engineeringdigest.journalApp.Repo.TodoRepo;
import net.engineeringdigest.journalApp.Repo.UserRepo;
import net.engineeringdigest.journalApp.model.Todo;
import net.engineeringdigest.journalApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepo toDoRepo;
    private final UserRepo userRepo;

    public TodoService(TodoRepo toDoRepo, UserRepo userRepo) {
        this.toDoRepo = toDoRepo;
        this.userRepo = userRepo;
    }


    // saveTask
    // getTaskById
    // deleteTaskById
    // updateTaskById

    @Transactional
    public Todo saveToDo(Todo todo,  User user) { // todo and user pass kar rahe hai
        User userInDB = userRepo.findByUsername(user.getUsername()); // user find kara
        Todo savedTodo = toDoRepo.save(todo); // todo save kara
        userInDB.getTodos().add(savedTodo); // user se todo list fetch karke usme ye naya todo add kara
        userRepo.save(userInDB); // phir user ko save kara
        return savedTodo;

    }

    public void deleteTodoById(String id){
        toDoRepo.deleteById(id);
    }

    @Transactional
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
