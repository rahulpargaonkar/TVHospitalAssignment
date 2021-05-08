package com.entity;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class Patient {
	private String name;
	private City city;
	private Date registrationDate;
	private List<Visit> visit;

}
