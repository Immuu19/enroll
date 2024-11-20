package com.educationportal.enroll.service;

public interface ValidationService {
    boolean isUserValid(String username);
    boolean isCourseValid(String courseId);
}
