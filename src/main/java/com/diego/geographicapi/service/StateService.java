package com.diego.geographicapi.service;

import com.diego.geographicapi.model.State;

public interface StateService {
	public State insertState(State state, Long countryId);
	public State getStateById(Long stateId, Long countryId);
	public State getStateByStateCode(String stateCode, Long countryId);
	public State updateState(State state, Long countryId);
	public void deleteState(Long stateId, Long countryId);
}
