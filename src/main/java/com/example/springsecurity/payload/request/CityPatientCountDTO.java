package com.example.springsecurity.payload.request;

public class CityPatientCountDTO {
    private String city;
    private Long totalPatients;

    public CityPatientCountDTO(String city, Number totalPatients) {
        this.city = city;
        this.totalPatients = totalPatients.longValue();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(Long totalPatients) {
        this.totalPatients = totalPatients;
    }

    // getters and setters
}
