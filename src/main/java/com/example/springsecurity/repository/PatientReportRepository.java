package com.example.springsecurity.repository;


import com.example.springsecurity.models.PatientReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientReportRepository extends JpaRepository<PatientReport,Integer> {


    List<PatientReport> findByFirstnameAndLastname(String firstName, String lastName);
    List<PatientReport> findByAppointmentId(Long Id);

//    PatientReport findTopByAppointmentIdOrderByCreatedAtDesc(Long appointmentId);




}

