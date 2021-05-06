package com.entity;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class Hospital {
	private String hospitalName;
	private String hospitalCity;
	private List<Patient> patient;

	
	public static int getPatientCountforCity(Hospital hp, String city) {
		return hp.getPatient().stream().filter(p -> p.getCity().equalsIgnoreCase(city)).collect(Collectors.toList())
				.size();

	}

	public int getPatientCountforLocationWithDateRange(Hospital hp, String city, Date fromDate, Date toDate) {
		return hp.getPatient().stream()
				.filter(p -> p.getAppointment().getApointmentDateTime().after(fromDate)
						&& p.getAppointment().getApointmentDateTime().before(toDate))
				.filter(p -> p.getCity().equalsIgnoreCase(city)).collect(Collectors.toList()).size();

	}

	public static int getPatientCountforLocationWithRegDateRange(Hospital hp, String city, Date fromDate, Date toDate) {
		return hp.getPatient().stream()
				.filter(p -> p.getAppointment().getRegistrationDateTime().after(fromDate)
						&& p.getAppointment().getRegistrationDateTime().before(toDate))
				.filter(p -> p.getCity().equalsIgnoreCase(city)).collect(Collectors.toList()).size();

	}

	public static int getOutstationPatientCount(Hospital hp, String city) {
		// TODO Auto-generated method stub
		Predicate<Patient> p1 = p -> p.getCity().equalsIgnoreCase(city);
		return hp.getPatient().stream().filter(p1.negate()).collect(Collectors.toList()).size();
	}
}
