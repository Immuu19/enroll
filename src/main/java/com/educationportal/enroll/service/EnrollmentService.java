package com.educationportal.enroll.service;

import com.educationportal.enroll.entity.EnrollmentStatus;
import com.educationportal.enroll.entity.PaymentDetails;

import java.util.List;

public interface EnrollmentService {
    String enrollStudent(String username, String courseId);
    List<EnrollmentStatus> checkEnrollment(String username);
    void discontinueEnrollment(String username, String courseId, String reason);
    List<EnrollmentStatus> getCoursesForUser(String username);
    List<EnrollmentStatus> getUsersForCourse(String courseId);
    void updateEnrollmentStatusAfterPayment(String username);
}
