package com.example.springsecurity.models;


import lombok.Data;

@Data

public class NotificationRequest {

    private String email;
    private String message;

}
