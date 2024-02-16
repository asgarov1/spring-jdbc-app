package com.asgarov.springjdbcapp.repository;

import com.asgarov.springjdbcapp.domain.Course;
import com.asgarov.springjdbcapp.domain.Student;
import com.asgarov.springjdbcapp.domain.StudentCourse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public StudentRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("student")
                .usingGeneratedKeyColumns("student_id");
    }

    public Student create(Student student) {
        Map<String, Object> params = new HashMap<>();
        params.put("group_id", student.getGroupId());
        params.put("first_name", student.getFirstName());
        params.put("last_name", student.getLastName());

        int studentId = (Integer) simpleJdbcInsert.executeAndReturnKey(params);

        student.setStudentId((long) studentId);
        return student;
    }

    public Student read(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM student WHERE student_id = ?",
                BeanPropertyRowMapper.newInstance(Student.class),
                id);
    }

    public void update(Student student) {
        jdbcTemplate.update(
                "UPDATE student SET group_id = ?, first_name = ?, last_name = ? WHERE student_id = ?",
                student.getGroupId(), student.getFirstName(), student.getLastName(), student.getStudentId()
        );
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM student WHERE student_id = ?;", id);
    }
}
