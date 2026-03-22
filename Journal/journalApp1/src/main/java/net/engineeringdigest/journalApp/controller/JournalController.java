package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Exceptions.*;
import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.JournalService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalController {

    private final JournalService journalService;
    private final UserRepository userRepository;


    public JournalController(JournalService journalService, UserRepository userRepository) {
        this.journalService = journalService;
        this.userRepository = userRepository;
     }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<?> createJournal(@RequestBody Journal journal) throws DataSaveException {
        try{
            journal.setCreatedAt(LocalDateTime.now());
            journalService.saveJournal(journal);
            return ResponseEntity.ok("Journal created successfully");
        }
        catch(DataSaveException e){
             throw new DataSaveException("Failed to save journal: " + e.getMessage());
        }
    }

    @Transactional
    @GetMapping("/all")
    public ResponseEntity<?> showAllJournals() throws DataFetchException {
        try {
            Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            User userInDB = userRepository.findByUsername(username);
            List<Journal> journalList = userInDB.getJournals();
            return ResponseEntity.ok(journalList);
        }
        catch(DataFetchException e){
             throw new DataFetchException("Failed to fetch journals: " + e.getMessage());

        }
    }

    @Transactional
    @GetMapping("/trashed")
    public ResponseEntity<List<Journal>> showTrashedJournals()throws DataSaveException{
        try{
            Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            List<Journal> trashedJournals = journalService.showTrashedJournals()
                    .stream().filter(journal -> journal.getUserId().equals(username)).toList();

            return ResponseEntity.ok(trashedJournals);
        }
        catch (DataSaveException e){
             throw new DataSaveException("Failed to fetch trashed journals: " + e.getMessage());
        }
    }

    @Transactional
    @PostMapping("/restore")
    public ResponseEntity<?> restoreJournals() throws DataSaveException {
        try {
            journalService.restoreJournals();
            return ResponseEntity.ok("Journal updated successfully");
        }
        catch (DataSaveException e){
                throw new DataSaveException("Failed to restore journals: " + e.getMessage());
        }
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateJournal(@PathVariable String id, @RequestBody Journal updatedJournal) throws DataUpdateException {
        try {
            Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            User userInDB = userRepository.findByUsername(username);
            List<Journal> journalList = userInDB.getJournals();
            Journal foundJournal = journalList.stream().filter(journal -> journal.getId().equals(id)).findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Journal not found with id: " + id));
            foundJournal.setTitle(updatedJournal.getTitle());
            foundJournal.setContent(updatedJournal.getContent());
            foundJournal.setUpdatedAt(LocalDateTime.now());
            journalService.saveJournal(foundJournal);
            return ResponseEntity.ok("Journal updated successfully");
        } catch (DataUpdateException e) {
            throw new DataUpdateException("Failed to update journal: " + e.getMessage());
        }
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable String id) throws DataDeleteException {
        try {
            Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            User userInDB = userRepository.findByUsername(username);
            List<Journal> journalList = userInDB.getJournals();
            Journal todeleteJournal = journalList.stream().filter(journal -> journal.getId().equals(id)).findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Journal not found with id: " + id));
            journalService.deleteJournalById(todeleteJournal.getId());
            return ResponseEntity.ok("Journal deleted successfully");
        } catch (DataDeleteException e) {
            throw new DataDeleteException("Failed to delete journal: " + e.getMessage());
        }
    }

    @DeleteMapping("/trash")
    public void trashJournal(String id) throws ResourceNotFoundException {
        try {
            journalService.trashJournalById(id);
        } catch (ResourceNotFoundException e) {
            System.out.println("Failed to trash journal: " + e.getMessage());
        }
    }
}
