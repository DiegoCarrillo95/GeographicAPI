package com.diego.geographicapi.service.implementation;

import java.util.List;

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
		return cityRepository.findByCityCodeAndStateCodeAndCountryCode(cityCode, stateCode, countryCode);
	}

	@Override
	public List<City> getAllCitiesByStateCodeAndCountryCode(String stateCode, String countryCode) {
		return cityRepository.findAllByStateCodeCountryCode(stateCode, countryCode);
	}

	@Override
	public City updateCity(City city, String stateCode, String countryCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCity(String cityCode, String stateCode, String countryCode) {
		// TODO Auto-generated method stub

	}
}