package com.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class Disease {
	private String diseaseName;
	private boolean isCriticaldisease;
}
