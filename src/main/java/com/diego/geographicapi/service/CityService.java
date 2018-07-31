package com.diego.geographicapi.service;

import com.diego.geographicapi.model.City;

public interface CityService {
	public Long insertCity(City city, Long stateId, Long countryId);
	public City getCity(Long cityId, Long stateId, Long countryId);
	public void updateCity(City city, Long stateId, Long countryId);
	public void deleteCity(Long cityId, Long stateId, Long countryId);
}
