package com.StudentAPI.studentAPI.controller;

import com.StudentAPI.studentAPI.dto.CreateStudentDTO;
import com.StudentAPI.studentAPI.dto.DeleteStudentDTO;
import com.StudentAPI.studentAPI.dto.StudentDTO;
import com.StudentAPI.studentAPI.dto.UpdateStudentDTO;
import com.StudentAPI.studentAPI.entity.StudentEntity;
import com.StudentAPI.studentAPI.mapper.StudentMapper;
import com.StudentAPI.studentAPI.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/getAll")
    public List<StudentDTO> getAllStudents() {
        List<StudentEntity> students = studentRepository.findAll();
        return students.stream()
                .map(StudentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable String name) {
        Optional<StudentEntity> entity = studentRepository.findByName(name);
        if (entity.isPresent()) {
            return ResponseEntity.ok(StudentMapper.toResponseDTO((entity.get())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<StudentEntity> createStudent(@RequestBody CreateStudentDTO createStudentDTO) {
        StudentEntity entity = StudentMapper.toCreateStudentEntity(createStudentDTO);
        return ResponseEntity.ok(studentRepository.save(entity));
     }

    @PutMapping("/update")
    public ResponseEntity<StudentEntity> updateStudent(@RequestBody UpdateStudentDTO updateStudentDTO) {
        if (studentRepository.existsByEmail(updateStudentDTO.getEmail())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentRepository.save
                (StudentMapper.toUpdateStudentEntity(updateStudentDTO)));

    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteStudent(@RequestBody DeleteStudentDTO deleteStudentDTO,
                                              @PathVariable String name) {
        StudentEntity deleteEntity = StudentMapper.toDeleteStudentEntity(name, deleteStudentDTO.getEmail());
        if (studentRepository.existsByEmail(deleteEntity.getEmail())) {
            return ResponseEntity.notFound().build();
        }
        studentRepository.deleteByEmail(deleteEntity.getEmail());
        return ResponseEntity.noContent().build();
    }
}
