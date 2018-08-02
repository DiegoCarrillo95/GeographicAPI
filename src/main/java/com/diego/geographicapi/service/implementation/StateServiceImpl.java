package com.diego.geographicapi.service.implementation;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.diego.geographicapi.exceptions.EntityNotFoundException;
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
	public State insertState(State state, Long countryId) {
		Country country = countryRepository.findOne(countryId);

		if (country == null) {
			throw new EntityNotFoundException("Country", "Id", countryId);
		}

		country.getStates().add(state);
		country = countryRepository.save(country);
		int stateIndex = country.getStates().indexOf(state);
		return country.getStates().get(stateIndex);
	}

	@Override
	public State getStateById(Long stateId, Long countryId) {
		Country country = countryRepository.findOne(countryId);

		if (country == null) {
			throw new EntityNotFoundException("Country", "Id", countryId);
		}

		for (State state : country.getStates()) {
			if (state.getId() == stateId) {
				return state;
			}
		}

		throw new EntityNotFoundException("State", "Id", stateId);
	}

	@Override
	public State updateState(State state, Long countryId) {
		Country country = countryRepository.findOne(countryId);

		if (country == null) {
			throw new EntityNotFoundException("Country", "Id", countryId);
		}
		
		for (int i = 0; i < country.getStates().size(); i++) {
			if (country.getStates().get(i).getId() == state.getId()) {
				BeanUtils.copyProperties(state, country.getStates().get(i), "id");
				
				countryRepository.save(country);
				
				return country.getStates().get(i);
			}
		}
		
		throw new EntityNotFoundException("State", "Id", state.getId());
	}

	@Override
	public void deleteState(Long stateId, Long countryId) {
		Country country = countryRepository.findOne(countryId);

		if (country == null) {
			throw new EntityNotFoundException("Country", "Id", countryId);
		}

		for (int i = 0; i < country.getStates().size(); i++) {
			if (country.getStates().get(i).getId() == stateId) {
				
				country.getStates().remove(i);
				
				countryRepository.save(country);
				
				return;
			}
		}

		throw new EntityNotFoundException("State", "Id", stateId);

	}

}
