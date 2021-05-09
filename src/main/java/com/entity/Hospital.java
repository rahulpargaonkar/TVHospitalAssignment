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

	public int getLocalPatientCount() {
		return getPatient().stream().filter(p -> p.getCity().name().equals(hospitalCity.name()))
				.collect(Collectors.toList()).size();

	}

	public int getOutstationPatientCount() {
		int outStationPatientCount = getPatient().size() - getLocalPatientCount();
		return outStationPatientCount;
	}

	public String getformattedLocalVsOutstationPercentage(int localCount, int outstationCount) {
		int totalPatientCount = localCount+outstationCount;
		double localPercentage = ((localCount * 100) / totalPatientCount);
		double outstationPercentage = outstationCount * 100 / totalPatientCount;
		String localVsOutstationPercentage = String.format("%.2f" + " %% Vs " + "%.2f " + "%%", localPercentage,
				outstationPercentage);
		return localVsOutstationPercentage;
	}

	public int getLocalpatientVisitedCountWithinLastNdays(int days) {

		List<Patient> local = getPatient().stream()
				.filter(localpredicate -> localpredicate.getCity().name().equals(hospitalCity.name()))
				.collect(Collectors.toList());
		List<Boolean> isPatientVisited = new ArrayList<Boolean>();
		local.forEach(p -> {
			isPatientVisited.add(p.isPatientVistedInLastNDays(days));
		});

		isPatientVisited.removeIf(patient -> !patient.booleanValue());
		return isPatientVisited.size();

	}

	public int getLocalPatientRegistrationCountinLastNdays(int days) {
		LocalDate fromDate = LocalDate.now().minusDays(days);
		return getPatient().stream().filter(p -> p.getRegistrationDate().isAfter(fromDate))
				.filter(p -> p.getCity().name().equals(hospitalCity.name())).collect(Collectors.toList()).size();

	}

}