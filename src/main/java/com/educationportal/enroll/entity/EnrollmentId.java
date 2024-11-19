package com.educationportal.enroll.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class EnrollmentId implements Serializable {
    private String username;
    private String courseId;
}
