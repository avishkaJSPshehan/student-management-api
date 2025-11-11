package com.example.studentapi.service;

import com.example.studentapi.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StudentService {

	Student create(Student student);

	Page<Student> getAll(String query, Pageable pageable);

	Optional<Student> getById(Long id);

	Student update(Long id, Student student);

	void delete(Long id);
}


