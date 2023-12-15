package com.example.springsecurity.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreatePayment {



    private Long appointmentId;

    private String email;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    @SerializedName("items")
    Object[] items;
    public Object[]  getItems() {
        return items;
    }
}
