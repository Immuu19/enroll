package com.educationportal.enroll.repository;

import com.educationportal.enroll.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {}
