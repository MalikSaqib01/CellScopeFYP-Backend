package com.example.springsecurity.payload.request;



import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.Date;



@Getter
@Setter
public class AppointmentRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String country;
    private String city;
    private String street;
    private String preferredTime; // Change the type to String
    private String preferredDate; // Change the type to String
    private String testType;
    private String purpose;

    // Constructors, getters, setters, and other methods go here
}
