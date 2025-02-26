package com.Manager.ContactManager.Repo;

import com.Manager.ContactManager.model.contactManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface contactManagerRepo extends MongoRepository<contactManager, String > {
}
