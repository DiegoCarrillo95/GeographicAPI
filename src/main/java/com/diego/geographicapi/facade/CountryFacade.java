package com.diego.geographicapi.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.diego.geographicapi.dto.CountryDto;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.service.CountryService;
import com.diego.geographicapi.util.Transformer;

@Component
public class CountryFacade {
	
	private final CountryService countryService;
	
	public CountryFacade(CountryService countryService) {
		this.countryService = countryService;
	}
	
	public CountryDto insertCountry(CountryDto countryDto) {
		Country country = countryService.insertCountry(Transformer.countryDtoToModelTransformer(countryDto));
		countryDto.setId(country.getId());
		return countryDto;
	}
	
	public List<CountryDto> getAllCountries() {
		List<CountryDto> countryDtos = new ArrayList<>();
		
		for(Country country: countryService.getAllCountries()) {
			countryDtos.add(Transformer.countryModelToDtoTransformer(country));
		}
		
		return countryDtos;
	}
	
	public CountryDto getCountry(long id) {
		return Transformer.countryModelToDtoTransformer(countryService.getCountryById(id));
	}
	
	public void updateCountry(CountryDto countryDto) {
		countryService.updateCountry(Transformer.countryDtoToModelTransformer(countryDto));
	}
	
	public void deleteCountry(CountryDto countryDto) {
		countryService.deleteCountry(Transformer.countryDtoToModelTransformer(countryDto).getId());
	}
	
}
