package com.educationportal.enroll.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Enrolment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Course course;

    private Boolean paymentStatus;
}
