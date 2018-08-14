package com.diego.geographicapi.facade.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.diego.geographicapi.dto.StateDto;
import com.diego.geographicapi.exceptions.EntityNotFoundException;
import com.diego.geographicapi.exceptions.ResourceNotFoundException;
import com.diego.geographicapi.facade.StateFacade;
import com.diego.geographicapi.model.State;
import com.diego.geographicapi.service.StateService;
import com.diego.geographicapi.util.Transformer;

@Component
public class StateFacadeImpl implements StateFacade{

	private final StateService stateService;

	public StateFacadeImpl(StateService stateService) {
		this.stateService = stateService;
	}

	public StateDto insertState(StateDto stateDto, String countryCode) {
		return Transformer.stateModelToDtoTransformer(
				stateService.insertState(Transformer.stateDtoToModelTransformer(stateDto), countryCode));
	}

	@Override
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

	@Override
	public StateDto getState(String stateCode, String countryCode) {
		try {
			return Transformer.stateModelToDtoTransformer(stateService.getStateByStateCode(stateCode, countryCode));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}

	@Override
	public StateDto updateState (StateDto stateDto, String stateCode, String countryCode) {
		try {
			return Transformer.stateModelToDtoTransformer(stateService.updateState(Transformer.stateDtoToModelTransformer(stateDto), stateCode, countryCode));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}

	@Override
	public void deleteState(String stateCode, String countryCode) {
		try {
			stateService.deleteState(stateCode, countryCode);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}
}
