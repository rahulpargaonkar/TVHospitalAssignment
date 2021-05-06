package com.entity;

import java.util.List;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Hospital {
	private String hospitalName;
	private String hospitalCity;
	private List<Patient> patient;

}
