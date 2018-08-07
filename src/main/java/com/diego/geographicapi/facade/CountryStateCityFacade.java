package com.diego.geographicapi.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.diego.geographicapi.dto.CityDto;
import com.diego.geographicapi.exceptions.EntityNotFoundException;
import com.diego.geographicapi.exceptions.ResourceNotFoundException;
import com.diego.geographicapi.model.City;
import com.diego.geographicapi.service.CityService;
import com.diego.geographicapi.util.Transformer;

@Component
public class CountryStateCityFacade {
	private final CityService cityService;

	public CountryStateCityFacade(CityService cityService) {
		this.cityService = cityService;
	}

	public CityDto insertCity(CityDto cityDto, String stateCode, String countryCode) {
		return Transformer.cityModelToDtoTransformer(
				cityService.insertCity(Transformer.cityDtoToModelTransformer(cityDto), stateCode, countryCode));
	}

	public List<CityDto> getAllCities(String stateCode, String countryCode) {
		try {
			List<City> cityModelList = cityService.getAllCitiesByStateCodeAndCountryCode(stateCode, countryCode);
			List<CityDto> cityDtoList = new ArrayList<>();

			for (City city : cityModelList) {
				cityDtoList.add(Transformer.cityModelToDtoTransformer(city));
			}

			return cityDtoList;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}

	public CityDto getCity(String cityCode, String stateCode, String countryCode) {
		try {
			return Transformer
					.cityModelToDtoTransformer(cityService.getCityByCityCode(cityCode, stateCode, countryCode));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}
}
