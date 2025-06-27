package com.API4.API4.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "Tasks")

public class Task {
    @Id
    private String id;
    private String task;
    private String description;

}

