package com.diego.geographicapi.service;

import com.diego.geographicapi.model.State;

public interface StateService {
	public Long insertState(State state, Long countryId);
	public State getState(Long stateId, Long countryId);
	public void updateState(State state, Long countryId);
	public void deleteState(Long stateId, Long countryId);
}
