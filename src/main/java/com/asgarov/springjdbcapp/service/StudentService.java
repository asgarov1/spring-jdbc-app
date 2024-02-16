package com.asgarov.springjdbcapp.service;

import com.asgarov.springjdbcapp.domain.Course;
import com.asgarov.springjdbcapp.domain.Student;
import com.asgarov.springjdbcapp.domain.StudentGroup;
import com.asgarov.springjdbcapp.dto.StudentDto;
import com.asgarov.springjdbcapp.repository.CourseRepository;
import com.asgarov.springjdbcapp.repository.StudentGroupRepository;
import com.asgarov.springjdbcapp.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentGroupRepository studentGroupRepository;

    public StudentDto getById(long id) {
        Student student = studentRepository.read(id);
        List<Course> courses = courseRepository.findByStudentId(id);
        StudentGroup studentGroup = studentGroupRepository.read(student.getGroupId());

        StudentDto studentDto = StudentDto.of(student);
        studentDto.setCourses(courses);
        studentDto.setStudentGroup(studentGroup);
        return studentDto;
    }


    public StudentDto create(StudentDto studentDto) {
        Student createdStudent = studentRepository.create(
                new Student(
                        Optional.ofNullable(studentDto.getStudentGroup()).map(StudentGroup::getGroupId).orElse(null),
                        studentDto.getFirstName(),
                        studentDto.getLastName())
        );
        return StudentDto.of(createdStudent);
    }

    public void update(StudentDto studentDto) {
        studentRepository.update(
                new Student(
                        studentDto.getStudentId(),
                        Optional.ofNullable(studentDto.getStudentGroup()).map(StudentGroup::getGroupId).orElse(null),
                        studentDto.getFirstName(),
                        studentDto.getLastName())
        );
    }

    public void deleteById(long id) {
        studentRepository.delete(id);
    }
}
