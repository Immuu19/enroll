package com.educationportal.enroll.service;

public interface ValidationService {
    boolean isUserValid(String username, String jwtToken);
    boolean isCourseValid(String courseId);
}
