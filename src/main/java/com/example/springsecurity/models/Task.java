package com.example.springsecurity.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 30)
    private String description;

    @Size(max = 30)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Size(max = 40)
    private String dayAndTime;

    private boolean setReminder;

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDayAndTime() {
        return dayAndTime;
    }

    public void setDayAndTime(String dayAndTime) {
        this.dayAndTime = dayAndTime;
    }

    public boolean isSetReminder() {
        return setReminder;
    }

    public void setSetReminder(boolean setReminder) {
        this.setReminder = setReminder;
    }
}


