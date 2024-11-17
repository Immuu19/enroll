package com.educationportal.enroll.controller;

import com.educationportal.enroll.dto.PaymentRequest;
import com.educationportal.enroll.dto.PaymentResponse;
import com.educationportal.enroll.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public PaymentResponse processPayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.processPayment(paymentRequest);
    }
}
