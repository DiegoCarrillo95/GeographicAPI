package com.diego.geographicapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diego.geographicapi.dto.CountryDto;
import com.diego.geographicapi.facade.CountryFacade;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {
	
	private final CountryFacade countryFacade;
	
	public CountryController(CountryFacade countryFacade){
		this.countryFacade = countryFacade;
	}
	
	@GetMapping
	public List<CountryDto> getAllCountries() {
		return countryFacade.getAllCountries();
	}
	
	@GetMapping("/{id}")
	public CountryDto getCountry(@PathVariable(value = "id") long id) {
		return countryFacade.getCountry(id);
	}
	
	@PostMapping
	public CountryDto insertCountry(@Valid @RequestBody CountryDto countryDto) {
		return countryFacade.insertCountry(countryDto);
	}
	
	
}
