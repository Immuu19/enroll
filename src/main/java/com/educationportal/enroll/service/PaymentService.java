package com.educationportal.enroll.service;

import com.educationportal.enroll.entity.PaymentDetails;

public interface PaymentService {
    String makePayment(String username, double amount);
}
