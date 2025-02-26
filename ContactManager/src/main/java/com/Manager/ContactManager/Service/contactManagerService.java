package com.Manager.ContactManager.Service;

import com.Manager.ContactManager.Repo.contactManagerRepo;
import com.Manager.ContactManager.model.contactManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class contactManagerService {

    @Autowired
    contactManagerRepo contactManagerrepo;

    public List<contactManager> getAllContact(){
        return contactManagerrepo.findAll();
    }

    public Optional<contactManager> getContactById(String id){
        return contactManagerrepo.findById(id);
    }

    public contactManager saveContact(contactManager contactManager){
         return contactManagerrepo.save(contactManager);
    }

    public void deleteContactById(String Id){
        contactManagerrepo.deleteById(Id);
    }

    public List<contactManager> saveAll(List<contactManager> contactManager){
       return contactManagerrepo.saveAll(contactManager);
    }

}
