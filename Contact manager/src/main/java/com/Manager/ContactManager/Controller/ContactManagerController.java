package com.Manager.ContactManager.Controller;

import com.Manager.ContactManager.Service.contactManagerService;
import com.Manager.ContactManager.model.contactManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ContactList")
public class ContactManagerController {

    @Autowired
    contactManagerService contactManagerService;

    @GetMapping()
    public List<contactManager> getAllContact(){
        return contactManagerService.getAllContact();
    }

    @GetMapping("/id/{id}")
    public Optional<contactManager> getContactById(@PathVariable String id){
        return contactManagerService.getContactById(id);
    }

    @PostMapping()
    public contactManager saveContact(@RequestBody contactManager contactManager){
        return contactManagerService.saveContact(contactManager);
    }

    @DeleteMapping("/{Id}")
    public void deleteContactById(@PathVariable String Id){
        contactManagerService.deleteContactById(Id);
    }

    @PostMapping("/saveAll")
    public List<contactManager> saveAll(@RequestBody List<contactManager> contactManager){
        return contactManagerService.saveAll(contactManager);
    }

}
