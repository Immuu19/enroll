package com.educationportal.enroll.repository;

import com.educationportal.enroll.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentDetails, String> {
}
