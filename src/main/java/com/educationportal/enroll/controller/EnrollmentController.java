package com.educationportal.enroll.controller;

import com.educationportal.enroll.entity.EnrollmentId;
import com.educationportal.enroll.entity.EnrollmentStatus;
import com.educationportal.enroll.service.EnrollmentService;
import com.educationportal.enroll.service.ValidationService;
import com.educationportal.enroll.service.PaymentService;
import com.educationportal.enroll.dto.PaymentRequest;
import com.educationportal.enroll.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping(value = "/enrollStudent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> enrollStudent(@RequestHeader("Authorization") String authorizationHeader,@RequestBody EnrollmentStatus enrollmentStatus) {
        String username = enrollmentStatus.getUsername();
        String courseId = enrollmentStatus.getCourseId();

        String jwtToken = authorizationHeader.substring(7);

        System.out.println(" JWT  " +jwtUtil.validateToken(jwtToken));
        if (!jwtUtil.validateToken(jwtToken)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("UNAUTHORISED. PLease use valid token");
        }

        try {
            // Attempt to enroll the student

            String response = enrollmentService.enrollStudent(username, courseId);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }


    @GetMapping(value = "/checkEnrollment/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkEnrollment(@RequestHeader("Authorization") String authorizationHeader,@PathVariable String username) {
        String jwtToken = authorizationHeader.substring(7);

        System.out.println(" JWT  " +jwtUtil.validateToken(jwtToken));
        if (!jwtUtil.validateToken(jwtToken)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("UNAUTHORISED. PLease use valid token");
        }
        return ResponseEntity.ok(enrollmentService.checkEnrollment(username));
    }

    @PostMapping(value = "/makePayment" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> makePayment(@RequestHeader("Authorization") String authorizationHeader,@RequestBody PaymentRequest paymentRequest) {
        try {
            String username = paymentRequest.getUsername();
            String courseId = paymentRequest.getCourseId();
            double amount = paymentRequest.getAmount();

            String jwtToken = authorizationHeader.substring(7);

            System.out.println(" JWT  " +jwtUtil.validateToken(jwtToken));
            if (!jwtUtil.validateToken(jwtToken)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("UNAUTHORISED. PLease use valid token");
            }

            Optional<EnrollmentStatus> optionalEnrollment = enrollmentService.findEnrollmentByUsernameAndCourseId(username, courseId);

            if (optionalEnrollment.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No enrollment found for the specified user and course.");
            }

            EnrollmentStatus enrollment = optionalEnrollment.get();

            if (enrollment.isCoursePaymentStatus()) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", HttpStatus.BAD_REQUEST.value());
                response.put("message", "The course is already paid and enrolled.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(response);
            }

            // Process payment
            String paymentReference = paymentService.makePayment(username,courseId, amount);

            // Update enrollment and payment status
            enrollmentService.updateEnrollmentStatusAfterPayment(username, courseId);

            return ResponseEntity.ok("Payment successful. Reference: " + paymentReference);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Payment failed: " + e.getMessage());
        }
    }


    @GetMapping("/getEnrolledCourses/{username}")
    public ResponseEntity<?> getEnrolledCourses(@RequestHeader("Authorization") String authorizationHeader,@PathVariable String username) {
        String jwtToken = authorizationHeader.substring(7);

        System.out.println(" JWT  " +jwtUtil.validateToken(jwtToken));
        if (!jwtUtil.validateToken(jwtToken)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("UNAUTHORISED. PLease use valid token");
        }

        try {
            List<EnrollmentStatus> enrolledCourses = enrollmentService.getEnrolledCoursesByUser(username);
            if (enrolledCourses.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No enrolled courses found for the user: " + username);
            }
            return ResponseEntity.ok(enrolledCourses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/getStudentsInCourse/{courseId}")
    public ResponseEntity<?> getStudentsInCourse(@RequestHeader("Authorization") String authorizationHeader,@PathVariable String courseId) {
        String jwtToken = authorizationHeader.substring(7);

        System.out.println(" JWT  " +jwtUtil.validateToken(jwtToken));
        if (!jwtUtil.validateToken(jwtToken)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("UNAUTHORISED. PLease use valid token");
        }

        try {
            List<EnrollmentStatus> students = enrollmentService.getStudentsByCourse(courseId);
            if (students.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No students found for the course: " + courseId);
            }
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteEnrollment/{id}")
    public ResponseEntity<String> deleteEnrollmentStatus(@RequestHeader("Authorization") String authorizationHeader,@PathVariable Long id) {
        String jwtToken = authorizationHeader.substring(7);

        System.out.println(" JWT  " +jwtUtil.validateToken(jwtToken));
        if (!jwtUtil.validateToken(jwtToken)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("UNAUTHORISED. PLease use valid token");
        }
        return enrollmentService.deleteEnrollmentStatusById(id);
    }

    @PostMapping("/validateUserAndCourse")
    public ResponseEntity<?> validateUserAndCourse(@RequestHeader("Authorization") String authorizationHeader,@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String courseId = request.get("course");

        String jwtToken = authorizationHeader.substring(7);

        System.out.println(" JWT  " +jwtUtil.validateToken(jwtToken));
        if (!jwtUtil.validateToken(jwtToken)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("UNAUTHORISED. PLease use valid token");
        }

        //System.out.println("JWT");
        //System.out.println(jwtUtil.extractClaims(jwtToken));

        boolean isUserValid = validationService.isUserValid(username,jwtToken);
        boolean isCourseValid = validationService.isCourseValid(courseId);

        Map<String, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("course", courseId);
        response.put("isUserValid", isUserValid);
        response.put("isCourseValid", isCourseValid);

        if (!isUserValid || !isCourseValid) {
            return ResponseEntity.badRequest().body(response);
        }

        response.put("message", "Validation successful");
        return ResponseEntity.ok(response);
    }
}
