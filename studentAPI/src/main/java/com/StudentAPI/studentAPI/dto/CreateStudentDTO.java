package com.StudentAPI.studentAPI.dto;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CreateStudentDTO {
    private String name;
    private String email;
    private String course;
    private Integer age;
    private LocalDateTime createdAt;
}
