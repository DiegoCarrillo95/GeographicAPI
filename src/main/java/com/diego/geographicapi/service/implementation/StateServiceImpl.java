package com.diego.geographicapi.service.implementation;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.diego.geographicapi.exceptions.EntityNotFoundException;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.model.State;
import com.diego.geographicapi.repository.CountryRepository;
import com.diego.geographicapi.repository.StateRepository;
import com.diego.geographicapi.service.StateService;

@Service
public class StateServiceImpl implements StateService {

	private final CountryRepository countryRepository;
	private final StateRepository stateRepository;

	public StateServiceImpl(CountryRepository countryRepository, StateRepository stateRepository) {
		this.countryRepository = countryRepository;
		this.stateRepository = stateRepository;
	}

	@Override
	public State insertState(State state, String countryCode) {
		Country countryToUpdate = countryRepository.findByCountryCode(countryCode)
				.orElseThrow(() -> new EntityNotFoundException("Country", "CountryCode", countryCode));

		countryToUpdate.addState(state);

		return stateRepository.save(state);
	}

	@Override
	public State getStateByStateCode(String stateCode, String countryCode) {
		return stateRepository.findByStateCodeAndCountryCode(stateCode, countryCode)
				.orElseThrow(() -> new EntityNotFoundException("State", "StateCode", stateCode));
	}

	@Override
	public List<State> getAllStatesByCountryCode(String countryCode) {
		return stateRepository.findAllByCountryCode(countryCode)
				.orElseThrow(() -> new EntityNotFoundException("Country", "CountryCode", countryCode));
	}

	@Override
	public State updateState(State updatedState, String stateCode, String countryCode) {
		State currentState = getStateByStateCode(stateCode, countryCode);

		BeanUtils.copyProperties(updatedState, currentState, "id", "country", "cities");

		return stateRepository.save(currentState);
	}

	@Override
	public void deleteState(String stateCode, String countryCode) {
		stateRepository.delete(getStateByStateCode(stateCode, countryCode));
	}

}