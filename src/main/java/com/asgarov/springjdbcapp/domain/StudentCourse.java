package com.asgarov.springjdbcapp.domain;

import lombok.Data;

@Data
public class StudentCourse {
    private Long studentCourseId;
    private Long studentId;
    private Long courseId;
}
