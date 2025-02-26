package com.Manager.ContactManager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "contactManagerDB" )
public class contactManager {
    @Id
    String id;
    String phNumber;
    String name;


    public contactManager(String id, String name, String phNumber){
        this.id = id;
        this.name= name;
        this.phNumber = phNumber;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String  phNumber) {
        this.phNumber = phNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
