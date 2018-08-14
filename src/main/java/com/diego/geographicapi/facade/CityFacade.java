package com.diego.geographicapi.facade;

import java.util.List;

import com.diego.geographicapi.dto.CityDto;

public interface CityFacade {
	public CityDto insertCity(CityDto cityDto, String stateCode, String countryCode);
	public List<CityDto> getAllCities(String stateCode, String countryCode);
	public CityDto getCity(String cityCode, String stateCode, String countryCode);
	public CityDto updateCity(CityDto cityDto, String cityCode, String stateCode, String countryCode);
	public void deleteCity(String cityCode, String stateCode, String countryCode);
}
