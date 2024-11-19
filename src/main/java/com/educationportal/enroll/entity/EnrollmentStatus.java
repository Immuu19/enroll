package com.educationportal.enroll.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
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

}
