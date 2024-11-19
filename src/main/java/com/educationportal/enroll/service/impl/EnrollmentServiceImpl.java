package com.educationportal.enroll.service.impl;

import com.educationportal.enroll.entity.EnrollmentStatus;
import com.educationportal.enroll.entity.EnrollmentId;
import com.educationportal.enroll.repository.EnrollmentRepository;
import com.educationportal.enroll.entity.EnrollmentStatusType;
import com.educationportal.enroll.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public String enrollStudent(String username, String courseId) {
        if (enrollmentRepository.countByUsername(username) >= 4) {
            throw new IllegalStateException("User has already enrolled in the maximum of 4 courses.");
        }

        // Check if the course has reached its maximum capacity
        if (enrollmentRepository.countByCourseId(courseId) >= 4) {
            throw new IllegalStateException("This course has reached the maximum capacity of 4 users.");
        }

        // Check if the user is already enrolled in this course
        EnrollmentId id = new EnrollmentId();
        id.setUsername(username);
        id.setCourseId(courseId);
        if (enrollmentRepository.existsById(id)) {
            EnrollmentStatus existing = enrollmentRepository.findById(id).orElseThrow();
            if (existing.isCoursePaymentStatus()) {
                throw new IllegalStateException("User is already enrolled and payment is completed.");
            } else {
                return "Enrollment pending payment. Please complete payment to finalize.";
            }
        }

        // Enroll the user
        EnrollmentStatus enrollmentStatus = new EnrollmentStatus();
        enrollmentStatus.setUsername(username);
        enrollmentStatus.setCourseId(courseId);
        enrollmentRepository.save(enrollmentStatus);
        return "Enrollment successful. Please proceed to make payment.";
    }

    @Override
    public List<EnrollmentStatus> checkEnrollment(String username) {
        return enrollmentRepository.findByUsername(username);
    }

    @Override
    public void discontinueEnrollment(String username, String courseId, String reason) {
        // Fetch enrollment, update status to DISCONTINUE, and save.
    }

    @Override
    public List<EnrollmentStatus> getCoursesForUser(String username) {
        return enrollmentRepository.findByUsername(username);
    }

    @Override
    public List<EnrollmentStatus> getUsersForCourse(String courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    @Override
    public void updateEnrollmentStatusAfterPayment(String username) {
        List<EnrollmentStatus> enrollments = enrollmentRepository.findByUsername(username);

        for (EnrollmentStatus enrollment : enrollments) {
            if (!enrollment.isCoursePaymentStatus()) {
                // Update enrollmentStatus and coursePaymentStatus
                enrollment.setEnrollmentStatus(EnrollmentStatusType.ENROLLED); // Update to "ENROLLED"
                enrollment.setCoursePaymentStatus(true); // Mark payment as completed
                enrollmentRepository.save(enrollment);
            }
        }
    }
}
