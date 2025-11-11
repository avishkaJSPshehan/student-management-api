package com.example.studentapi.controller;

import com.example.studentapi.exception.ResourceNotFoundException;
import com.example.studentapi.model.Student;
import com.example.studentapi.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/students")
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	// Add Student
	@PostMapping
	public ResponseEntity<Student> create(@Valid @RequestBody Student student) {
		Student created = studentService.create(student);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(created.getId())
				.toUri();
		return ResponseEntity.created(location).body(created);
	}

	// Get All Students with pagination, sorting, and optional search ?q=
	@GetMapping
	public ResponseEntity<Page<Student>> getAll(
			@RequestParam(value = "q", required = false) String query,
			@PageableDefault(size = 10) Pageable pageable) {
		Page<Student> page = studentService.getAll(query, pageable);
		return ResponseEntity.ok(page);
	}

	// Get Student By ID
	@GetMapping("/{id}")
	public ResponseEntity<Student> getById(@PathVariable Long id) {
		Student student = studentService.getById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
		return ResponseEntity.ok(student);
	}

	// Update Student
	@PutMapping("/{id}")
	public ResponseEntity<Student> update(@PathVariable Long id, @Valid @RequestBody Student student) {
		Student updated = studentService.update(id, student);
		return ResponseEntity.ok(updated);
	}

	// Delete Student
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		studentService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}


