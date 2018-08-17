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
import com.diego.geographicapi.util.CityDtoModelTransformer;

@Component
public class CityFacadeImpl implements CityFacade {

	private CityService cityService;
	private CityDtoModelTransformer cityDtoModelTransformer;

	public CityFacadeImpl(CityService cityService, CityDtoModelTransformer cityDtoModelTransformer) {
		this.cityService = cityService;
		this.cityDtoModelTransformer = cityDtoModelTransformer;
	}

	@Override
	public CityDto insertCity(CityDto cityDto, String stateCode, String countryCode) {
		return cityDtoModelTransformer.transformToDto(
				cityService.insertCity(cityDtoModelTransformer.transformToModel(cityDto), stateCode, countryCode));
	}

	@Override
	public List<CityDto> getAllCities(String stateCode, String countryCode) {
		try {
			List<City> cityModelList = cityService.getAllCitiesByStateCodeAndCountryCode(stateCode, countryCode);
			List<CityDto> cityDtoList = new ArrayList<>();

			for (City city : cityModelList) {
				cityDtoList.add(cityDtoModelTransformer.transformToDto(city));
			}

			return cityDtoList;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}

	@Override
	public CityDto getCity(String cityCode, String stateCode, String countryCode) {
		try {
			return cityDtoModelTransformer
					.transformToDto(cityService.getCityByCityCode(cityCode, stateCode, countryCode));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}

	@Override
	public CityDto updateCity(CityDto cityDto, String cityCode, String stateCode, String countryCode) {
		try {
			return cityDtoModelTransformer.transformToDto((cityService
					.updateCity(cityDtoModelTransformer.transformToModel(cityDto), cityCode, stateCode, countryCode)));
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
