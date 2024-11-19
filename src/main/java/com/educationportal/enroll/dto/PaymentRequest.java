package com.educationportal.enroll.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private String username;
    private double amount;
}
