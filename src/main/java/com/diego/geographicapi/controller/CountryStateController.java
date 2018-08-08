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

import com.diego.geographicapi.dto.StateDto;
import com.diego.geographicapi.facade.CountryStateFacade;

@RestController
@RequestMapping("/api/v1/country/{country_code}/state")
public class CountryStateController {

	private final CountryStateFacade countryStateFacade;

	public CountryStateController(CountryStateFacade countryStateFacade) {
		this.countryStateFacade = countryStateFacade;
	}

	@GetMapping
	public List<StateDto> getAllStates(@PathVariable(value = "country_code") String countryCode) {
		return countryStateFacade.getAllStates(countryCode);
	}

	@GetMapping("/{state_code}")
	public StateDto getState(@PathVariable(value = "country_code") String countryCode,
			@PathVariable(value = "state_code") String stateCode) {
		
		return countryStateFacade.getState(stateCode, countryCode);
	}

	@PostMapping
	public StateDto insertState(@PathVariable(value = "country_code") String countryCode,
			@Valid @RequestBody StateDto stateDto) {
		
		return countryStateFacade.insertState(stateDto, countryCode);
	}
	
	@PutMapping("/{state_code}")
	public StateDto updateState(@PathVariable(value = "country_code") String countryCode, 
			@PathVariable(value = "state_code") String stateCode,
			@Valid @RequestBody StateDto stateDto) {

		return countryStateFacade.updateState(stateDto, stateCode, countryCode);
	}
	
	@DeleteMapping("/{state_code}")
	public void deleteState(@PathVariable(value = "country_code") String countryCode, 
			@PathVariable(value = "state_code") String stateCode) {
		
		countryStateFacade.deleteState(stateCode, countryCode);
	}
}
