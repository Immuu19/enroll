package com.educationportal.enroll.repository;

import com.educationportal.enroll.entity.EnrollmentStatus;
import com.educationportal.enroll.entity.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;


import java.util.List;

public interface EnrollmentRepository extends JpaRepository<EnrollmentStatus, EnrollmentId> {

    void deleteById(Long id);

    @Query("SELECT e FROM EnrollmentStatus e WHERE e.username = :username")
    List<EnrollmentStatus> findByUsername(String username);

    @Query("SELECT e FROM EnrollmentStatus e WHERE e.courseId = :courseId")
    List<EnrollmentStatus> findByCourseId(String courseId);

    long countByUsername(String username);

    long countByCourseId(String courseId);

    //Optional<EnrollmentStatus> findById(EnrollmentId enrollmentId);
    boolean existsByUsernameAndCourseId(String username, String courseId);

    Optional<EnrollmentStatus> findByUsernameAndCourseId(String username, String courseId);

    boolean existsById(Long id);
}
