package com.educationportal.enroll.service;

import com.educationportal.enroll.dto.PaymentRequest;
import com.educationportal.enroll.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest paymentRequest);
}
