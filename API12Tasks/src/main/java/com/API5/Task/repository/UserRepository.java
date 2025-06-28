package com.API5.Task.repository;


import com.API5.Task.model.Task;
import com.API5.Task.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    public User findByUserName(String userName);
}
