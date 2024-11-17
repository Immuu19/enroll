package com.educationportal.enroll.repository;

import com.educationportal.enroll.model.Enrolment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrolmentRepository extends JpaRepository<Enrolment, Long> {}

