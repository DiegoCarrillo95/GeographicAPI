package com.diego.geographicapi.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.diego.geographicapi.dto.CountryDto;
import com.diego.geographicapi.exceptions.EntityNotFoundException;
import com.diego.geographicapi.exceptions.ResourceNotFoundException;
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
		try {
			List<CountryDto> countryDtos = new ArrayList<>();

			for (Country country : countryService.getAllCountries()) {
				countryDtos.add(Transformer.countryModelToDtoTransformer(country));
			}

			return countryDtos;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}

	public CountryDto getCountry(String countryCode) {
		try {
			return Transformer.countryModelToDtoTransformer(countryService.getCountryByCountryCode(countryCode));

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}

	public CountryDto updateCountry(String countryCode, CountryDto countryDto) {
		try {
			return Transformer.countryModelToDtoTransformer(
					countryService.updateCountry(countryCode, Transformer.countryDtoToModelTransformer(countryDto)));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}

	public void deleteCountry(String countryCode) {
		try {
			countryService.deleteCountry(countryCode);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}

}
