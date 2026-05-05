package com.StudentAPI.studentAPI.repository;

import com.StudentAPI.studentAPI.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    Optional<StudentEntity> findByName(String name);
    boolean existsByEmail(String email);
    void deleteByEmail(String email);
}
