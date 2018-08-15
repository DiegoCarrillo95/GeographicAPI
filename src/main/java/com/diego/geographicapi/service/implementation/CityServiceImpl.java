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
		State stateToUpdate = stateRepository.findByStateCodeAndCountryCode(stateCode, countryCode)
				.orElseThrow(() -> new EntityNotFoundException("State", "StateCode", stateCode));

		stateToUpdate.addCity(city);

		return cityRepository.save(city);
	}

	@Override
	public City getCityByCityCode(String cityCode, String stateCode, String countryCode) {
		return cityRepository.findByCityCodeAndStateCodeAndCountryCode(cityCode, stateCode, countryCode)
				.orElseThrow(() -> new EntityNotFoundException("City", "CityCode", cityCode));
	}

	@Override
	public List<City> getAllCitiesByStateCodeAndCountryCode(String stateCode, String countryCode) {
		return cityRepository.findAllByStateCodeCountryCode(stateCode, countryCode)
				.orElseThrow(() -> new EntityNotFoundException("State", "StateCode", stateCode));
	}

	@Override
	public City updateCity(City updatedCity, String cityCode, String stateCode, String countryCode) {
		City currentCity = getCityByCityCode(cityCode, stateCode, countryCode);

		BeanUtils.copyProperties(updatedCity, currentCity, "id", "state");

		return cityRepository.save(currentCity);
	}

	@Override
	public void deleteCity(String cityCode, String stateCode, String countryCode) {
		cityRepository.delete(getCityByCityCode(cityCode, stateCode, countryCode));
	}
}