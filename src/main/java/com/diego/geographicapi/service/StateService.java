package com.diego.geographicapi.service;

import java.util.List;

import com.diego.geographicapi.model.State;

public interface StateService {
	public State insertState(State state, String countryCode);
	public State getStateByStateCode(String stateCode, String countryCode);
	public List<State> getAllStatesByCountryCode(String countryCode);
	public State updateState(State updatedState, String stateCode, String countryCode);
	public void deleteState(String stateCode, String countryCode);
}
