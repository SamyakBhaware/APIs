package net.engineeringdigest.journalApp.controller;

 import net.engineeringdigest.journalApp.Service.TodoService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.*;
 import net.engineeringdigest.journalApp.model.Todo;


 import java.util.*;

 @RestController
 @RequestMapping("/Todo")
 public class TodoController{

  @Autowired
  TodoService todoService;

  @PostMapping("/save")
  public Todo saveToDo(@RequestBody Todo todo){
   return todoService.saveToDo(todo);
  }

  @PostMapping("/saveAll")
  public List<Todo> saveAllToDo(@RequestBody List<Todo> todos){
   return todoService.saveAllToDo(todos);
  }

  @GetMapping("/getAll")
  public List<Todo> getAllTodos(){
   return todoService.getAllTodos();
  }

  @GetMapping("/get/{id}")
  public Todo getTodoById(@PathVariable String id){
   Optional<Todo> optionalTodo =  todoService.getTodoById(id);
   if(optionalTodo.isPresent()){
    return optionalTodo.get();
   }
   return null;
  }

  @DeleteMapping("/deleteAll")
  public void deleteAllToDo(){
   todoService.deleteAllToDo();
  }

  @DeleteMapping("/delete/{id}")
  public void deleteAllById(@PathVariable String id){
   todoService.deleteAllById(id);
  }

  @PutMapping("/update/{id}")
  public Todo updateTodo(@PathVariable String id, @RequestBody Todo updatedtodo){
    return todoService.updateTodo(id, updatedtodo);
   }


 }