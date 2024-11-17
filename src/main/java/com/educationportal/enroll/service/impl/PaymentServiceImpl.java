package com.educationportal.enroll.service.impl;

import com.educationportal.enroll.dto.PaymentRequest;
import com.educationportal.enroll.dto.PaymentResponse;
import com.educationportal.enroll.model.Enrolment;
import com.educationportal.enroll.repository.EnrolmentRepository;
import com.educationportal.enroll.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private EnrolmentRepository enrolmentRepository;

    private Random random = new Random();

    @Override
    @Transactional
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        // Fetch the enrolment details
        Enrolment enrolment = enrolmentRepository.findById(paymentRequest.getEnrolmentId())
                .orElseThrow(() -> new RuntimeException("Enrolment not found"));

        // Simulate payment processing (50% chance of success)
        boolean paymentSuccess = random.nextBoolean();

        if (paymentSuccess) {
            // Update the payment status in the database
            enrolment.setPaymentStatus(true);
            enrolmentRepository.save(enrolment);
            return new PaymentResponse(true, "Payment processed successfully.");
        } else {
            return new PaymentResponse(false, "Payment failed. Please try again.");
        }
    }
}
