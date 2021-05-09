package com.entity;

import java.time.LocalDate;
import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class Visit {
	
	private LocalDate visitDateTime;
	private String doctorName;
}
