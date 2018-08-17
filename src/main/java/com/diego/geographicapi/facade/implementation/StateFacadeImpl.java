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
import com.diego.geographicapi.util.StateDtoModelTransformer;

@Component
public class StateFacadeImpl implements StateFacade {

	private StateService stateService;
	private StateDtoModelTransformer stateDtoModelTransformer;

	public StateFacadeImpl(StateService stateService, StateDtoModelTransformer stateDtoModelTransformer) {
		this.stateService = stateService;
		this.stateDtoModelTransformer = stateDtoModelTransformer;
	}

	public StateDto insertState(StateDto stateDto, String countryCode) {
		return stateDtoModelTransformer.transformToDto(
				stateService.insertState(stateDtoModelTransformer.transformToModel(stateDto), countryCode));
	}

	@Override
	public List<StateDto> getAllStates(String countryCode) {
		try {
			List<State> stateModelList = stateService.getAllStatesByCountryCode(countryCode);
			List<StateDto> stateDtoList = new ArrayList<>();

			for (State state : stateModelList) {
				stateDtoList.add(stateDtoModelTransformer.transformToDto(state));
			}

			return stateDtoList;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}

	@Override
	public StateDto getState(String stateCode, String countryCode) {
		try {
			return stateDtoModelTransformer.transformToDto(stateService.getStateByStateCode(stateCode, countryCode));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getResourceName(), e.getFieldName(), e.getFieldValue());
		}
	}

	@Override
	public StateDto updateState(StateDto stateDto, String stateCode, String countryCode) {
		try {
			return stateDtoModelTransformer.transformToDto(stateService
					.updateState(stateDtoModelTransformer.transformToModel(stateDto), stateCode, countryCode));
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
