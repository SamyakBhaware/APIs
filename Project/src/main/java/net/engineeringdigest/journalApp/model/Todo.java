package net.engineeringdigest.journalApp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter

@Document(collection = "Todos")
@Data
public class Todo {
    @Id
    private String id;

    private String task;
    private String description;

}
