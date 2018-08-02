package com.diego.geographicapi.service;

import com.diego.geographicapi.model.City;

public interface CityService {
	public City insertCity(City city, Long stateId, Long countryId);
	public City getCityById(Long cityId, Long stateId, Long countryId);
	public City updateCity(City city, Long stateId, Long countryId);
	public void deleteCity(Long cityId, Long stateId, Long countryId);
}
