package com.example.studentapi.service.impl;

import com.example.studentapi.exception.ResourceNotFoundException;
import com.example.studentapi.model.Student;
import com.example.studentapi.repository.StudentRepository;
import com.example.studentapi.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;

	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Override
	@Transactional
	public Student create(Student student) {
		return studentRepository.save(student);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Student> getAll(String query, Pageable pageable) {
		if (query == null || query.isBlank()) {
			return studentRepository.findAll(pageable);
		}
		return studentRepository.findByNameContainingIgnoreCaseOrCourseContainingIgnoreCase(query, query, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Student> getById(Long id) {
		return studentRepository.findById(id);
	}

	@Override
	@Transactional
	public Student update(Long id, Student student) {
		Student existing = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));

		existing.setName(student.getName());
		existing.setEmail(student.getEmail());
		existing.setCourse(student.getCourse());
		existing.setAge(student.getAge());

		return studentRepository.save(existing);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Student existing = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
		studentRepository.delete(existing);
	}
}


