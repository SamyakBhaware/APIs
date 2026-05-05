package com.StudentAPI.studentAPI.mapper;

import com.StudentAPI.studentAPI.dto.CreateStudentDTO;
import com.StudentAPI.studentAPI.dto.StudentDTO;
import com.StudentAPI.studentAPI.dto.UpdateStudentDTO;
import com.StudentAPI.studentAPI.entity.StudentEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;



@Component
public class StudentMapper {
    private StudentMapper() {
        /* This utility class should not be instantiated */
    }


    public static StudentDTO toResponseDTO(StudentEntity entity) {
        StudentDTO dto = new StudentDTO();
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setCourse(entity.getCourse());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public static StudentEntity toUpdateStudentEntity(UpdateStudentDTO updateStudentDTO) {
          StudentEntity updateEntity = new StudentEntity();
            updateEntity.setName(updateStudentDTO.getName());
            updateEntity.setEmail(updateStudentDTO.getEmail());
            updateEntity.setCourse(updateStudentDTO.getCourse());
            updateEntity.setUpdatedAt(updateStudentDTO.getUpdatedAt());
            return updateEntity;
    }

    public static StudentEntity toCreateStudentEntity(CreateStudentDTO createStudentDTO) {
        StudentEntity entity = new StudentEntity();
        entity.setName(createStudentDTO.getName());
        entity.setEmail(createStudentDTO.getEmail());
        entity.setCourse(createStudentDTO.getCourse());
        entity.setAge(createStudentDTO.getAge());
        entity.setCreatedAt(createStudentDTO.getCreatedAt());
        return entity;
    }

    public static StudentEntity toDeleteStudentEntity(String name, String email) {
        StudentEntity deleteEntity = new StudentEntity();
        deleteEntity.setName(name);
        deleteEntity.setEmail(email);
        return deleteEntity;
    }
}
