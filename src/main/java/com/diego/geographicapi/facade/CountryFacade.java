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

		for (Country country : countryService.getAllCountries()) {
			countryDtos.add(Transformer.countryModelToDtoTransformer(country));
		}

		return countryDtos;
	}

	public CountryDto getCountry(String countryCode) {
		return Transformer.countryModelToDtoTransformer(countryService.getCountryByCountryCode(countryCode));
	}

	public CountryDto updateCountry(String countryCode, CountryDto countryDto) {
		return Transformer.countryModelToDtoTransformer(
				countryService.updateCountry(countryCode, Transformer.countryDtoToModelTransformer(countryDto)));
	}

	public void deleteCountry(String countryCode) {
		Country country = countryService.getCountryByCountryCode(countryCode);
		countryService.deleteCountry(country.getId());
	}

}
