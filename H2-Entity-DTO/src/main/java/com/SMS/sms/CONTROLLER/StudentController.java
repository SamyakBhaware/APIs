package com.SMS.sms.CONTROLLER;

import com.SMS.sms.DTO.StudentDTO;
import com.SMS.sms.ENTITY.StudentEntity;
import com.SMS.sms.ENTITY.StudentMapper;
import com.SMS.sms.REPOSITORY.StudentRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    public StudentController(StudentRepository studentRepository, StudentMapper studentMapper) {
         this.studentMapper = studentMapper;
            this.studentRepository = studentRepository;
    }

    @GetMapping("/name/{name}")
    public StudentDTO getStudentByName(@PathVariable String name) {
        StudentEntity studentEntity = studentRepository.findByName(name);
        return studentMapper.toDTO(studentEntity);
    }


    @PostMapping("/add")
    public StudentEntity createStudent(@RequestBody StudentEntity studentEntity) {
        studentRepository.save(studentEntity);
        return studentEntity;
    }

    @GetMapping("/getAll")
    public Iterable<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }

}
