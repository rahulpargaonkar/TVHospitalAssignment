package com.entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Patient {
	private String name;
	private int age;
	private String city;
	private AppointmentDetails appointment;
	private String phoneNumber;
	private String emailId;
	private Disease disease;
}
