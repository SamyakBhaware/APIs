package com.API5.Task.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "users")

    @Data
    @NoArgsConstructor
public class User{
        private ObjectId id;
        @Indexed(unique = true)
        @NonNull
        private String userName;
        @NonNull
        private String password;

        @DBRef
        private List<Task> tasks = new ArrayList<>();
    }

