package com.example.studentapi.repository;

import com.example.studentapi.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

	Page<Student> findByNameContainingIgnoreCaseOrCourseContainingIgnoreCase(String name, String course, Pageable pageable);
}


