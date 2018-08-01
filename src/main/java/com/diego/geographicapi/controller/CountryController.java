package com.diego.geographicapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diego.geographicapi.dto.CountryDto;
import com.diego.geographicapi.facade.CountryFacade;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {
	
	//TODO: TESTAR
	
	@Autowired
	private CountryFacade facade;
	
	@GetMapping
	public List<CountryDto> getAllCountries() {
		return facade.getAllCountries();
	}
	
	@GetMapping("/{id}")
	public CountryDto getCountry(@PathVariable(value = "id") long id) {
		return facade.getCountry(id);
	}
	
	
}
