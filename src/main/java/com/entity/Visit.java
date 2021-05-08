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
public class Visit {
	private Date visitDateTime;
	private String doctorName;
}
