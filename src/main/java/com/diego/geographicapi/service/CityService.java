package com.diego.geographicapi.service;

import java.util.List;

import com.diego.geographicapi.model.City;

public interface CityService {
	public City insertCity(City city, String stateCode, String countryCode);
	public City getCityByCityCode(String cityCode, String stateCode, String countryCode);
	public List<City> getAllCitiesByStateCodeAndCountryCode(String stateCode,String countryCode);
	public City updateCity(City city, String stateCode, String countryCode);
	public void deleteCity(String cityCode, String stateCode, String countryCode);
}
