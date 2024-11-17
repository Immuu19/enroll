package com.educationportal.enroll.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long enrolmentId;
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
}
