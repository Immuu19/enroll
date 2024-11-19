package com.educationportal.enroll.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PaymentRequest {
    private String username;
    private String courseId;
    private double amount;
}
