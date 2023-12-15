package com.example.springsecurity.models;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class PatientReport {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

	@Column(name = "appointmentId")
	private Long appointmentId;

	@Column(name = "firstname")
	private String firstname;

    @Column(name = "lastname")
    private String lastname;

	@Column(name = "email")
	private String email;
    @Column(name = "testName")
    private String testName;
    @Column(name = "result")
    private String result;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
