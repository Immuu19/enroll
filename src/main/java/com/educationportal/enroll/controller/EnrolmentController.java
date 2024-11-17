package com.educationportal.enroll.controller;

import com.educationportal.enroll.model.Enrolment;
import com.educationportal.enroll.service.EnrolmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrolment")
public class EnrolmentController {

    @Autowired
    private EnrolmentService enrolmentService;

    @PostMapping("/enroll")
    public Enrolment enrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        return enrolmentService.enrollStudent(studentId, courseId);
    }
}

