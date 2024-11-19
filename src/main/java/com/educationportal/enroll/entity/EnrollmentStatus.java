package com.educationportal.enroll.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@IdClass(EnrollmentId.class)
public class EnrollmentStatus {

    @Id
    private String username;

    @Id
    private String courseId;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatusType enrollmentStatus;

    public boolean coursePaymentStatus;

    private String discontinueReason;
}
