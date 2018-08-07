package com.diego.geographicapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diego.geographicapi.dto.CityDto;
import com.diego.geographicapi.facade.CountryStateCityFacade;

@RestController
@RequestMapping("/api/v1/country/{country_code}/state/{state_code}/city")
public class CountryStateCityController {

	private final CountryStateCityFacade countryStateCityFacade;

	public CountryStateCityController(CountryStateCityFacade countryStateCityFacade) {
		this.countryStateCityFacade = countryStateCityFacade;
	}

	@GetMapping
	public List<CityDto> getAllCities(@PathVariable(value = "state_code") String stateCode,
			@PathVariable(value = "country_code") String countryCode) {
		return countryStateCityFacade.getAllCities(stateCode, countryCode);
	}

	@GetMapping("/{city_code}")
	public CityDto getState(@PathVariable(value = "city_code") String cityCode,
			@PathVariable(value = "state_code") String stateCode,
			@PathVariable(value = "country_code") String countryCode) {

		return countryStateCityFacade.getCity(cityCode, stateCode, countryCode);
	}

	@PostMapping
	public CityDto insertCity(@PathVariable(value = "state_code") String stateCode,
			@PathVariable(value = "country_code") String countryCode, @Valid @RequestBody CityDto cityDto) {

		return countryStateCityFacade.insertCity(cityDto, stateCode, countryCode);
	}
}
