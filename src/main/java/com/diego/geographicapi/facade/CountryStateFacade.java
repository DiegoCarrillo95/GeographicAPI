package com.diego.geographicapi.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.diego.geographicapi.dto.StateDto;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.model.State;
import com.diego.geographicapi.service.CountryService;
import com.diego.geographicapi.service.StateService;
import com.diego.geographicapi.util.Transformer;

@Component
public class CountryStateFacade {

	private final CountryService countryService;
	private final StateService stateService;

	public CountryStateFacade(CountryService countryService, StateService stateService) {
		this.countryService = countryService;
		this.stateService = stateService;
	}

	public StateDto insertState(StateDto stateDto, String countryCode) {
		Country country = countryService.getCountryByCountryCode(countryCode);
		country.getStates().add(Transformer.stateDtoToModelTransformer(stateDto));
		country = countryService.updateCountry(country);

		return Transformer
				.stateModelToDtoTransformer(stateService.getStateByStateCode(stateDto.getStateCode(), country.getId()));
	}

	public List<StateDto> getAllStates(String countryCode) {
		Country country = countryService.getCountryByCountryCode(countryCode);
		List<State> stateModelList = stateService.getAllStatesByCountry(country.getId());
		List<StateDto> stateDtoList = new ArrayList<>();

		for (State state : stateModelList) {
			stateDtoList.add(Transformer.stateModelToDtoTransformer(state));
		}

		return stateDtoList;
	}

	public StateDto getState(String stateCode, String countryCode) {
		Country country = countryService.getCountryByCountryCode(countryCode);
		return Transformer.stateModelToDtoTransformer(stateService.getStateByStateCode(stateCode, country.getId()));

	}
}
