package com.StudentManagent.StudentManagent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentRepository repo;

    @Autowired
    private StudentMapper mapper;

    @GetMapping("/get/{name}")
    public StudentDTO getAll(@PathVariable String name) {
        return mapper.toDTO(repo.findByName(name));
    }

    @PostMapping("/create")
    public StudentEntity create(@RequestBody StudentEntity student) {
        if(student.getConfirmPassword() == null || !student.getConfirmPassword().equals(student.getPassword())) {
            throw new IllegalArgumentException("Password and Confirm Password do not match");
        }
        else {
            student.setConfirmPassword(student.getConfirmPassword());
            return repo.save(student);
        }
    }

    @PostMapping("/createAll")
    public List<StudentEntity> createAll(@RequestBody List<StudentEntity> students) {

        List<StudentEntity> studentEntities = new ArrayList<>();
        for (StudentEntity student : students) {
            if (student.getConfirmPassword() == null || !student.getConfirmPassword().equals(student.getPassword())) {
                throw new IllegalArgumentException("Password and Confirm Password do not match for student: " + student.getName());
            } else {
                student.setConfirmPassword(student.getConfirmPassword());
                studentEntities.add(student);
            }
        }
        return repo.saveAll(studentEntities);
    }

        @PutMapping("/update/{name}")
        public StudentEntity update (@PathVariable String name, @RequestBody StudentEntity newstudent){
            StudentEntity oldstudent = repo.findByName(name);
            oldstudent.setName(newstudent.getName());
            oldstudent.setEmail(newstudent.getEmail());
            oldstudent.setAge(newstudent.getAge());
            oldstudent.setPassword(newstudent.getPassword());
            if (newstudent.getConfirmPassword() == null || !newstudent.getConfirmPassword().equals(newstudent.getPassword())) {
                throw new IllegalArgumentException("Password and Confirm Password do not match");
            } else {
                oldstudent.setConfirmPassword(newstudent.getConfirmPassword());
                return repo.save(oldstudent);
            }
        }

        @DeleteMapping("/{id}")
        public void delete (@PathVariable Integer id){
            repo.deleteById(id);
        }

    }
