package net.engineeringdigest.journalApp.Service;


import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Repo.UserRepo;
import net.engineeringdigest.journalApp.apiResponse.WhetherResponse;
import net.engineeringdigest.journalApp.model.Todo;
import net.engineeringdigest.journalApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final TodoService todoService;
    private final WhetherService whetherService;

    public UserService (UserRepo userRepo, TodoService todoService, WhetherService whetherService) {
        this.userRepo = userRepo;
        this.todoService = todoService;
        this.whetherService = whetherService;

    }





    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

//    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);
    // saveNewUser
    // saveUser
    // deleteUserByUsername
    // deleteAllUsers
    // updateUser
    // findByUserName
    // getAllUsers

    @Transactional
    public User saveNewUser(User user){
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(Arrays.asList("USER"));
        return userRepo.save(user);
    }

    @Transactional
    public User saveAdmin(User user){
        try {
            String encodedPassword = encoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setRoles(Arrays.asList("ADMIN"));
            return userRepo.save(user);
        } catch (Exception e) {
            log.error("There seems an error, check if the user already exists ",e.getMessage());
            throw new RuntimeException("Error occurred while saving the Admin :", e);
        }
    }

    @Transactional
    public User saveUser(User user){
        return userRepo.save(user);
    }


    @Transactional
    public void deleteUserByUsername(String username){
        User userInDB = userRepo.findByUsername(username);
        List<Todo> todos = userInDB.getTodos();
        for (Todo todo : todos) {
            todoService.deleteTodoById(todo.getId());
        }
        todos.clear();
        userRepo.delete(userInDB);

    }

    public void deleteAllUsers(){
        userRepo.deleteAll();
    }

    @Transactional
    public ResponseEntity<?> updateUser(User updatedUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDB = userRepo.findByUsername(username);
        userInDB.setUsername(updatedUser.getUsername());
        userInDB.setPassword(updatedUser.getPassword());
        saveNewUser(userInDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    public User findByUserName(String username){
        return userRepo.findByUsername(username);
    }

    public List<User> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getAuthorities().toString();
        List roles = Collections.singletonList(authentication.getAuthorities()
                .stream()
                .anyMatch(x -> x.
                        getAuthority().equals("ADMIN")));
        if(roles.isEmpty()){
            return null;
        }
        return userRepo.findAll();
    }

    public String getWeather(String city){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WhetherResponse whetherResponse = whetherService.getWhether(city);

        int feelslike = whetherResponse.getCurrent().getFeelslike();
        int temperature = whetherResponse.getCurrent().getTemperature();
        int windSpeed = whetherResponse.getCurrent().getWind_speed();


        String name = authentication.getName();
        String string = " The weather in " + city +
                        " feels like " + feelslike +
                        " with a temperature is " +  temperature + "°C"
                + " and wind speed of " + windSpeed + " km/h.";

        return "Hello "+name + string;
    }
}
