package com.diego.geographicapi.util;

import com.diego.geographicapi.dto.CityDto;
import com.diego.geographicapi.dto.CountryDto;
import com.diego.geographicapi.dto.StateDto;
import com.diego.geographicapi.model.City;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.model.State;

public class Transformer {
	public static CountryDto countryModelToDtoTransformer(Country countryModel) {
		CountryDto countryDto = new CountryDto();
		countryDto.setId(countryModel.getId());
		countryDto.setName(countryModel.getName());
		countryDto.setCountryCode(countryModel.getCountryCode());
		for(State state: countryModel.getStates()){
			countryDto.getStates().add(stateModelToDtoTransformer(state));
		}
		return countryDto;
	}

	public static Country countryDtoToModelTransformer(CountryDto countryDto) {
		Country countryModel = new Country();
		countryModel.setId(countryDto.getId());
		countryModel.setName(countryDto.getName());
		countryModel.setCountryCode(countryDto.getCountryCode());
		for(StateDto state: countryDto.getStates()){
			countryModel.addState(stateDtoToModelTransformer(state));
		}
		return countryModel;
	}
	
	public static StateDto stateModelToDtoTransformer(State stateModel) {
		StateDto stateDto = new StateDto();
		stateDto.setId(stateModel.getId());
		stateDto.setName(stateModel.getName());
		stateDto.setStateCode(stateModel.getStateCode());
		for(City city: stateModel.getCities()){
			stateDto.getCities().add(cityModelToDtoTransformer(city));
		}
		return stateDto;
	}
	
	public static State stateDtoToModelTransformer(StateDto stateDto) {
		State stateModel = new State();
		stateModel.setId(stateDto.getId());
		stateModel.setName(stateDto.getName());
		stateModel.setStateCode(stateDto.getStateCode());
		for(CityDto city: stateDto.getCities()){
			stateModel.addCity(cityDtoToModelTransformer(city));
		}
		return stateModel;
	}
	
	public static CityDto cityModelToDtoTransformer(City cityModel){
		CityDto cityDto = new CityDto();
		cityDto.setId(cityModel.getId());
		cityDto.setName(cityModel.getName());
		cityDto.setCityCode(cityModel.getCityCode());
		return cityDto;
	}
	
	public static City cityDtoToModelTransformer(CityDto cityDto) {
		City cityModel = new City();
		cityModel.setId(cityDto.getId());
		cityModel.setName(cityDto.getName());
		cityModel.setCityCode(cityDto.getCityCode());
		return cityModel;
	}
}
