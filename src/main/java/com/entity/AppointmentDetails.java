package com.entity;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class AppointmentDetails {
	private Date apointmentDateTime;
	private Date registrationDateTime;
	private String appointmentCity;
	private Doctor doctor;
}
