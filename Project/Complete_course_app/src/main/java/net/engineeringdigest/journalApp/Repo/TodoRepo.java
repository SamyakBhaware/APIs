package net.engineeringdigest.journalApp.Repo;

import net.engineeringdigest.journalApp.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepo extends MongoRepository<Todo, String>{
}
