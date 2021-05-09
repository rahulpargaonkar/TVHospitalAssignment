package com.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Hospital {
	private String hospitalName;
	private City hospitalCity;
	private List<Patient> patient;
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public String getLocalVsOutstationPercentage() {
		int totalPatientCount =getPatient().size();
		int outstationCount = totalPatientCount - getLocalPatients(getPatient()).size();
		int localCount = totalPatientCount - outstationCount;
		return getformattedString(localCount, outstationCount);

	}

	public String getLocalVsOutstationPercentageWithinLastNdays(int days) {
		List<Patient> patientWithinLastNdays = getRegistredPatientsInLastNdays(days);
		int localCount = getLocalPatients(patientWithinLastNdays).size();
		int outstationCount = patientWithinLastNdays.size() - localCount;
		return getformattedString(localCount, outstationCount);
	}

	public int getLocalpatientVisitedCountWithinLastNdays(int days) {
		List<Boolean> isPatientVisited = new ArrayList<Boolean>();
		getLocalPatients(getPatient()).forEach(patient -> {
			isPatientVisited.add(patient.isPatientVistedInLastNDays(days));
		});

		isPatientVisited.removeIf(patient -> !patient.booleanValue());
		return isPatientVisited.size();

	}

	private String getformattedString(int localCount, int outstationCount) {
		int totalPatientCount = localCount + outstationCount;
		double localPercentage = ((localCount * 100) / totalPatientCount);
		double outstationPercentage = outstationCount * 100 / totalPatientCount;

		String localVsOutstationPercentage = String.format("%.2f" + " %% Vs " + "%.2f " + "%%", localPercentage,
				outstationPercentage);
		return localVsOutstationPercentage;
	}

	public List<Patient> getRegistredPatientsInLastNdays(int days) {
		LocalDate fromDate = LocalDate.now().minusDays(days);
		return getPatient().stream().filter(patient -> patient.getRegistrationDate().isAfter(fromDate))
				.collect(Collectors.toList());

	}

	private List<Patient> getLocalPatients(List<Patient> patientList) {
		// TODO Auto-generated method stub
		return patientList.stream()
				.filter(localpredicate -> localpredicate.getCity().name().equals(hospitalCity.name()))
				.collect(Collectors.toList());

	}

}