package com.educationportal.enroll.service;

import com.educationportal.enroll.model.Enrolment;

public interface EnrolmentService {
    Enrolment enrollStudent(Long studentId, Long courseId);
}
