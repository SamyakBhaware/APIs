package net.engineeringdigest.journalApp.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter

@Document(collection = "API3")
public class Todo {
    private String id;
    private String task;
    private String description;


}
