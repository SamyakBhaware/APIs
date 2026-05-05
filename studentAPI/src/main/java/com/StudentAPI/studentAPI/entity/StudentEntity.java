package com.StudentAPI.studentAPI.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Student_Name", nullable = false)
    private String name;
    @Column(name = "Student_Email", nullable = false, unique = true)
    private String email;
    @Column( name = "Student_Course", nullable = false)
    private String course;
    @Column(name = "Student_Age", nullable = false)
    private Integer age;

    @Column(name = "created_at", updatable = false)
    @CurrentTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
