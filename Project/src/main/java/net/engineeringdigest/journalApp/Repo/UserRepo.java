package net.engineeringdigest.journalApp.Repo;

import net.engineeringdigest.journalApp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User, String>{
    User findByUsername(String username);
    Void deleteByUsername(String username);
 }
