package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.repository.JournalRepository;
import net.engineeringdigest.journalApp.repository.TrashRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalService {

    private static final Logger logger = LoggerFactory.getLogger(JournalService.class);
    private final JournalRepository journalRepository;
    private final TrashRepo trashRepository;
    private final EmailService emailService;

    public JournalService(JournalRepository journalRepository, TrashRepo trashRepository, EmailService emailService) {
        this.journalRepository = journalRepository;
        this.trashRepository = trashRepository;
        this.emailService = emailService;
    }

    public void saveJournal(Journal journal) {
        if (journal == null) {
            logger.warn("Attempted to save null user");
            return;
        }
        journalRepository.save(journal);
        logger.info("Journal saved successfully: {}", journal);
    }
    public void deleteJournalById(String id) {
        if (id == null || id.isEmpty()) {
            logger.warn("Cannot delete user: id is null or empty");
            return;
        }
        Journal userInDB = journalRepository.findById(id).orElse(null);
        if (userInDB != null) {
            journalRepository.delete(userInDB);
            logger.info("User deleted by id: {}", id );
        } else {
            logger.warn("User not found for deletion: {}", id);
        }
    }

    public List<Journal> showAllJournals(){
        List<Journal> journals = journalRepository.findAll();
        logger.info("All journals retrieved successfully, count: {}", journals.size());
        return journals;
    }

    public List<Journal> showTrashedJournals(){
        List<Journal> journals = trashRepository.findAll();
        logger.info("All trash journals retrieved successfully, count: {}", journals.size());
        return journals;
    }

    public Journal updateJournalById(String id) {
        if (id == null || id.isEmpty()) {
            logger.warn("Cannot update journal: id is null or empty");
            return null;
        }
        Journal journalInDB = journalRepository.findById(id).orElse(null);
        if (journalInDB != null) {
            logger.info("Journal found for update: {}", id);
            return journalInDB;
        } else {
            logger.warn("Journal not found for update: {}", id);
            return null;
        }
    }

    public void trashJournalById(String id) {
        if (id == null || id.isEmpty()) {
            logger.warn("Cannot trash journal: id is null or empty");
            return;
        }
        Journal journalInDB = journalRepository.findById(id).orElse(null);
        trashRepository.save(journalInDB);
        journalRepository.delete(journalInDB);
        if (journalInDB != null) {
            // Implement logic to mark the journal as trashed instead of deleting it
            logger.info("Journal trashed successfully: {}", id);
        } else {
            logger.warn("Journal not found for trashing: {}", id);
        }
    }

    public void restoreJournals() {
        List<Journal> trashedJournals = trashRepository.findAll();
        for (Journal journal : trashedJournals) {
            journalRepository.save(journal);
            trashRepository.delete(journal);
            logger.info("Journal restored successfully: {}", journal.getId());
        }
    }

}
