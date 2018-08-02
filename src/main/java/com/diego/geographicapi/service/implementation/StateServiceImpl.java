package com.diego.geographicapi.service.implementation;

import org.springframework.stereotype.Service;

import com.diego.geographicapi.exceptions.ResourceNotFoundException;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.model.State;
import com.diego.geographicapi.repository.CountryRepository;
import com.diego.geographicapi.service.StateService;

@Service
public class StateServiceImpl implements StateService {

	private final CountryRepository countryRepository;
	
	public StateServiceImpl(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@Override
	public Long insertState(State state, Long countryId) {
		Country country = countryRepository.findOne(countryId);

		if (country == null) {
			throw new ResourceNotFoundException("Country", "Id", countryId);
		}

		country.getStates().add(state);
		country = countryRepository.save(country);
		int stateIndex = country.getStates().indexOf(state);
		return country.getStates().get(stateIndex).getId();
	}

	@Override
	public State getState(Long stateId, Long countryId) {
		Country country = countryRepository.findOne(countryId);

		if (country == null) {
			throw new ResourceNotFoundException("Country", "Id", countryId);
		}

		for (State state : country.getStates()) {
			if (state.getId() == stateId) {
				return state;
			}
		}

		throw new ResourceNotFoundException("State", "Id", stateId);
	}

	@Override
	public void updateState(State state, Long countryId) {
		Country country = countryRepository.findOne(countryId);

		if (country == null) {
			throw new ResourceNotFoundException("Country", "Id", countryId);
		}
		
		for (int i = 0; i < country.getStates().size(); i++) {
			if (country.getStates().get(i).getId() == state.getId()) {
				country.getStates().get(i).setName(state.getName());
				country.getStates().get(i).setStateCode(state.getStateCode());
				country.getStates().get(i).setCities(state.getCities());
				
				countryRepository.save(country);
				
				return;
			}
		}
		
		throw new ResourceNotFoundException("State", "Id", state.getId());
	}

	@Override
	public void deleteState(Long stateId, Long countryId) {
		Country country = countryRepository.findOne(countryId);

		if (country == null) {
			throw new ResourceNotFoundException("Country", "Id", countryId);
		}

		for (int i = 0; i < country.getStates().size(); i++) {
			if (country.getStates().get(i).getId() == stateId) {
				
				country.getStates().remove(i);
				
				countryRepository.save(country);
				
				return;
			}
		}

		throw new ResourceNotFoundException("State", "Id", stateId);

	}

}
