package com.Management.User.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter

@Document(collection = "userDB")
public class User {
    @Id
    private String id;
    private int age;
    private String name;

    public User(String id, String name, int age){
        this.age = age;
        this.id = id;
        this.name = name;
    }


}
