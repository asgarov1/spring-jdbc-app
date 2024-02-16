package com.asgarov.springjdbcapp.repository;

import com.asgarov.springjdbcapp.domain.Student;
import com.asgarov.springjdbcapp.domain.StudentGroup;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class StudentGroupRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public StudentGroupRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("student_group")
                .usingGeneratedKeyColumns("student_group_id");
    }

    public StudentGroup create(StudentGroup studentGroup) {
        int groupId = (Integer) simpleJdbcInsert.executeAndReturnKey(
                Map.of("group_name", studentGroup.getGroupName())
        );

        studentGroup.setGroupId((long) groupId);
        return studentGroup;
    }

    public StudentGroup read(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM student_group WHERE group_id = ?",
                BeanPropertyRowMapper.newInstance(StudentGroup.class),
                id);
    }

    public void update(StudentGroup studentGroup) {
        jdbcTemplate.update(
                "UPDATE student_group SET group_name = ? WHERE group_id = ?",
                studentGroup.getGroupName(), studentGroup.getGroupId()
        );
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM student_group WHERE group_id = ?;", id);
    }
}
