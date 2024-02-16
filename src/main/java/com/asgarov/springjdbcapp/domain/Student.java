package com.asgarov.springjdbcapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Long studentId;
    private Long groupId;
    private String firstName;
    private String lastName;

    public Student(Long groupId, String firstName, String lastName) {
        this.groupId = groupId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
