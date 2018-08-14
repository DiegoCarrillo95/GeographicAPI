package com.diego.geographicapi.facade.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.diego.geographicapi.dto.CityDto;
import com.diego.geographicapi.exceptions.EntityNotFoundException;
import com.diego.geographicapi.exceptions.ResourceNotFoundException;
import com.diego.geographicapi.facade.CityFacade;
import com.diego.geographicapi.model.City;
import com.diego.geographicapi.service.CityService;
import com.diego.geographicapi.util.Transformer;

@Component
public class CityFacadeImpl implements CityFacade{
	private final CityService cityService;

	public CityFacadeImpl(CityService cityService) {
		this.cityService = cityService;
	}

	@Override
	public CityDto insertCity(CityDto cityDto, String stateCode, String countryCode) {
		return Transformer.cityModelToDtoTransformer(
				cityService.insertCity(Transformer.cityDtoToModelTransformer(cityDto), stateCode, countryCode));
	}

	@Override
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

	@Override
	public CityDto getCity(String cityCode, String stateCode, String countryCode) {
		try {
			return Transformer
					.cityModelToDtoTransformer(cityService.getCityByCityCode(cityCode, stateCode, countryCode));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}

	@Override
	public CityDto updateCity(CityDto cityDto, String cityCode, String stateCode, String countryCode) {
		try {
			return Transformer.cityModelToDtoTransformer(cityService
					.updateCity(Transformer.cityDtoToModelTransformer(cityDto), cityCode, stateCode, countryCode));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}

	@Override
	public void deleteCity(String cityCode, String stateCode, String countryCode) {
		try {
			cityService.deleteCity(cityCode, stateCode, countryCode);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}
}
