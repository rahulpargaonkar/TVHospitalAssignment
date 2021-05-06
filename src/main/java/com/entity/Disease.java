package com.entity;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class Disease {
	private String diseaseName;
	private boolean isCriticaldisease;
}
