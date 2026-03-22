package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "Users")
public class User {

    @Id
    private String id;

    @NonNull
    @Indexed(unique = true)
    private String username;


    @Indexed(unique = true)
    private String email;

    @NonNull
    private String password;

    private List<String> roles;

    @DBRef
    private List<Journal> journals;


}