package com.StudentManagent.StudentManagent;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.id.IncrementGenerator;

@Data
@Entity
@Table(name = "StudentDB")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "student_name", nullable = false)
    private String name;
    @Column(name = "student_age", nullable = false)
    private Integer age;
    @Column(name = "student_email", nullable = false, unique = true)
    private String email;
    @Column(name = "student_password", nullable = false)
    private String password;

    @Transient
    private String confirmPassword;
}
