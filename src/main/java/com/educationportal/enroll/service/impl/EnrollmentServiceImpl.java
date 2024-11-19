package com.educationportal.enroll.service.impl;

import com.educationportal.enroll.entity.EnrollmentStatus;
import com.educationportal.enroll.entity.EnrollmentId;
import java.util.stream.Collectors;
import com.educationportal.enroll.repository.EnrollmentRepository;
import com.educationportal.enroll.entity.EnrollmentStatusType;
import com.educationportal.enroll.service.EnrollmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public String enrollStudent(String username, String courseId) {
        if (enrollmentRepository.countByUsername(username) >= 4) {
            throw new IllegalStateException("User has already enrolled in the maximum of 4 courses.");
        }

        System.out.println("HERE");

        // Check if the course has reached its maximum capacity
        if (enrollmentRepository.countByCourseId(courseId) >= 4) {
            throw new IllegalStateException("This course has reached the maximum capacity of 4 users.");
        }

        System.out.println("HEREEEE");

        // Check if the user is already enrolled in this course
        //EnrollmentId id = new EnrollmentId();
        //id.setUsername(username);
        //id.setCourseId(courseId);
        if (enrollmentRepository.existsByUsernameAndCourseId(username, courseId)) {

            EnrollmentStatus existing = enrollmentRepository.findByUsernameAndCourseId(username, courseId)
                    .orElseThrow(() -> new IllegalStateException("Enrollment record not found unexpectedly."));

            if (existing.isCoursePaymentStatus()) {
                return "User is already enrolled and payment is completed. HAPPY LEARNING !!!";
            } else {
                return "Enrollment pending payment. Please complete payment to finalize.";
            }
        }

        // Enroll the user
        EnrollmentStatus enrollmentStatus = new EnrollmentStatus();
        enrollmentStatus.setUsername(username);
        enrollmentStatus.setCourseId(courseId);
        enrollmentRepository.save(enrollmentStatus);
        return "Enrollment Initiated. Please proceed to make payment via /api/makePayment.";
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
    public List<EnrollmentStatus> getEnrolledCoursesByUser(String username) {
        return enrollmentRepository.findByUsername(username).stream()
                .filter(enrollment -> enrollment.getEnrollmentStatus() == EnrollmentStatusType.ENROLLED)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentStatus> getStudentsByCourse(String courseId) {
        return enrollmentRepository.findByCourseId(courseId).stream()
                .filter(enrollment -> enrollment.getEnrollmentStatus() == EnrollmentStatusType.ENROLLED)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EnrollmentStatus> findEnrollmentByUsernameAndCourseId(String username, String courseId) {
        EnrollmentId enrollmentId = new EnrollmentId();
        enrollmentId.setUsername(username);
        enrollmentId.setCourseId(courseId);
        System.out.println("findEnrollmentByUsernameAndCourseId");
        //return enrollmentRepository.findById(enrollmentId);
        return enrollmentRepository.findByUsernameAndCourseId(username,courseId);
    }

    @Override
    public void saveEnrollmentStatus(EnrollmentStatus enrollmentStatus) {
        enrollmentRepository.save(enrollmentStatus);
    }

    @Transactional
    @Override
    public ResponseEntity<String> deleteEnrollmentStatusById(Long id) {

        if (enrollmentRepository.existsById(id)) {
            enrollmentRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enrollment status deleted successfully. Kindly Check with Head Office for refund");
        } else {
            return new ResponseEntity<>("Enrollment status not found for ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @Override
    public void updateEnrollmentStatusAfterPayment(String username, String courseId) {
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
