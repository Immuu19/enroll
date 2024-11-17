package com.educationportal.enroll.service.impl;

import com.educationportal.enroll.model.Course;
import com.educationportal.enroll.model.Enrolment;
import com.educationportal.enroll.model.Student;
import com.educationportal.enroll.repository.CourseRepository;
import com.educationportal.enroll.repository.EnrolmentRepository;
import com.educationportal.enroll.repository.StudentRepository;
import com.educationportal.enroll.service.EnrolmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrolmentServiceImpl implements EnrolmentService {

    @Autowired
    private EnrolmentRepository enrolmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    @Transactional
    public Enrolment enrollStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();

        Enrolment enrolment = new Enrolment();
        enrolment.setStudent(student);
        enrolment.setCourse(course);
        enrolment.setPaymentStatus(false);

        return enrolmentRepository.save(enrolment);
    }
}

