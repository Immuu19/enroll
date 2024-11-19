package com.educationportal.enroll.controller;

import com.educationportal.enroll.entity.EnrollmentStatus;
import com.educationportal.enroll.service.EnrollmentService;
import com.educationportal.enroll.service.PaymentService;
import com.educationportal.enroll.dto.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/enrollStudent")
    public ResponseEntity<?> enrollStudent(@RequestBody EnrollmentStatus enrollmentStatus) {
        String username = enrollmentStatus.getUsername();
        String courseId = enrollmentStatus.getCourseId();

        try {
            // Attempt to enroll the student
            String response = enrollmentService.enrollStudent(username, courseId);
            return ResponseEntity.status(204).body(response);
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }


    @GetMapping("/checkEnrollment/{username}")
    public ResponseEntity<List<EnrollmentStatus>> checkEnrollment(@PathVariable String username) {
        return ResponseEntity.ok(enrollmentService.checkEnrollment(username));
    }

    @PostMapping("/makePayment")
    public ResponseEntity<?> makePayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            String username = paymentRequest.getUsername();
            double amount = paymentRequest.getAmount();

            // Process payment
            String paymentReference = paymentService.makePayment(username, amount);

            // Update enrollment and payment status
            enrollmentService.updateEnrollmentStatusAfterPayment(username);

            return ResponseEntity.ok("Payment successful. Reference: " + paymentReference);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Payment failed: " + e.getMessage());
        }
    }


    @PutMapping("/discontinue")
    public ResponseEntity<?> discontinueEnrollment(@RequestBody EnrollmentStatus enrollmentStatus) {
        // Discontinue logic
        return ResponseEntity.noContent().build();
    }
}
