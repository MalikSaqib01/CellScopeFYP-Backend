package com.example.springsecurity.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;

public class AddTaskRequest {

    @JsonProperty
    private String text;

    @JsonProperty
    private String day;

    @JsonProperty
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @JsonProperty
    private boolean reminder;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }
}
