package com.educationportal.enroll.service;

import com.educationportal.enroll.entity.EnrollmentStatus;
import com.educationportal.enroll.entity.PaymentDetails;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface EnrollmentService {
    String enrollStudent(String username, String courseId);
    List<EnrollmentStatus> checkEnrollment(String username);
    void discontinueEnrollment(String username, String courseId, String reason);
    List<EnrollmentStatus> getCoursesForUser(String username);
    List<EnrollmentStatus> getUsersForCourse(String courseId);
    void updateEnrollmentStatusAfterPayment(String username, String courseId);
    List<EnrollmentStatus> getEnrolledCoursesByUser(String username);
    List<EnrollmentStatus> getStudentsByCourse(String courseId);
    Optional<EnrollmentStatus> findEnrollmentByUsernameAndCourseId(String username, String courseId);
    void saveEnrollmentStatus(EnrollmentStatus enrollmentStatus);
    ResponseEntity<String> deleteEnrollmentStatusById(Long id);

}
