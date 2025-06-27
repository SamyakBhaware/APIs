package com.API5.Task.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Document(collection = "Tasks")
public class Task {

    private String id;
    private String name;
    private String description;


}
