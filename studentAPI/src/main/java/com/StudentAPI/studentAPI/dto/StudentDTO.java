package com.StudentAPI.studentAPI.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentDTO {
    private String name;
    private String email;
    private String course;
    private LocalDateTime createdAt;
}
