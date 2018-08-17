package com.diego.geographicapi.util;

import org.springframework.stereotype.Component;

import com.diego.geographicapi.dto.CityDto;
import com.diego.geographicapi.model.City;

@Component
public class CityDtoModelTransformer implements TwoWayTransformer<City, CityDto> {

	@Override
	public CityDto transformToDto(City source) {
		if (source == null) {
			return null;
		}
		CityDto cityDto = new CityDto();
		cityDto.setId(source.getId());
		cityDto.setName(source.getName());
		cityDto.setCityCode(source.getCityCode());
		return cityDto;
	}

	@Override
	public City transformToModel(CityDto source) {
		if (source == null) {
			return null;
		}
		City cityModel = new City();
		cityModel.setId(source.getId());
		cityModel.setName(source.getName());
		cityModel.setCityCode(source.getCityCode());
		return cityModel;
	}

}
