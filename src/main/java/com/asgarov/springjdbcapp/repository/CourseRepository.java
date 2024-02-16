package com.asgarov.springjdbcapp.repository;

import com.asgarov.springjdbcapp.domain.Course;
import com.asgarov.springjdbcapp.domain.StudentCourse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class CourseRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CourseRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("course")
                .usingGeneratedKeyColumns("course_id");
    }

    public Course create(Course course) {
        int courseId = (Integer) simpleJdbcInsert.executeAndReturnKey(Map.of(
                        "course_name", course.getCourseName(),
                        "course_description", course.getCourseDescription()
                )
        );
        course.setCourseId((long) courseId);
        return course;
    }

    public Course read(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM course WHERE course_id = ?",
                BeanPropertyRowMapper.newInstance(Course.class),
                id);
    }

    public void update(Course course) {
        jdbcTemplate.update(
                "UPDATE course SET course_name = ?, course_description = ? WHERE course_id = ?",
                course.getCourseName(), course.getCourseDescription(), course.getCourseId()
        );
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM course WHERE course_id = ?;", id);
    }

    public List<Course> findByStudentId(Long id) {
        List<StudentCourse> studentCourses = jdbcTemplate.query("SELECT * FROM student_course WHERE student_id = ?",
                BeanPropertyRowMapper.newInstance(StudentCourse.class), id);

        return studentCourses.stream()
                .map(StudentCourse::getCourseId)
                .map(this::read)
                .toList();
    }
}
