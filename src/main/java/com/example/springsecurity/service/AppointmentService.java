package com.example.springsecurity.service;

import com.example.springsecurity.models.Appointment;
import com.example.springsecurity.payload.request.AppointmentRequest;
import com.example.springsecurity.payload.request.CityPatientCountDTO;
import com.example.springsecurity.repository.AppointmentRepository;
import com.example.springsecurity.repository.PreferredTimeAndDateProjection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.context.Context;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Autowired
    MailService mailService;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Object createAppointment(AppointmentRequest appointmentRequest) throws MessagingException {
        Appointment newAppointment = new Appointment();
        BeanUtils.copyProperties(appointmentRequest, newAppointment);

        boolean found = appointmentRepository.existsByPreferredTimeAndPreferredDate(newAppointment.getPreferredTime() , newAppointment.getPreferredDate());

        newAppointment.setStatus("Pending"); // Example status

        System.out.println();


        if (!found) {


            Appointment appointment = appointmentRepository.save(newAppointment);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("malicksaqib01@gmail.com");
            simpleMailMessage.setTo("saqii8872@gmail.com");
            simpleMailMessage.setSubject("HOME VISIT REQUEST");
            System.out.println(newAppointment.toString());
            simpleMailMessage.setText(newAppointment.toString());
            mailService.sendMessage(simpleMailMessage);

            return appointment;

        } else {
            return "Slot is already booked";
        }
    }



//    public String createAppointment(AppointmentRequest appointmentRequest) {
//        Appointment newAppointment = new Appointment();
//        BeanUtils.copyProperties(appointmentRequest, newAppointment);
//
//       List<Appointment> appointments = appointmentRepository.findAll();
//
//
//        newAppointment.setStatus("Pending"); // Example status
//        //appointmentRepository.save(newAppointment);
//
//
//        System.out.println();
//
//        Appointment lastAppointment = appointments.get(appointments.size()-1);
//        String lastPreferredTime1 = lastAppointment.getPreferredTime();
//        LocalTime lastPreferredTime = LocalTime.parse(lastPreferredTime1, DateTimeFormatter.ofPattern("HH:mm"));
//        int lastPreferredHour = lastPreferredTime.getHour();
//        int lastPreferredMinute = lastPreferredTime.getMinute();
//        LocalTime currentPreferredTime = LocalTime.parse(newAppointment.getPreferredTime(), DateTimeFormatter.ofPattern("HH:mm"));
//        int PreferredHour = currentPreferredTime.getHour();
//        int PreferredMinute = currentPreferredTime.getMinute();
//
//        System.out.println(lastPreferredHour);
//
//        if (lastPreferredHour<PreferredHour || (lastPreferredHour>PreferredHour))
//        {
//            appointmentRepository.save(newAppointment);
//            return "slot confirmed for: " + PreferredHour+":"+PreferredMinute;
//        }
//        else {
//             return "slot already booked for: " + PreferredHour+":"+PreferredMinute ;
//        }
//        // Save the appointment to the database
//
//    }

    // Retrieve all appointments
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // Retrieve an appointment by ID
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }


    public  List<Appointment> getAppointmentByEmail(String email) {
        return appointmentRepository.findByEmail(email);
    }

    // Update an appointment by ID
    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
        if (appointmentRepository.existsById(id)) {

            updatedAppointment.setId((id));
            return appointmentRepository.save(updatedAppointment);
        } else {
            throw new IllegalArgumentException("Appointment not found");
        }
    }

    // Delete an appointment by ID
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }



    public List<CityPatientCountDTO> getAppointmentByCity() {
        return  appointmentRepository.countPatientsByCityDTO();
    }


    public List<PreferredTimeAndDateProjection> getAllPreferredTimeAndDate() {
        return appointmentRepository.findAllPreferredTimeAndDate();
    }
}

