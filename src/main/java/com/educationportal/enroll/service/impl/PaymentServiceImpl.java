package com.educationportal.enroll.service.impl;

import com.educationportal.enroll.entity.PaymentDetails;
import com.educationportal.enroll.repository.PaymentRepository;
import com.educationportal.enroll.service.PaymentService;
import com.educationportal.enroll.entity.PaymentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;


    @Override
    public String makePayment(String username,String courseId, double amount) {

        // Simulate payment logic
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid payment amount.");
        }

        // Generate a mock payment reference
        String paymentReference = UUID.randomUUID().toString();

        PaymentDetails paymentDetails = new PaymentDetails();

        paymentDetails.setUsername(username);
        paymentDetails.setAmountPaid(amount);
        paymentDetails.setCourseId(courseId);
        paymentDetails.setPaymentReference(paymentReference);
        paymentDetails.setPaymentSuccess(true);
        paymentRepository.save(paymentDetails);

        // Optional: Log payment information to DB or console
        System.out.println("Payment successful for username: " + username + ", Amount: " + amount);

        return paymentReference;
    }

}
