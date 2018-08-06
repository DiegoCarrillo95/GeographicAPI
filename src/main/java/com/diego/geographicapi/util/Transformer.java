package com.diego.geographicapi.util;

import com.diego.geographicapi.dto.CountryDto;
import com.diego.geographicapi.dto.StateDto;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.model.State;

public class Transformer {
	public static CountryDto countryModelToDtoTransformer(Country countryModel) {
		CountryDto countryDto = new CountryDto();
		countryDto.setId(countryModel.getId());
		countryDto.setName(countryModel.getName());
		countryDto.setCountryCode(countryModel.getCountryCode());
		//TODO: countryDto.setStates(states);
		return countryDto;
	}

	public static Country countryDtoToModelTransformer(CountryDto countryDto) {
		Country countryModel = new Country();
		countryModel.setId(countryDto.getId());
		countryModel.setName(countryDto.getName());
		countryModel.setCountryCode(countryDto.getCountryCode());
		//TODO: countryModel.setStates(states);
		return countryModel;
	}
	
	public static StateDto stateModelToDtoTransformer(State stateModel) {
		StateDto stateDto = new StateDto();
		stateDto.setId(stateModel.getId());
		stateDto.setName(stateModel.getName());
		stateDto.setStateCode(stateModel.getStateCode());
		//TODO: stateDto.setCities(stateModel.getCities());
		return stateDto;
	}
	
	public static State stateDtoToModelTransformer(StateDto stateDto) {
		State stateModel = new State();
		stateModel.setId(stateDto.getId());
		stateModel.setName(stateDto.getName());
		stateModel.setStateCode(stateDto.getStateCode());
		//TODO: stateModel.setCities(stateDto.getCities());
		return stateModel;
	}

}
