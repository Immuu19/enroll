package com.educationportal.enroll.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PaymentDetails {

    @Id
    private String username;

    private String courseId;

    private String paymentReference;

    private double amountPaid;

    private boolean paymentSuccess;
}
