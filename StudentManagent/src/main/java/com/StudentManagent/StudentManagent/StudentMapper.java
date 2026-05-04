package com.StudentManagent.StudentManagent;


import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentDTO toDTO(StudentEntity studentEntity) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(studentEntity.getName());
        studentDTO.setEmail(studentEntity.getEmail());
        return studentDTO;
    }

    public StudentEntity toEntity(StudentDTO studentDTO) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName(studentDTO.getName());
        studentEntity.setEmail(studentDTO.getEmail());
        return studentEntity;
    }
}
