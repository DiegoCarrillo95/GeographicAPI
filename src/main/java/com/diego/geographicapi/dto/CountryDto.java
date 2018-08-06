package com.diego.geographicapi.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CountryDto {

	private Long id;
	
	@Size(min = 3, max = 100, message = "Name should have at least 3 and less than 100 characters")
	private String name;
	
	@Size(min = 2, max = 2, message = "Country code should have 2 characters")
	private String countryCode;
	
	private List<StateDto> states = new ArrayList<>();
	
}
