package com.example.springsecurity.controllers;


import com.example.springsecurity.models.Appointment;
import com.example.springsecurity.payload.request.AppointmentRequest;
import com.example.springsecurity.payload.request.CityPatientCountDTO;
import com.example.springsecurity.repository.PreferredTimeAndDateProjection;
import com.example.springsecurity.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointment")
@CrossOrigin("*")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // Create an appointment
    @PostMapping("/create")
    public ResponseEntity<Object> createAppointment(@RequestBody AppointmentRequest appointmentRequest) throws MessagingException {
        Object createdAppointment = appointmentService.createAppointment(appointmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }


    // Retrieve all appointments
    @GetMapping("/all")
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // Retrieve an appointment by ID
    @GetMapping("/specificid/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        return appointment.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/specificmail/{email}")
    public List<Appointment> getAppointmentByEmail(@PathVariable String email) {
        List<Appointment> appointments = appointmentService.getAppointmentByEmail(email);

//        if (appointment)
        return appointments;


    }

    @GetMapping("/citypatients")
    public List<CityPatientCountDTO> getAppointmentByCity() {
        return appointmentService.getAppointmentByCity();
    }


    // Update an appointment by ID
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment updatedAppointment) {
        try {
            Appointment appointment = appointmentService.updateAppointment(id, updatedAppointment);
            return ResponseEntity.ok().body(appointment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an appointment by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/preferredTimeAndDate")
    public List<PreferredTimeAndDateProjection> getAllPreferredTimeAndDate() {
        return appointmentService.getAllPreferredTimeAndDate();
    }
}

