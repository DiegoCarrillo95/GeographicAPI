package com.diego.geographicapi.service.implementation;

import java.util.List;

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
		Country countryToUpdate = countryRepository.findByCountryCode(countryCode);
		
		if(countryToUpdate == null) {
			throw new EntityNotFoundException("Country", "CountryCode", countryCode);
		}
		
		countryToUpdate.addState(state);
		
		return stateRepository.save(state);
	}

	@Override
	public State getStateByStateCode(String stateCode, String countryCode) {
		State state = stateRepository.findByStateCodeAndCountryCode(stateCode, countryCode);
		
		if(state == null) {
			throw new EntityNotFoundException("State", "StateCode", stateCode);
		}
		
		return state;
	}

	@Override
	public List<State> getAllStatesByCountryCode(String countryCode) {
		List<State> states = stateRepository.findAllByCountryCode(countryCode);
		
		if(states == null) {
			throw new EntityNotFoundException("Country", "CountryCode", countryCode);
		}
		
		return states;
	}

	@Override
	public State updateState(State state, String countryCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteState(String stateCode, String countryCode) {
		// TODO Auto-generated method stub
		
	}
	
}