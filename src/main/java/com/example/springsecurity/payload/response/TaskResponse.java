package com.example.springsecurity.payload.response;

public class TaskResponse {

    private Long id;

    private String text;

    private String day;

    private boolean reminder;

    public TaskResponse() {
    }

    public TaskResponse(Long id, String text, String day, boolean reminder) {
        this.id = id;
        this.text = text;
        this.day = day;
        this.reminder = reminder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
