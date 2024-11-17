package com.educationportal.enroll.repository;

import com.educationportal.enroll.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {}
