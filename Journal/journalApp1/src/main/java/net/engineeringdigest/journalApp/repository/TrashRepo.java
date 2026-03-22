package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.Journal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrashRepo extends MongoRepository<Journal, String> {
//    List<Journal> findByJournalId(long journalId);
}
