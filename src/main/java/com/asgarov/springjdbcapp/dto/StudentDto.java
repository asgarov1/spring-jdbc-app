package com.asgarov.springjdbcapp.dto;

import com.asgarov.springjdbcapp.domain.Course;
import com.asgarov.springjdbcapp.domain.Student;
import com.asgarov.springjdbcapp.domain.StudentGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long studentId;
    private String firstName;
    private String lastName;

    private List<Course> courses;
    private StudentGroup studentGroup;

    public StudentDto(Long studentId, String firstName, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static StudentDto of(Student student) {
        return new StudentDto(student.getStudentId(), student.getFirstName(), student.getLastName());
    }
}
