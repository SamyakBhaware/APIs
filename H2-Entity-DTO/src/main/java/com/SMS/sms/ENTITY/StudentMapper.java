package com.SMS.sms.ENTITY;

import com.SMS.sms.DTO.StudentDTO;
import org.springframework.stereotype.Component;


// id name age email className

@Component
public class StudentMapper {
    public StudentDTO toDTO(StudentEntity studentEntity) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(studentEntity.getId());
        studentDTO.setName(studentEntity.getName());
        studentDTO.setClassName(studentEntity.getClassName());
        return studentDTO;
    }

    public StudentEntity toEntity(StudentDTO studentDTO) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentDTO.getId());
        studentEntity.setClassName(studentDTO.getClassName());
        studentEntity.setName(studentDTO.getName());
        return studentEntity;
    }

}

