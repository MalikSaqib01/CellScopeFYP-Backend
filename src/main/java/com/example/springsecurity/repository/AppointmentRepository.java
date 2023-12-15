package com.example.springsecurity.repository;

import com.example.springsecurity.models.Appointment;
import com.example.springsecurity.models.PatientReport;
import com.example.springsecurity.payload.request.CityPatientCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


//    @Query
//    Appointment findByAppointmentId(Long Id);

    @Query
    List<Appointment> findByEmail(String email);

    @Query(value = "SELECT new com.example.springsecurity.payload.request.CityPatientCountDTO(city, COUNT(*)) FROM Appointment GROUP BY city")
    List<CityPatientCountDTO> countPatientsByCityDTO();

    @Query(value = "SELECT a FROM Appointment a ORDER BY a.preferredTime DESC" , nativeQuery = true)
    List<Appointment> findAllOrderByPreferredTimeDesc();

    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.preferredTime = :preferredTime AND a.preferredDate = :preferredDate")
    boolean existsByPreferredTimeAndPreferredDate(@Param("preferredTime") String preferredTime, @Param("preferredDate") String preferredDate);

    @Query(value = "SELECT preferred_time AS preferredTime, preferred_date AS preferredDate FROM appointment", nativeQuery = true)
    List<PreferredTimeAndDateProjection> findAllPreferredTimeAndDate();


}