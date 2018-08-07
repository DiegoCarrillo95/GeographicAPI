package com.diego.geographicapi.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.diego.geographicapi.dto.StateDto;
import com.diego.geographicapi.exceptions.EntityNotFoundException;
import com.diego.geographicapi.exceptions.ResourceNotFoundException;
import com.diego.geographicapi.model.State;
import com.diego.geographicapi.service.StateService;
import com.diego.geographicapi.util.Transformer;

@Component
public class CountryStateFacade {

	private final StateService stateService;

	public CountryStateFacade(StateService stateService) {
		this.stateService = stateService;
	}

	public StateDto insertState(StateDto stateDto, String countryCode) {
		return Transformer.stateModelToDtoTransformer(
				stateService.insertState(Transformer.stateDtoToModelTransformer(stateDto), countryCode));
	}

	public List<StateDto> getAllStates(String countryCode) {
		try {
			List<State> stateModelList = stateService.getAllStatesByCountryCode(countryCode);
			List<StateDto> stateDtoList = new ArrayList<>();

			for (State state : stateModelList) {
				stateDtoList.add(Transformer.stateModelToDtoTransformer(state));
			}

			return stateDtoList;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}

	public StateDto getState(String stateCode, String countryCode) {
		try {
			return Transformer.stateModelToDtoTransformer(stateService.getStateByStateCode(stateCode, countryCode));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}

	public StateDto updateState(String stateCode, StateDto stateDto, String countryCode) {
		// TODO:
		return null;
	}

	public void deleteState(String stateCode, String countryCode) {
		// TODO:
	}
}
