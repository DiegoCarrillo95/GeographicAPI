package com.diego.geographicapi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diego.geographicapi.dto.CityDto;
import com.diego.geographicapi.dto.StateDto;
import com.diego.geographicapi.model.City;
import com.diego.geographicapi.model.State;

@Component
public class StateDtoModelTransformer implements TwoWayTransformer<State, StateDto> {
	
	@Autowired
	CityDtoModelTransformer cityDtoModelTransformer;
	
	@Override
	public StateDto transformToDto(State source) {
		if (source == null) {
			return null;
		}
		StateDto stateDto = new StateDto();
		stateDto.setId(source.getId());
		stateDto.setName(source.getName());
		stateDto.setStateCode(source.getStateCode());
		for (City city : source.getCities()) {
			stateDto.getCities().add(cityDtoModelTransformer.transformToDto(city));
		}
		return stateDto;
	}

	@Override
	public State transformToModel(StateDto source) {
		if (source == null) {
			return null;
		}
		State stateModel = new State();
		stateModel.setId(source.getId());
		stateModel.setName(source.getName());
		stateModel.setStateCode(source.getStateCode());
		for (CityDto city : source.getCities()) {
			stateModel.addCity(cityDtoModelTransformer.transformToModel(city));
		}
		return stateModel;
	}
}
