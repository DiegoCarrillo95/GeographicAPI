package com.diego.geographicapi.facade.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.diego.geographicapi.dto.CountryDto;
import com.diego.geographicapi.exceptions.EntityNotFoundException;
import com.diego.geographicapi.exceptions.ResourceNotFoundException;
import com.diego.geographicapi.facade.CountryFacade;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.service.CountryService;
import com.diego.geographicapi.util.CountryDtoModelTransformer;


@Component
public class CountryFacadeImpl implements CountryFacade{

	private CountryService countryService;
	private CountryDtoModelTransformer countryDtoModelTransformer;

	public CountryFacadeImpl(CountryService countryService, CountryDtoModelTransformer countryDtoModelTransformer) {
		this.countryService = countryService;
		this.countryDtoModelTransformer =countryDtoModelTransformer;
	}
	
	@Override
	public CountryDto insertCountry(CountryDto countryDto) {
		return countryDtoModelTransformer.transformToDto(
				countryService.insertCountry(countryDtoModelTransformer.transformToModel(countryDto)));
	}

	@Override
	public List<CountryDto> getAllCountries() {
		List<CountryDto> countryDtos = new ArrayList<>();

		for (Country country : countryService.getAllCountries()) {
			countryDtos.add(countryDtoModelTransformer.transformToDto(country));
		}

		return countryDtos;
	}

	@Override
	public CountryDto getCountry(String countryCode) {
		try {
			return countryDtoModelTransformer.transformToDto(countryService.getCountryByCountryCode(countryCode));

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}

	@Override
	public CountryDto updateCountry(String countryCode, CountryDto countryDto) {
		try {
			return countryDtoModelTransformer.transformToDto(
					countryService.updateCountry(countryCode, countryDtoModelTransformer.transformToModel(countryDto)));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}

	@Override
	public void deleteCountry(String countryCode) {
		try {
			countryService.deleteCountry(countryCode);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}

}
