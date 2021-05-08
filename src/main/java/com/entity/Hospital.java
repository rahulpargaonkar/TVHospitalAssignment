package com.entity;

import java.util.ArrayList;
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
	private City hospitalCity;
	private List<Patient> patient;

	public int getPatientCountforCity() {
		return getPatient().stream().filter(p -> p.getCity().name().equals(hospitalCity.name()))
				.collect(Collectors.toList()).size();

	}

	public int getPatientCountforLocalWithRegistrationDateRange(Date fromDate, Date toDate) {
		return getPatient().stream()
				.filter(p -> p.getRegistrationDate().after(fromDate) && p.getRegistrationDate().before(toDate))
				.filter(p -> p.getCity().name().equals(hospitalCity.name())).collect(Collectors.toList()).size();

	}

	public int getOutstationPatientCount() {
		Predicate<Patient> outstationPredicate = p -> p.getCity().name().equals(hospitalCity.name());
		return getPatient().stream().filter(outstationPredicate.negate()).collect(Collectors.toList()).size();
	}

	public String getLocalVsOutstationPatientPercentage() {
		int totalPatientCount = getPatient().size();
		int localCount = getPatientCountforCity();
		int outstationCount = totalPatientCount - localCount;
		String localVsOutstationPercentage = getformattedLocalVsOutstationCount(localCount, outstationCount);
		return localVsOutstationPercentage;
	}

	public String getformattedLocalVsOutstationCount(int localCount, int outstationCount) {
		int totalPatientCount = getPatient().size();
		double localPercentage = ((localCount * 100) / totalPatientCount);
		double outstationPercentage = outstationCount * 100 / totalPatientCount;
		String localVsOutstationPercentage = String.format("%.2f" + " %% Vs " + "%.2f " + "%%", localPercentage,
				outstationPercentage);
		return localVsOutstationPercentage;
	}

	public int getLocalpatientVisitedCountWithinLastNdays(Date fromDate, Date toDate) {
		List<Patient> local = getPatient().stream()
				.filter(localpredicate -> localpredicate.getCity().name().equals(hospitalCity.name()))
				.collect(Collectors.toList());
		List<Visit> visitList = new ArrayList<Visit>();
		local.forEach(p -> p.getVisit().stream()
				.filter(v -> v.getVisitDateTime().after(fromDate) && v.getVisitDateTime().before(toDate)).findFirst()
				.ifPresent(v -> visitList.add(v)));
		return visitList.size();

	}

}