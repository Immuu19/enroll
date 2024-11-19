package com.educationportal.enroll.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(
        name = "enrollment_status",
        uniqueConstraints = @UniqueConstraint(columnNames = {"username", "courseId"}) // Ensure uniqueness
)
public class EnrollmentStatus {

    // Auto-increment primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Composite key fields
    private String username;
    private String courseId;

    // Enrollment status with default value TBA
    @Enumerated(EnumType.STRING)
    private EnrollmentStatusType enrollmentStatus = EnrollmentStatusType.PAYMENT_PENDING;

    // Payment status with default value false
    private boolean coursePaymentStatus = false;

    // No-args constructor required by JPA
    public EnrollmentStatus() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public EnrollmentStatusType getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(EnrollmentStatusType enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public boolean isCoursePaymentStatus() {
        return coursePaymentStatus;
    }

    public void setCoursePaymentStatus(boolean coursePaymentStatus) {
        this.coursePaymentStatus = coursePaymentStatus;
    }
}
