package com.diego.geographicapi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diego.geographicapi.dto.CountryDto;
import com.diego.geographicapi.dto.StateDto;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.model.State;

@Component
public class CountryDtoModelTransformer implements TwoWayTransformer<Country, CountryDto> {
	
	@Autowired
	StateDtoModelTransformer stateDtoModelTransformer;
	
	@Override
	public CountryDto transformToDto(Country source) {
		if (source == null) {
			return null;
		}

		CountryDto countryDto = new CountryDto();
		countryDto.setId(source.getId());
		countryDto.setName(source.getName());
		countryDto.setCountryCode(source.getCountryCode());
		for (State state : source.getStates()) {
			countryDto.getStates().add(stateDtoModelTransformer.transformToDto(state));
		}
		return countryDto;

	}

	@Override
	public Country transformToModel(CountryDto source) {
		if (source == null) {
			return null;
		}
		Country countryModel = new Country();
		countryModel.setId(source.getId());
		countryModel.setName(source.getName());
		countryModel.setCountryCode(source.getCountryCode());
		for (StateDto state : source.getStates()) {
			countryModel.addState(stateDtoModelTransformer.transformToModel(state));
		}
		return countryModel;
	}
}
