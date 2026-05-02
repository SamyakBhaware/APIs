package com.SMS.sms.REPOSITORY;

import com.SMS.sms.ENTITY.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    StudentEntity findByName(String name);
}
