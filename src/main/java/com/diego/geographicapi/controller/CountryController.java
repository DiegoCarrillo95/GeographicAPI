package com.diego.geographicapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diego.geographicapi.dto.CountryDto;
import com.diego.geographicapi.facade.CountryFacade;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {

	private final CountryFacade countryFacade;

	public CountryController(CountryFacade countryFacade) {
		this.countryFacade = countryFacade;
	}

	@GetMapping
	public List<CountryDto> getAllCountries() {
		return countryFacade.getAllCountries();
	}

	@GetMapping("/{country_code}")
	public CountryDto getCountry(@PathVariable(value = "country_code") String countryCode) {
		return countryFacade.getCountry(countryCode);
	}

	@PostMapping
	public CountryDto insertCountry(@Valid @RequestBody CountryDto countryDto) {
		return countryFacade.insertCountry(countryDto);
	}

	@PutMapping("/{country_code}")
	public CountryDto updateCountry(@PathVariable(value = "country_code") String countryCode,
			@Valid @RequestBody CountryDto countryDto) {

		return countryFacade.updateCountry(countryCode, countryDto);
	}

	@DeleteMapping("/{country_code}")
	public void deleteCountry(@PathVariable(value = "country_code") String countryCode){
		countryFacade.deleteCountry(countryCode);
	}

}
