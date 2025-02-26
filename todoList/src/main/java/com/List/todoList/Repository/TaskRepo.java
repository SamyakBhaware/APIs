package com.List.todoList.Repository;

import com.List.todoList.Modal.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends MongoRepository<Task, String> {
}
