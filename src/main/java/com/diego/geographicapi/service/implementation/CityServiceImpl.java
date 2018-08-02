package com.diego.geographicapi.service.implementation;

import org.springframework.stereotype.Service;

import com.diego.geographicapi.exceptions.EntityNotFoundException;
import com.diego.geographicapi.model.City;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.model.State;
import com.diego.geographicapi.repository.CountryRepository;
import com.diego.geographicapi.service.CityService;

@Service
public class CityServiceImpl implements CityService {

	private final CountryRepository countryRepository;
	
	public CityServiceImpl(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@Override
	public Long insertCity(City city, Long stateId, Long countryId) {
		Country country = countryRepository.findOne(countryId);
		if (country == null) {
			throw new EntityNotFoundException("Country", "Id", countryId);
		}

		State state = findStateById(stateId, country);
		if (state == null) {
			throw new EntityNotFoundException("State", "Id", stateId);
		}

		state.getCities().add(city);

		Country updatedCountry = countryRepository.save(country);
		State updatedState = findStateById(stateId, updatedCountry);
		City updatedCity = findCityByFields(city, updatedState);

		return updatedCity.getId();

	}

	@Override
	public City getCity(Long cityId, Long stateId, Long countryId) {
		Country country = countryRepository.findOne(countryId);
		if (country == null) {
			throw new EntityNotFoundException("Country", "Id", countryId);
		}

		State state = findStateById(stateId, country);
		if (state == null) {
			throw new EntityNotFoundException("State", "Id", stateId);
		}
		
		City city = findCityById(cityId, state);
		if (city == null) {
			throw new EntityNotFoundException("City", "Id", cityId);
		}
		
		return city;
	}


	@Override
	public void updateCity(City city, Long stateId, Long countryId) {
		Country country = countryRepository.findOne(countryId);
		if (country == null) {
			throw new EntityNotFoundException("Country", "Id", countryId);
		}

		State state = findStateById(stateId, country);
		if (state == null) {
			throw new EntityNotFoundException("State", "Id", stateId);
		}
		
		for (int i = 0; i < state.getCities().size(); i++) {
			if (state.getCities().get(i).getId() == city.getId()) {
				state.getCities().get(i).setName(city.getName());
				state.getCities().get(i).setCityCode(city.getCityCode());
				
				countryRepository.save(country);
				
				return;
			}
		}
		
		throw new EntityNotFoundException("City", "Id", city.getId());
	}

	@Override
	public void deleteCity(Long cityId, Long stateId, Long countryId) {
		Country country = countryRepository.findOne(countryId);
		if (country == null) {
			throw new EntityNotFoundException("Country", "Id", countryId);
		}

		State state = findStateById(stateId, country);
		if (state == null) {
			throw new EntityNotFoundException("State", "Id", stateId);
		}
		
		for (int i = 0; i < state.getCities().size(); i++) {
			if (state.getCities().get(i).getId() == cityId) {
				
				state.getCities().remove(i);
				countryRepository.save(country);
				return;
			}
		}
		
		throw new EntityNotFoundException("City", "Id", cityId);
	}

	private State findStateById(Long stateId, Country country) {
		for (State state : country.getStates()) {
			if (state.getId() == stateId) {
				return state;
			}
		}

		return null;
	}

	private City findCityById(Long cityId, State state) {
		for (City city : state.getCities()) {
			if (city.getId() == cityId) {
				return city;
			}
		}
		
		return null;
	}
	
	private City findCityByFields(City cityWithoutId, State state) {
		for (City city : state.getCities()) {
			if (city.getName() == cityWithoutId.getName() && city.getCityCode() == cityWithoutId.getCityCode()) {
				return city;
			}
		}

		return null;
	}

	
}
