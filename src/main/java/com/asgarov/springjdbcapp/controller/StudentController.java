package com.asgarov.springjdbcapp.controller;

import com.asgarov.springjdbcapp.dto.StudentDto;
import com.asgarov.springjdbcapp.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(studentService.getById(id));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public StudentDto createStudent(@RequestBody StudentDto studentDto) {
        return studentService.create(studentDto);
    }

    @PutMapping
    public HttpStatus updateStudent(@RequestBody StudentDto studentDto) {
        studentService.update(studentDto);
        return HttpStatus.NO_CONTENT;
    }

    @DeleteMapping("{id}")
    public HttpStatus deleteStudent(@PathVariable long id) {
        studentService.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }
}
