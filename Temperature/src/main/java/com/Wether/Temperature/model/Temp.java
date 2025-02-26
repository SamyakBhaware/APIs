package com.Wether.Temperature.model;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.Min;


@Getter
@Setter

@Document(collection = "tempDB")
public class Temp {
    @Id
    private String id;

    @NotNull(message = "Time cannot be null")
    @Min(value = 0, message = "Time should be positive")
    private Integer time;

    @NotNull(message = "Temperature cannot be null")
    private Integer temp;

    public Temp(String id, int time, int temp){
        this.id = id;
        this.temp = temp;
        this.time = time;
    }
}
