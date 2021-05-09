package com.entity;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Patient {
	private String name;
	private City city;
	private LocalDate registrationDate;
	private List<Visit> visit;
	
	public boolean isPatientVistedInLastNDays(int days) {
		// TODO Auto-generated method stub
		LocalDate fromDate= LocalDate.now().minusDays(days);
		boolean isvisited= getVisit().stream()
				.filter(visit -> visit.getVisitDateTime().isAfter(fromDate)).findFirst().isPresent();
		return isvisited;
		
	}

}
