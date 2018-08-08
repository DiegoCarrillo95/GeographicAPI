package com.diego.geographicapi.service.implementation;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.diego.geographicapi.exceptions.EntityNotFoundException;
import com.diego.geographicapi.model.City;
import com.diego.geographicapi.model.State;
import com.diego.geographicapi.repository.CityRepository;
import com.diego.geographicapi.repository.StateRepository;
import com.diego.geographicapi.service.CityService;

@Service
public class CityServiceImpl implements CityService {

	private final StateRepository stateRepository;

	private final CityRepository cityRepository;

	public CityServiceImpl(CityRepository cityRepository, StateRepository stateRepository) {
		this.cityRepository = cityRepository;
		this.stateRepository = stateRepository;
	}

	@Override
	public City insertCity(City city, String stateCode, String countryCode) {
		State stateToUpdate = stateRepository.findByStateCodeAndCountryCode(stateCode, countryCode);

		if (stateToUpdate == null) {
			throw new EntityNotFoundException("State", "StateCode", stateCode);
		}

		stateToUpdate.addCity(city);

		stateRepository.save(stateToUpdate);

		return cityRepository.findByCityCodeAndStateCodeAndCountryCode(city.getCityCode(), stateCode, countryCode);

	}

	@Override
	public City getCityByCityCode(String cityCode, String stateCode, String countryCode) {
		City city = cityRepository.findByCityCodeAndStateCodeAndCountryCode(cityCode, stateCode, countryCode);

		if (city == null) {
			throw new EntityNotFoundException("City", "CityCode", cityCode);
		}

		return city;
	}

	@Override
	public List<City> getAllCitiesByStateCodeAndCountryCode(String stateCode, String countryCode) {
		List<City> cities = cityRepository.findAllByStateCodeCountryCode(stateCode, countryCode);

		if (cities == null) {
			throw new EntityNotFoundException("State", "StateCode", stateCode);
		}

		return cities;
	}

	@Override
	public City updateCity(City updatedCity, String cityCode, String stateCode, String countryCode) {
		City currentCity = cityRepository.findByCityCodeAndStateCodeAndCountryCode(cityCode, stateCode, countryCode);

		if (currentCity == null) {
			throw new EntityNotFoundException("City", "CityCode", cityCode);
		}

		BeanUtils.copyProperties(updatedCity, currentCity, "id", "state");

		return cityRepository.save(currentCity);
	}

	@Override
	public void deleteCity(String cityCode, String stateCode, String countryCode) {
		City cityToDelete = cityRepository.findByCityCodeAndStateCodeAndCountryCode(cityCode, stateCode, countryCode);

		if (cityToDelete == null) {
			throw new EntityNotFoundException("City", "CityCode", cityCode);
		}
		
		cityRepository.delete(cityToDelete);
	}
}