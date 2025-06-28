package com.API5.Task.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "taskDB")

@Data
@NoArgsConstructor
public class Task {
    private ObjectId id;
    @NonNull
    private String taskName;
    private String taskDescription;
}

