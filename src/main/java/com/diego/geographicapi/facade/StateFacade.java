package com.diego.geographicapi.facade;

import java.util.List;

import com.diego.geographicapi.dto.StateDto;

public interface StateFacade {
	public StateDto insertState(StateDto stateDto, String countryCode);
	public List<StateDto> getAllStates(String countryCode);
	public StateDto getState(String stateCode, String countryCode);
	public StateDto updateState (StateDto stateDto, String stateCode, String countryCode);
	public void deleteState(String stateCode, String countryCode);
	
}
