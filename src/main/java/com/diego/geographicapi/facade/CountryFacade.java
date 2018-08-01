package com.diego.geographicapi.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diego.geographicapi.dto.CountryDto;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.service.CountryService;
import com.diego.geographicapi.util.Transformer;

@Component
public class CountryFacade {
	
	@Autowired
	private CountryService countryService;
	
	public List<CountryDto> getAllCountries() {
		List<CountryDto> countryDtos = new ArrayList<>();
		
		for(Country country: countryService.getAllCountries()) {
			countryDtos.add(Transformer.countryModelToDtoTransformer(country));
		}
		
		return countryDtos;
	}
	
	public CountryDto getCountry(long id) {
		return Transformer.countryModelToDtoTransformer(countryService.getCountry(id));
	}
	
	public void updateCountry(CountryDto countryDto) {
		countryService.updateCountry(Transformer.countryDtoToModelTransformer(countryDto));
	}
	
	public void deleteCountry(CountryDto countryDto) {
		countryService.deleteCountry(Transformer.countryDtoToModelTransformer(countryDto).getId());
	}
	
}
