package com.educationportal.enroll.repository;

import com.educationportal.enroll.entity.EnrollmentStatus;
import com.educationportal.enroll.entity.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<EnrollmentStatus, EnrollmentId> {

    @Query("SELECT e FROM EnrollmentStatus e WHERE e.username = :username")
    List<EnrollmentStatus> findByUsername(String username);

    @Query("SELECT e FROM EnrollmentStatus e WHERE e.courseId = :courseId")
    List<EnrollmentStatus> findByCourseId(String courseId);

    long countByUsername(String username);

    long countByCourseId(String courseId);
}
