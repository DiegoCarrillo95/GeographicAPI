package com.diego.geographicapi.dto;

import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CityDto {

	@EqualsAndHashCode.Exclude
	private Long id;
	
	@Size(min = 3, message = "Name should have at least 3 and less than 100 characters")
	private String name;
	
	@Size(min = 3, max = 3, message = "City code should have 3 characters")
	private String cityCode;
	
}