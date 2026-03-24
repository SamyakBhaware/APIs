package net.engineeringdigest.journalApp.controller;

 import net.engineeringdigest.journalApp.Service.TodoService;
 import net.engineeringdigest.journalApp.Service.UserService;
 import net.engineeringdigest.journalApp.model.User;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.security.core.Authentication;
 import org.springframework.web.bind.annotation.*;
 import net.engineeringdigest.journalApp.model.Todo;


 import java.util.*;
 import java.util.stream.Collectors;

@RestController
 @RequestMapping("/api/Todo")
 public class TodoController{

  @Autowired
  TodoService todoService;

  @Autowired
  UserService userService;

  @PostMapping("/save")
  public ResponseEntity<Todo> saveToDo(@RequestBody Todo todo){
   Authentication authentication =  org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
   String username = authentication.getName();
    User user = userService.findByUserName(username);
    Todo todo1 = todoService.saveToDo( todo, user);
    return new ResponseEntity<>(todo1, HttpStatus.CREATED);
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<List<Todo>> getTodoById(@PathVariable String id){
   Authentication authentication =  org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
   String username = authentication.getName();
   User user = userService.findByUserName(username);
   List<Todo> collect = user.getTodos().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
   if(!collect.isEmpty()){
    collect.get(0).getId();
    List<Todo> todos =  user.getTodos();
    if(todos != null && !todos.isEmpty()){
     return new ResponseEntity<>(todos, HttpStatus.OK);
    }
   }
   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteById(@PathVariable String id){
   Authentication authentication =  org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
   String username = authentication.getName();
   User user = userService.findByUserName(username);
   Todo todoInDB = user.getTodos().stream().filter(x -> x.getId().equals(id)).findFirst().get();
   if(todoInDB != null){
    todoService.deleteTodoById(id);
    return  new ResponseEntity<>(HttpStatus.OK);
   }
   return ResponseEntity.badRequest().body("Todo not found");

  }

  @PutMapping("/update/{id}")
  public ResponseEntity<Todo> updateTodo(@PathVariable String id, @RequestBody Todo updatedtodo){
   Authentication authentication =  org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
   String username = authentication.getName();
   User user = userService.findByUserName(username);
   Todo todoInDB = user.getTodos().stream().filter(x -> x.getId().equals(id)).findFirst().get();
   if(todoInDB != null){
    Todo updatedTodo = todoService.updateTodo(id, updatedtodo);
    return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
   }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }



 }